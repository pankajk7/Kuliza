package weather.app.sample.pankaj.kuliza.rest

import io.reactivex.Single
import retrofit2.Response
import weather.app.sample.pankaj.kuliza.model.WeatherData
import weather.app.sample.pankaj.kuliza.rest.`interface`.WeatherApi

object WeatherApiService {

    private val weatherApiService by lazy {
        ApiClient.client.create(WeatherApi::class.java)
    }

    fun getWeatherData(options: Map<String, String>): Single<Response<WeatherData>> {
        return weatherApiService.getWeatherForecast(options)
    }
}