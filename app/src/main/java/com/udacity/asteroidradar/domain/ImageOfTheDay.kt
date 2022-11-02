package com.udacity.asteroidradar.domain

import android.media.Image
import androidx.lifecycle.Transformations.map
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.udacity.asteroidradar.database.DatabaseAsteroid
import com.udacity.asteroidradar.database.DatabaseImageOfTheDay

data class ImageOfTheDay(
    @PrimaryKey
    var url: String = "",

    @Json(name = "media_type")
    var mediaType: String = "",

    var title: String = ""
)

fun ImageOfTheDay.asDatabaseModel(): DatabaseImageOfTheDay {
    return DatabaseImageOfTheDay(url, mediaType, title)
}