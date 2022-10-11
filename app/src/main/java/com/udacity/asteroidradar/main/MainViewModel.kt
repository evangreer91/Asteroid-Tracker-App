package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.NeoApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    init {
        getNeoWSProperties()
    }

    private fun getNeoWSProperties() {

        // pass om a hash map to add query parameters to the get API call
        val filter = HashMap<String, String>()
        filter["start_date"] = "2015-09-08"
        filter["end_date"] = "2015-09-08"
        filter["api_key"] = "API KEY HERE"

        // we run getAsteroids call in a coroutine and take advantage of error handling
        viewModelScope.launch {
            try {
                var response = NeoApi.retrofitService.getAsteroids(filter)
                val result = JSONObject(response)
                val data = parseAsteroidsJsonResult(result)

                _response.value = "Success: ${data.size} Asteroids fetched."
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }
}