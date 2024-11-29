package com.example.alongweather.logic.network

import com.example.alongweather.logic.model.DailyResponse
import com.example.alongweather.logic.model.PlaceResponse
import com.example.alongweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object AlongWeatherNetwork {

    private val placeService = ServiceCreator.create<PlaceService>()

    private val weatherService = ServiceCreator.create<WeatherService>()

    suspend fun searchPlaces(query : String) : PlaceResponse {
        return placeService.searchPlaces(query).await()
    }

    suspend fun getRealtimeWeather(lng : String, lat : String) : RealtimeResponse {
        return weatherService.getRealtimeWeather(lng, lat).await()
    }

    suspend fun getDailyWeather(lng : String, lat : String) : DailyResponse {
        return weatherService.getDailyWeather(lng, lat).await()
    }

    private suspend fun<T> Call<T>.await() : T {
        return suspendCoroutine {
            continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        continuation.resume(body)
                    } else {
                        continuation.resumeWithException(RuntimeException("response body is null"))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }
    }
}