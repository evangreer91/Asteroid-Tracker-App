package com.udacity.asteroidradar.main

import android.app.Application
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.domain.ImageOfTheDay
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _imageOfTheDayResponse = MutableLiveData<ImageOfTheDay>()
    val imageOfTheDayResponse: LiveData<ImageOfTheDay>
        get() = _imageOfTheDayResponse

    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)

    init {
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
        }
        getImageOfTheDay()
    }

    val asteroids = asteroidRepository.asteroids

    private fun getImageOfTheDay() {

        val APIKey = "gBoOTigLxjL6vuY426CjoefdjLlrJeWm3u8Dza7A"

        // we run getPictureOfTheDay call in a coroutine and take advantage of error handling
        viewModelScope.launch {
            try {
                var imageResponse = NasaApi.retrofitService.getImageOfTheDay(APIKey)
                _imageOfTheDayResponse.value = imageResponse
            } catch (e: Exception) {
                println("Handling error...")
            }
        }
    }

    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()
    val navigateToSelectedAsteroid: LiveData<Asteroid>
        get() = _navigateToSelectedAsteroid

    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToSelectedAsteroid.value = asteroid
    }

    fun displayAsteroidDetailsComplete() {
        _navigateToSelectedAsteroid.value = null
    }

    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}