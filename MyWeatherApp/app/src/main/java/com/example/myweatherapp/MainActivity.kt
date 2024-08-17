package com.example.myweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myweatherapp.ui.theme.MyWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    val viewModel: WeatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        setContent {
            MyWeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherFlow(viewModel)
                }
            }
        }

    }
}

@Composable
fun WeatherFlow(viewModel: WeatherViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home" ) {
        composable("home") {
            HomeScreen(navController)
        }
        composable("weather") {
            WeatherScreen(navController, viewModel)
        }
        composable("forecast") {
            ForecastScreen(navController, viewModel)
        }
    }

}
