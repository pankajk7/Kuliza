package weather.app.sample.pankaj.kuliza.presenter

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import weather.app.sample.pankaj.kuliza.BuildConfig
import weather.app.sample.pankaj.kuliza.R
import weather.app.sample.pankaj.kuliza.model.WeatherData
import weather.app.sample.pankaj.kuliza.rest.WeatherApiService

object WeatherPresenter {

    fun isSearchStringValid(context: Context, searchString: String?): Boolean {
        if (TextUtils.isEmpty(searchString)) {
            Toast.makeText(context, context.getString(R.string.fetch_info),
                    Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    fun fetchWeatherData(searchString: String, weatherData: WeatherDataListener<Response<WeatherData>>) {
        val map = mutableMapOf<String, String>()
        map["key"] = BuildConfig.API_KEY
        map["q"] = searchString
        map["days"] = "7"
        WeatherApiService.getWeatherData(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Response<WeatherData>> {
                    override fun onSuccess(t: Response<WeatherData>) {
                        if (t.isSuccessful)
                            weatherData.onSuccess(t)
                        else
                            weatherData.onError()
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onError(e: Throwable) {
                        weatherData.onError()
                    }
                })
    }

    interface WeatherDataListener<T> {
        fun onSuccess(@NonNull response: T)
        fun onError()
        fun onSubscribe(@NonNull d: Disposable)
    }
}