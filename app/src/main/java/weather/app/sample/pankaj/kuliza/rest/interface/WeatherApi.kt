package weather.app.sample.pankaj.kuliza.rest.`interface`

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import weather.app.sample.pankaj.kuliza.model.WeatherData

interface WeatherApi {

    @GET("forecast.json")
    fun getWeatherForecast(@QueryMap options: Map<String, String>) : Single<Response<WeatherData>>
}
