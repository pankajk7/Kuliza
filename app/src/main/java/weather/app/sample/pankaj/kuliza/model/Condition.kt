package weather.app.sample.pankaj.kuliza.model

import com.google.gson.annotations.SerializedName

class Condition {

    @SerializedName("text")
    var text: String? = null
    @SerializedName("icon")
    var icon: String? = null
}