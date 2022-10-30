package com.udacity.asteroidradar.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.AsteroidFilter
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.database.dates
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidDatabase) {
    private val dates = getNextSevenDaysFormattedDates()

    var asteroids = Transformations.map(database.asteroidDao.getAllAsteroids()) {
        it.asDomainModel()
    }

    fun filterAsteroids(filter: AsteroidFilter) {
        asteroids = when(filter) {
            AsteroidFilter.SHOW_ALL -> Transformations.map(database.asteroidDao.getAllAsteroids()) {
                it.asDomainModel()
            }
            AsteroidFilter.SHOW_TODAY -> Transformations.map(database.asteroidDao.getTodayAsteroids(dates[0])) {
                it.asDomainModel()
            }
            AsteroidFilter.SHOW_WEEK -> Transformations.map(database.asteroidDao.getWeeksAsteroids(dates[0], dates.last())) {
                it.asDomainModel()
            }
        }
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val filter = HashMap<String, String>()
            filter["start_date"] = dates[0]
            filter["end_date"] = dates.last()
            filter["api_key"] = "gBoOTigLxjL6vuY426CjoefdjLlrJeWm3u8Dza7A"

            val response = NasaApi.retrofitService.getAsteroids(filter)
            val data = parseAsteroidsJsonResult(JSONObject(response)).asDatabaseModel().toTypedArray()

            database.asteroidDao.insertAll(*data)
        }
    }
}