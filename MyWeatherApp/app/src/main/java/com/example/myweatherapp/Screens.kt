package com.example.myweatherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.networkmodule.RowData
import com.example.networkmodule.WeatherResponse
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(navController: NavController) {
    val modifier = Modifier.fillMaxSize()
    Box(
        modifier = modifier.background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
        ) {
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(220.dp))
            Text(
                fontSize = 30.sp,
                text = stringResource(R.string.welcome_to_weather),
                textAlign = TextAlign.Center
            )
            Text(
                fontSize = 26.sp,
                text = Utility().displayDate(),
            )
            Button(onClick = {
                navController.navigate("weather") {
                    popUpTo("home") { inclusive = true} }
            }) {
                Text(fontSize = 22.sp, text = "Get Weather", textAlign = TextAlign.Center)
            }
        }
    }

    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate("weather") {
            popUpTo("home") { inclusive = true}
        }
    }
}

@Preview
@Composable
fun PreViewHomeScreen() {
    HomeScreen(navController = rememberNavController())
}

@Composable
fun WeatherScreen(navController: NavController, viewModel: WeatherViewModel = viewModel()) {

    val cityName by viewModel.city.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
            verticalArrangement = Arrangement.Center) {

            CityNameInput(cityName = cityName, onCityNameChange = { viewModel.updateCityName( it) })

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.getWeather(cityName)
                    keyboardController?.hide() },
                enabled = cityName.isNotBlank()) {
                Text(
                    text = stringResource(R.string.get_weather_info),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("forecast") },
                enabled = cityName.isNotBlank()) {
                Text(
                    text = stringResource(R.string.get_weather_forecast_of_coming_days),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (uiState) {
                is UiState.Loading -> {
//                    CircularProgressIndicator() -- not needed for this case
                }
                is UiState.Success -> {
                    val weather = (uiState as UiState.Success<WeatherResponse>).data as WeatherResponse
                    Text(
                        text = "City: ${weather.name}\n" +
                                "Current Temperature: ${weather.main.temp} °F\n" +
                                "Feels Like: ${weather.main.feels_like} °F\n" +
                                "Humidity: ${weather.main.humidity} % \n"+
                                "Wind speed: ${weather.wind.speed} mph",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 16.dp, start = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                        .padding(20.dp)) {
                        Text(
                            text = "Condition: ${weather.weather[0].description} \n",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        val imageUrl =
                            "" + com.example.networkmodule.BuildConfig.SERVER_IMAGE_BASE_URL + weather.weather[0].icon + ".png"
//                        println("Image icon url : $imageUrl")
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = "Loaded Image",
                            modifier = Modifier.size(100.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
                is UiState.Error -> {
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        text = "Code: "+ (uiState as UiState.Error).message + " message : " +(uiState as UiState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreViewWeatherScreen() {
    WeatherScreen(navController = rememberNavController(), )
}

@Composable
fun CityNameInput(cityName: String, onCityNameChange: (String) -> Unit) {
//    var cityName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Enter City Name",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = cityName,
            maxLines = 1,
            singleLine = true,
            onValueChange = { onCityNameChange(it) },
            label = { Text("City Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun CityNameInputPreview() {
    CityNameInput("cityname", onCityNameChange = {Unit})
}

@Composable
fun ForecastScreen(navController: NavHostController, viewModel: WeatherViewModel = viewModel()) {
    val cityName by viewModel.city.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val daysForecastDataState by viewModel.daysForecastDataState.collectAsState()

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 20.dp), contentAlignment = Alignment.TopCenter) {
        Column {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    fontSize = 26.sp,
                    text = stringResource(R.string.weather_forecast_of_days),
                )
                Text(
                    fontSize = 22.sp,
                    text = Utility().displayDate(),
                )
                Text(
                    fontSize = 22.sp,
                    text = "City : $cityName",
                )

                Spacer(modifier = Modifier.height(26.dp))

                when (daysForecastDataState) {
                    is UiState.Loading -> {
                        CircularProgressIndicator()
                    }
                    is UiState.Success -> {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            TableCell(text = "Conditions", weight = 1f)
                            TableCell(text = "Icon", weight = 1f)
                            TableCell(text = "Temperature\nFeels Like", weight = 1f)
                            TableCell(text = "Date\nTime Stamp", weight = 1f)
                        }
                        ListWithFourColumns(data = (daysForecastDataState as UiState.Success<List<RowData>>).data)
                        Spacer(modifier = Modifier.height(40.dp))
                        backButton(navController)
                    }
                    is UiState.Error -> {
                        Spacer(modifier = Modifier.height(40.dp))
                        Text(
                            text = "Code: "+ (daysForecastDataState as UiState.Error).message + " message : " +(daysForecastDataState as UiState.Error).message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(40.dp))
                        backButton(navController)
                    }
                }


            }
        }
    }

    LaunchedEffect(Unit) {
//        delay(3000)
        viewModel.getWeatherForecast(cityName)
    }

}

@Composable
private fun backButton(navController: NavHostController) {
    Button(
        onClick = {
            navController.popBackStack()
        }
    ) {
        Text(
            fontSize = 26.sp,
            text = "Back",
            textAlign = TextAlign.Center,
            modifier = Modifier.width(100.dp)
        )
    }
}

@Composable
fun TableCell(text: String, weight: Float) {
    Box(
        modifier = Modifier
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ListWithFourColumns(data: List<RowData>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(data) { item ->
            println("row data is $item")
            ForecastDaysColumnRow(item)
        }
    }
}

@Composable
fun ForecastDaysColumnRow(rowData: RowData) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = rowData.weather.description,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )
        val imageUrl =
            "" + com.example.networkmodule.BuildConfig.SERVER_IMAGE_BASE_URL + rowData.weather.icon + ".png"
//                        println("Image icon url : $imageUrl")
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = "Loaded Image",
            modifier = Modifier.size(50.dp),
            contentScale = ContentScale.Fit
        )

        Text(""+rowData.temp + " °F\n" +
                ""+rowData.feels_like +" °F\n",
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp, top = 10.dp),
            style = MaterialTheme.typography.bodySmall
        )

        Text(
            text = ""+rowData.dtTxt,
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp),
            style = MaterialTheme.typography.bodySmall
        )
//        Text(
//            text = ""+rowData.temp_min,
//            modifier = Modifier.weight(1f),
//            style = MaterialTheme.typography.bodySmall
//        )
    }
}
@Preview
@Composable
fun PreViewForecastScreen() {
    ForecastScreen(navController = rememberNavController())
}