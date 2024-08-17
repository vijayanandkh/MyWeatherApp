package com.example.networkmodule

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val main:Main,
    val weather: List<Weather>,
    val name: String,
    val wind: Wind,
    val coord: Coord
)

data class Main(
    val temp: Double,
    val feels_like : Double,
    val humidity: Int,
    val temp_min: Double,
    val temp_max: Double
)

data class Weather (
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Double,
    val deg: Int
)

data class Coord (
    var lon : Double? = null,
    var lat : Double? = null
)

/*
data class CityNames(
    val id: Int? = null,
    val coord: Coord? = Coord(),
    val country: String? = null,
    val geoname: Geoname? = Geoname(),
    val name: String? = null,
)

data class Geoname (
    val cl: String? = null,
    val code: String? = null,
    val parent: Int? = null
)
*/

data class WeatherForecastItem (
    val dt : Int? = null,
    val main: Main,
    val weather : ArrayList<Weather> = arrayListOf(),
    val wind: Wind,
    @SerializedName("dt_txt") val dtTxt: String
)

data class Temp (
   val day : Double? = null,
   val min : Double? = null,
   val max : Double? = null,
)

//data class FeelsLike (
//    val day : Double? = null,
//    val night : Double? = null,
//    val eve : Double? = null,
//    val morn : Double? = null
//)

data class WeatherForecastResponse (
  val cod    : String? = null,
  val cnt    : Int? = null,
  val list : ArrayList<WeatherForecastItem> = arrayListOf()
)

data class RowData(
    val temp: Double,
//    val temp_min: Double,
//    val temp_max: Double,
    val weather: Weather,
    val feels_like: Double?,
    val dtTxt: String
)

//data class CityLocation (
//   val name : String? = null,
//   val lat : Double? = null,
//   val lon : Double? = null,
//   val country : String? = null,
//)

