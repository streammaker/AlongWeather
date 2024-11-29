package com.example.alongweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.alongweather.logic.Repository
import com.example.alongweather.logic.model.Location
import com.example.alongweather.logic.model.Weather

class WeatherViewModel : ViewModel() {

    private val locationLiveData = MutableLiveData<Location>()
    var locationLng = ""
    var locationLat = ""
    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData) {
        location ->
        Repository.refershWeather(location.lng, location.lat)
    }

    fun refreshWeather(lng : String, lat : String) {
        locationLiveData.value = Location(lng, lat)
    }
}