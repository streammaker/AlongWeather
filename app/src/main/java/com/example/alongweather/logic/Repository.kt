package com.example.alongweather.logic

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.alongweather.AlongApplication
import com.example.alongweather.logic.dao.PlaceDao
import com.example.alongweather.logic.model.Place
import com.example.alongweather.logic.model.PlaceResponse
import com.example.alongweather.logic.model.Weather
import com.example.alongweather.logic.network.AlongWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import okhttp3.Dispatcher

object Repository {

    fun searchPlaces(query : String): LiveData<Result<List<Place>>> {
        return liveData<Result<List<Place>>>(Dispatchers.IO) {
            val result = try {
                val placeResponse = AlongWeatherNetwork.searchPlaces(query)
                if (placeResponse.status == "ok") {
                    Log.d("luozhenfeng", "1111")
                    val places = placeResponse.places
                    Result.success(places)
                } else {
                    Result.failure(RuntimeException("response status is ${placeResponse.status}"))
                }
            } catch (e : Exception) {
                Log.d("luozhenfeng", "2222")
                Result.failure<List<Place>>(e)
            }
            Log.d("luozhenfeng", "3333")
            emit(result)
        }
    }

    fun refershWeather(lng : String, lat : String) : LiveData<Result<Weather>> {
        return liveData<Result<Weather>>(Dispatchers.IO) {
            val result = try {
                coroutineScope {
                    val deferredRealtime = async {
                        AlongWeatherNetwork.getRealtimeWeather(lng, lat)
                    }
                    val deferredDaily = async {
                        AlongWeatherNetwork.getDailyWeather(lng, lat)
                    }
                    val realtimeResponse = deferredRealtime.await()
                    val dailyResponse = deferredDaily.await()
                    if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                        val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                        Result.success(weather)
                    } else {
                        Result.failure(RuntimeException("realtimeResponse status is ${realtimeResponse.status}" +
                        "dailyResponse status is ${dailyResponse.status}"))
                    }
                }
            } catch (e : Exception) {
                Result.failure<Weather>(e)
            }
            emit(result)
        }
    }

    fun savePlace(place : Place) {
        PlaceDao.savePlace(place)
    }

    fun getSavedPlace() : Place {
        return PlaceDao.getSavedPlace()
    }

    fun isPlaceSaved() : Boolean {
        return PlaceDao.isPlaceSaved()
    }
}