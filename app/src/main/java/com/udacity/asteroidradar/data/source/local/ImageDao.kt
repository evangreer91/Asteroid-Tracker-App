package com.udacity.asteroidradar.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.udacity.asteroidradar.data.source.local.ImageOfTheDay

@Dao
interface ImageDao {

    @Insert
    fun insert(imageOfDay: ImageOfTheDay)

    @Query("SELECT * FROM image_of_day_table")
    suspend fun getImage(): LiveData<ImageOfTheDay>
}