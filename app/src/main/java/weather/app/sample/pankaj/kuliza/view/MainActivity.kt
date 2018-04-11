package weather.app.sample.pankaj.kuliza.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response
import weather.app.sample.pankaj.kuliza.R
import weather.app.sample.pankaj.kuliza.model.WeatherData
import weather.app.sample.pankaj.kuliza.presenter.WeatherPresenter
import weather.app.sample.pankaj.kuliza.utils.UiUtils

class MainActivity : AppCompatActivity(), WeatherPresenter.WeatherDataListener<Response<WeatherData>> {

    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        UiUtils.rotateLoader(this@MainActivity, iv_loader)

        compositeDisposable = CompositeDisposable()
        WeatherPresenter.fetchWeatherData(this)
    }

    override fun onSuccess(response: Response<WeatherData>) {

    }

    override fun onError(e: Throwable) {

    }

    override fun onSubscribe(d: Disposable) {
        compositeDisposable?.add(d)
    }

    override fun onDestroy() {
        compositeDisposable?.dispose()
        super.onDestroy()
    }
}
