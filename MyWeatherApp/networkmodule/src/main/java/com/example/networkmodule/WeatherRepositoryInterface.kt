package com.example.networkmodule

interface WeatherRepositoryInterface {
//    suspend fun getCurrentWeather(city: String, apiKey: String): WeatherResponse

    suspend fun getCurrentWeather(city: String, units: String): NetworkCallResult<WeatherResponse>
    suspend fun getWeatherForecast(city: String, units: String, cnt:Int = 4): NetworkCallResult<WeatherForecastResponse>
    suspend fun getLocationWeatherForecast(lat :Double, lon: Double, exclude: String?, units: String): NetworkCallResult<WeatherForecastResponse>
}