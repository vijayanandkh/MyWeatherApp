package com.example.networkmodule

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

enum class UNIT_TYPE(units: String, degree: String) {
    imperial("imperial", "°F"),
    metric("metric","°C"),
    standard("standard","°K")
}

interface WeatherApiServiceInterface {
    @GET(BuildConfig.API_VERSION+"weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") apiKeys: String,
        @Query("units") units: String=UNIT_TYPE.imperial.name
    ): Response<WeatherResponse>

    @GET(BuildConfig.API_VERSION+"forecast")
    suspend fun getWeatherForecast(
        @Query("q") city: String,
        @Query("appid") apiKeys: String,
        @Query("units") units: String=UNIT_TYPE.imperial.name,
        @Query("cnt") cnt: Int = 4// no of days (1 to 16) - actual default is 16 but using 4
    ): Response<WeatherForecastResponse>


    @GET(BuildConfig.API_VERSION+"onecall")
    suspend fun getLocationWeatherForecast(
        @Query("lat") latitude: Double = 32.7876012,
        @Query("long") longitude: Double = -79.9402728,
        @Query("exclude") exclude: String?,
        @Query("appid") apiKeys: String,
        @Query("units") units: String=UNIT_TYPE.imperial.name
    ): Response<WeatherForecastResponse>
}
