package weather.app.sample.pankaj.kuliza.model

import com.google.gson.annotations.SerializedName

class Forecastday {

    @SerializedName("date")
    var dateString: String? = null
    @SerializedName("day")
    var day: Day? = null
    @SerializedName("date_epoch")
    var epochTime: Long? = 0L
}