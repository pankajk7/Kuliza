package weather.app.sample.pankaj.kuliza.model

import com.google.gson.annotations.SerializedName

class WeatherData {

    @SerializedName("forecast")
    var forecast: Forecast? = null
    @SerializedName("current")
    var current: Current? = null
}
