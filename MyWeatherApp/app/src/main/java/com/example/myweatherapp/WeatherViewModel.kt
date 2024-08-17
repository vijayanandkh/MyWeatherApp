package com.example.myweatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networkmodule.NetworkCallResult
import com.example.networkmodule.RowData
import com.example.networkmodule.UNIT_TYPE
import com.example.networkmodule.WeatherForecastResponse
import com.example.networkmodule.WeatherRepository
import com.example.networkmodule.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModel() {

    private val _city = MutableStateFlow("")
    val city: StateFlow<String>  = _city.asStateFlow()

    private val _weatherState = MutableStateFlow<WeatherResponse?>(null)
    val weatherState: StateFlow<WeatherResponse?> get() = _weatherState.asStateFlow()
    var weatherResponse : WeatherResponse? =null
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    private val _uiState = MutableStateFlow<UiState<WeatherResponse>>(UiState.Loading)
    val uiState: StateFlow<UiState<WeatherResponse>> = _uiState

    private val _uiForecastState = MutableStateFlow<UiState<WeatherForecastResponse>>(UiState.Loading)
    val uiForecastState: StateFlow<UiState<WeatherForecastResponse>> = _uiForecastState

    val _daysForecastDataState = MutableStateFlow<UiState<List<RowData>>>(UiState.Loading)
    val daysForecastDataState: StateFlow<UiState<List<RowData>>> = _daysForecastDataState

    fun getWeather(city: String) {
        println("search weather for city : $city")
        viewModelScope.launch {
            try {
                val response = weatherRepository.getCurrentWeather(city, UNIT_TYPE.imperial.name)
                weatherResponse = null
                when(response) {
                    is NetworkCallResult.Success -> {
                        _uiState.value = UiState.Success(response.data)
                        weatherResponse = response.data
                    }
                    is NetworkCallResult.Error -> {
                        _uiState.value = UiState.Error(response.code, response.message)
                    }
                    is NetworkCallResult.Exception -> {

                        if (response.message.isNullOrBlank()) {
                            _uiState.value = UiState.Error(response.message!!, response.e.message.toString())
                        } else {
                            _uiState.value = UiState.Error("Something went wrong", "Pleas check errors in logs")
                        }
                    }
                }
            } catch (e: Exception) {
                println("exception : $e")
                _uiState.value =  UiState.Error("Some exception", "Pleas check error in api logs")
            }
        }
    }


    fun getWeatherForecast(city: String) {
        println("search weather forecast for city : $city")
        viewModelScope.launch {
            try {
//                if(weatherResponse != null) {
//                    // use lat lon for forecast weather
//                } else {
//                    // get lat lon for the city and call forecast
//
//                }
//                val response = weatherRepository.getLocationWeatherForecast(weatherResponse!!.coord.lat!!, weatherResponse!!.coord.lon!!,"", UNIT_TYPE.imperial.name)
                val response = weatherRepository.getWeatherForecast(city, UNIT_TYPE.imperial.name)

                println("forecast data: $response.data")
                when(response) {
                    is NetworkCallResult.Success -> {
                        _daysForecastDataState.value = processForecastData(response.data)
                    }
                    is NetworkCallResult.Error -> {
                        _daysForecastDataState.value = UiState.Error(response.code, response.message)
                    }
                    is NetworkCallResult.Exception -> {

                        if (response.message.isNullOrBlank()) {
                            _daysForecastDataState.value = UiState.Error(response.message!!, response.e.message.toString())
                        } else {
                            _daysForecastDataState.value = UiState.Error("Something went wrong", "Pleas check errors in logs")
                        }
                    }
                }
            } catch (e: Exception) {
                println("exception : $e")
                _daysForecastDataState.value =  UiState.Error("Some exception", "Pleas check error in api logs")
            }
        }
    }

    private fun processForecastData(weatherForecastResponse: WeatherForecastResponse): UiState<List<RowData>> {
        val forecastDetailsList: ArrayList<RowData> = arrayListOf()
        var index =0
        for(weatherInfo in weatherForecastResponse.list) {
            println("day $index forecast: $weatherInfo")
            forecastDetailsList.add(RowData(weatherInfo.main.temp, weatherInfo.weather[0],weatherInfo.main.feels_like, weatherInfo.dtTxt))
            println("day $index forecast: ${forecastDetailsList.get(index)}")
            index++
        }
       return UiState.Success(forecastDetailsList)
    }

    private fun updateWeatherForCity(weatherResponse: WeatherResponse) {
        clearErrorMessage()
        _weatherState.value = weatherResponse
    }



    fun updateCityName(newCityName: String) {
        println("new city name: $newCityName")
        _city.value = newCityName
        weatherResponse = null
    }

    // Method to clear the error message
    fun clearErrorMessage() {
        _errorMessage.value = null
    }



}