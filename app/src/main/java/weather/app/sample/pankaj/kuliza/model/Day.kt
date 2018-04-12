package weather.app.sample.pankaj.kuliza.model

import com.google.gson.annotations.SerializedName

class Day {

    @SerializedName("condition")
    var condition: Condition? = null
    @SerializedName("maxtemp_c")
    var maxTemp: String? = null
    @SerializedName("mintemp_c")
    var minTemp: String? = null
}
