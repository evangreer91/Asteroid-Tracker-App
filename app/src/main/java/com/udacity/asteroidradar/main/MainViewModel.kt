package com.udacity.asteroidradar.main

import android.app.Application
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
import android.util.Log
import android.view.MenuItem
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

    enum class AsteroidFilter(val value: String) {
        SHOW_WEEK("week"),
        SHOW_TODAY("today"),
        SHOW_SAVED("all")
    }

    private val _imageOfTheDayResponse = MutableLiveData<ImageOfTheDay>()
    val imageOfTheDayResponse: LiveData<ImageOfTheDay>
        get() = _imageOfTheDayResponse

    var optionMenu = MutableLiveData<AsteroidFilter>(AsteroidFilter.SHOW_SAVED)

    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)

    init {

        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
        }
        getImageOfTheDay()
    }

    val asteroids: LiveData<List<Asteroid>> = Transformations.switchMap(optionMenu) {
        when(it) {
            AsteroidFilter.SHOW_SAVED -> asteroidRepository.allAsteroids
            AsteroidFilter.SHOW_TODAY -> asteroidRepository.todayAsteroids
            else -> asteroidRepository.weeksAsteroids
        }
    }

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