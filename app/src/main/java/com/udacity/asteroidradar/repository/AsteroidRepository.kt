package com.udacity.asteroidradar.repository

import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.domain.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.UnknownHostException

class AsteroidRepository(private val database: AsteroidDatabase) {
    private val dates = getNextSevenDaysFormattedDates()

    val allAsteroids = Transformations.map(database.asteroidDao.getAllAsteroids()) {
        it.asDomainModel()
    }

    val todayAsteroids = Transformations.map(database.asteroidDao.getTodayAsteroids(dates[0])) {
        it.asDomainModel()
    }

    val weeksAsteroids = Transformations.map(database.asteroidDao.getWeeksAsteroids(dates[0], dates.last())) {
        it.asDomainModel()
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try{
                val filter = HashMap<String, String>()
                filter["start_date"] = dates[0]
                filter["end_date"] = dates.last()
                filter["api_key"] = "gBoOTigLxjL6vuY426CjoefdjLlrJeWm3u8Dza7A"

                val response = NasaApi.retrofitService.getAsteroids(filter)
                val data = parseAsteroidsJsonResult(JSONObject(response)).asDatabaseModel().toTypedArray()

                database.asteroidDao.insertAll(*data)
            } catch(e: UnknownHostException) {

            }
        }
    }

    suspend fun refreshImageOfTheDay() {
        withContext(Dispatchers.IO) {
            try {
                val APIKey = "gBoOTigLxjL6vuY426CjoefdjLlrJeWm3u8Dza7A"

                val response = NasaApi.retrofitService.getImageOfTheDay(APIKey).asDatabaseModel()

                database.asteroidDao.updateImageOfTheDay(response)
            } catch(e: UnknownHostException) {

            }
        }
    }
}