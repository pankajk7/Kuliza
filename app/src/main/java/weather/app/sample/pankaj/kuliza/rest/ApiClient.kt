package weather.app.sample.pankaj.kuliza.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    val BASE_URL = "http://api.apixu.com/v1/"
    private var retrofit: Retrofit? = null


    val client: Retrofit
        get() {
            retrofit?.let {
                return it
            } ?: run {
                return Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
        }
}
