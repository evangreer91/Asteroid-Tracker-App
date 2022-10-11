package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.ImageOfTheDay
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel : ViewModel() {

    private val _imageOfTheDayResponse = MutableLiveData<ImageOfTheDay>()
    val imageOfTheDayResponse: LiveData<ImageOfTheDay>
        get() = _imageOfTheDayResponse

    private val _asteroidsResponse = MutableLiveData<String>()
    val asteroidsResponse: LiveData<String>
        get() = _asteroidsResponse

    init {
        getNeoWSProperties()
        getImageOfTheDay()
    }

    private fun getNeoWSProperties() {

        // pass om a hash map to add query parameters to the get API call
        val filter = HashMap<String, String>()
        filter["start_date"] = "2015-09-08"
        filter["end_date"] = "2015-09-08"
        filter["api_key"] = "API_KEY_HERE"

        // we run getAsteroids call in a coroutine and take advantage of error handling
        viewModelScope.launch {
            try {
                var response = NasaApi.retrofitService.getAsteroids(filter)
                val result = JSONObject(response)
                val data = parseAsteroidsJsonResult(result)

                _asteroidsResponse.value = "Success: ${data.size} Asteroids fetched."
            } catch (e: Exception) {
                _asteroidsResponse.value = "Failure: ${e.message}"
            }
        }
    }

    private fun getImageOfTheDay() {

        val APIKey = "API_KEY_HERE"

        // we run getPictureOfTheDay call in a coroutine and take advantage of error handling
        viewModelScope.launch {
            try {
                var imageResponse = NasaApi.retrofitService.getImageOfTheDay(APIKey)

                _imageOfTheDayResponse.value = imageResponse
            } catch (e: Exception) {

            }
        }
    }
}