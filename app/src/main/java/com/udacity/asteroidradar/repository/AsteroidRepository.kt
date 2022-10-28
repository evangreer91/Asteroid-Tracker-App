package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.ImageOfTheDay
import com.udacity.asteroidradar.domain.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val filter = HashMap<String, String>()
            filter["start_date"] = "2015-09-08"
            filter["end_date"] = "2015-09-08"
            filter["api_key"] = "gBoOTigLxjL6vuY426CjoefdjLlrJeWm3u8Dza7A"

            val response = NasaApi.retrofitService.getAsteroids(filter)
            val data =
                parseAsteroidsJsonResult(JSONObject(response)).asDatabaseModel().toTypedArray()

            database.asteroidDao.insertAll(*data)
        }
    }
}

