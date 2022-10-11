package com.udacity.asteroidradar.api

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

// root address of the nasa server endpoint
private const val BASE_URL = "https://api.nasa.gov/"

// create a RetroFit builder
// pass in a scalars converter that supports returning strings and other primitive types
// specify route web address of our server's endpoint
// call build to create the RetroFit object
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

// public interface that exposes the getProperties method
// defines an interface that explains how retrofit talks to our web server using HTTP requests
interface NeoWebServiceApi {
    // getProperties gets the JSON response
    // use the GET annotation and specify the endpoint
    // returns a Retrofit callback that delivers a JSON string response
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(@QueryMap filter: HashMap<String, String>): String
}

// public api object that exposes the lazy-initialized Retrofit service
// to create a retrofit service, call retrofit.create passing in the service interface API
// calling NeoApi.retrofitService will return a retrofit object implementing NeoWebServiceApi
object NeoApi {
    val retrofitService: NeoWebServiceApi by lazy { retrofit.create(NeoWebServiceApi::class.java) }
}