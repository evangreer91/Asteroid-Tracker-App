package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.data.source.local.ImageOfTheDay
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel : ViewModel() {

    private val _imageOfTheDayResponse = MutableLiveData<ImageOfTheDay>()
    val imageOfTheDayResponse: LiveData<ImageOfTheDay>
        get() = _imageOfTheDayResponse

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    init {
        getNeoWSProperties()
        getImageOfTheDay()
    }

    private fun getNeoWSProperties() {

        // pass om a hash map to add query parameters to the get API call
        val filter = HashMap<String, String>()
        filter["start_date"] = "2015-09-08"
        filter["end_date"] = "2015-09-08"
        filter["api_key"] = "SPI_KRY_HERE"

        // we run getAsteroids call in a coroutine and take advantage of error handling
        viewModelScope.launch {
            try {
                var response = NasaApi.retrofitService.getAsteroids(filter)
                val result = JSONObject(response)
                val data = parseAsteroidsJsonResult(result)

                _asteroids.value = data
                _status.value = "Success"
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
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