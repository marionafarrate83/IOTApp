package com.example.iotapp

import retrofit2.Response
import retrofit2.http.GET

interface WeatherService {
    @GET("weather?q=mexico,MX&appid=41d1d7f5c2475b3a16167b30bc4f265c&units=metric")
    suspend fun getWeather(): Response<WeatherItem>
}