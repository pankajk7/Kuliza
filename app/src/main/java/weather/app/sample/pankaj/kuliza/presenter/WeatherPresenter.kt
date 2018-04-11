package weather.app.sample.pankaj.kuliza.presenter

import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import weather.app.sample.pankaj.kuliza.BuildConfig
import weather.app.sample.pankaj.kuliza.model.WeatherData
import weather.app.sample.pankaj.kuliza.rest.WeatherApiService

object WeatherPresenter {

    var weatherDataListener: WeatherDataListener<Response<WeatherData>>? = null

    fun fetchWeatherData(weatherData: WeatherDataListener<Response<WeatherData>>) {
        val map = mutableMapOf<String, String>()
        map["key"] = BuildConfig.API_KEY
        map["q"] = "Bangalore"
        map["days"] = "7"
        WeatherApiService.getWeatherData(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Response<WeatherData>> {
                    override fun onSuccess(t: Response<WeatherData>) {
                        weatherDataListener?.onSuccess(t)
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onError(e: Throwable) {

                    }
                })
    }

    interface WeatherDataListener<T> {
        fun onSuccess(@NonNull t: T)
        fun onError(@NonNull e: Throwable)
        fun onSubscribe(@NonNull d: Disposable)
    }
}