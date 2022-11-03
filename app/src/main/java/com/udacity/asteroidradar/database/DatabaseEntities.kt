package com.udacity.asteroidradar.database

import androidx.lifecycle.Transformations.map
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.ImageOfTheDay

@Entity(tableName = "asteroid_table")
data class DatabaseAsteroid constructor(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean)

@Entity(tableName = "image_of_the_day_table")
data class DatabaseImageOfTheDay constructor(
    @PrimaryKey
    val url: String,
    val mediaType: String,
    val title: String
)

@JvmName("asDomainModelDatabaseAsteroid")
fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid (
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}

@JvmName("asDomainModelDatabaseImageOfTheDay")
fun DatabaseImageOfTheDay.asDomainModel(): ImageOfTheDay {
    return ImageOfTheDay (url, mediaType, title)
}