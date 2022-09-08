package com.example.pracnetwork1

import com.example.pracnetwork1.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherInterface {

    @GET("weather")
    fun getWeather(@Query("q") q: String,
                   @Query("appid") appid: String


    ): Call<WeatherResponse>

}