package com.example.alongweather.logic.dao

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.alongweather.AlongApplication
import com.example.alongweather.logic.model.Place
import com.google.gson.Gson

object PlaceDao {

    fun savePlace(place : Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace() : Place {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() : Boolean {
        return sharedPreferences().contains("place")
    }

    private fun sharedPreferences() : SharedPreferences {
        return AlongApplication.context.getSharedPreferences("along_weather", Context.MODE_PRIVATE)
    }
}