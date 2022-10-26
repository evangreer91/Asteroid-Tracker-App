package com.udacity.asteroidradar.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "image_of_day_table")
data class ImageOfTheDay(
    @PrimaryKey(autoGenerate = true)
    var imageId: Long = 1L,

    @Json(name = "media_type")
    var mediaType: String = "",

    var title: String = "",

    var url: String = ""
)