package com.example.alongweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.alongweather.logic.Repository
import com.example.alongweather.logic.dao.PlaceDao
import com.example.alongweather.logic.model.Place

class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
    }
    fun searchPlaces(query : String) {
        searchLiveData.value = query
    }

    fun savePlace(place : Place) {
        Repository.savePlace(place)
    }

    fun getSavedPlace() : Place {
        return Repository.getSavedPlace()
    }

    fun isPlaceSaved() : Boolean {
        return Repository.isPlaceSaved()
    }
}