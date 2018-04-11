package weather.app.sample.pankaj.kuliza.model

import com.google.gson.annotations.SerializedName

class Forecast {

    @SerializedName("forecastday")
    var forecastDayList: List<Forecastday>? = null
}