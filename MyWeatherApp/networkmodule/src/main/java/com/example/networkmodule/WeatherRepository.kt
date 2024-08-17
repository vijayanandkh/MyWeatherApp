package com.example.networkmodule

import com.example.networkmodule.NetworkCallResult.Success
import java.net.UnknownHostException
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApiService: WeatherApiServiceInterface
): WeatherRepositoryInterface {

//    suspend fun getCurrentWeather(
//        city: String,
//        apiKey: String
//    ): NetworkCallResult<WeatherResponse> {
//        return try {
//            val response = weatherApiService.getCurrentWeather(city, apiKey)
//            println("api call response code ${response.code()}")
//            println("api call response body ${response.body()}")
//            if (response.isSuccessful && response.body() != null) {
//                Success(response.body()!!)
//            } else {
//                NetworkCallResult.Error(code = response.code(), response.message())
//            }
//        } catch (e: UnknownHostException) {
//            NetworkCallResult.Error(code = 404, message = "No Known host")
//        } catch (e: Throwable) {
//            NetworkCallResult.Exception(e.message, Throwable(e))
//        }
//    }

    override suspend fun getCurrentWeather(
        city: String,
        units: String,
    ): NetworkCallResult<WeatherResponse> {
        return try {
            val response = weatherApiService.getCurrentWeather(city, BuildConfig.WEATHER_API_KEY)
            if (response.isSuccessful && response.body() != null) {
                Success(response.body()!!)
            } else {
                NetworkCallResult.Error(code = response.code(), response.message())
            }
        } catch (e: UnknownHostException) {
            NetworkCallResult.Error(code = 404, message = "No Known host")
        } catch (e: Throwable) {
            NetworkCallResult.Exception(e.message, Throwable(e))
        }
    }

    override suspend fun getWeatherForecast(
        city: String,
        units: String,
        cnt: Int
    ): NetworkCallResult<WeatherForecastResponse> {
        return try {
            val response = weatherApiService.getWeatherForecast(city, BuildConfig.WEATHER_API_KEY, units, cnt)
            if (response.isSuccessful && response.body() != null) {
                Success(response.body()!!)
            } else {
                NetworkCallResult.Error(code = response.code(), response.message())
            }
        } catch (e: UnknownHostException) {
            NetworkCallResult.Error(code = 404, message = "No Known host")
        } catch (e: Throwable) {
            NetworkCallResult.Exception(e.message, Throwable(e))
        }
    }

    override suspend fun getLocationWeatherForecast(
        lat: Double,
        lon: Double,
        exclude: String?,
        units: String
    ): NetworkCallResult<WeatherForecastResponse> {
        return try {
            val response = weatherApiService.getLocationWeatherForecast(lat,lon, "hourly,minutely,alerts", BuildConfig.WEATHER_API_KEY, units)
            if (response.isSuccessful && response.body() != null) {
                Success(response.body()!!)
            } else {
                NetworkCallResult.Error(code = response.code(), response.message())
            }
        } catch (e: UnknownHostException) {
            NetworkCallResult.Error(code = 404, message = "No Known host")
        } catch (e: Throwable) {
            NetworkCallResult.Exception(e.message, Throwable(e))
        }
    }

}