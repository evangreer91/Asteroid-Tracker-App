package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.PictureOfTheDay
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel : ViewModel() {

    private val _imageOfTheDayResponse = MutableLiveData<PictureOfTheDay>()
    val imageOfTheDayResponse: LiveData<PictureOfTheDay>
        get() = _imageOfTheDayResponse

    private val _asteroidsResponse = MutableLiveData<String>()
    val asteroidsResponse: LiveData<String>
        get() = _asteroidsResponse

    init {
        getNeoWSProperties()
        getNASAPictureOfTheDay()
    }

    private fun getNeoWSProperties() {

        // pass om a hash map to add query parameters to the get API call
        val filter = HashMap<String, String>()
        filter["start_date"] = "2015-09-08"
        filter["end_date"] = "2015-09-08"
        filter["api_key"] = "KnAmTbGzQWQKqgLxcDOGbyQsy3xfghB55wYm7LSq"

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

    private fun getNASAPictureOfTheDay() {

        val APIKey = "KnAmTbGzQWQKqgLxcDOGbyQsy3xfghB55wYm7LSq"

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