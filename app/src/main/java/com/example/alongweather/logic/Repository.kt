package com.example.alongweather.logic

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.alongweather.AlongApplication
import com.example.alongweather.logic.model.Place
import com.example.alongweather.logic.model.PlaceResponse
import com.example.alongweather.logic.network.AlongWeatherNetwork
import kotlinx.coroutines.Dispatchers
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



}