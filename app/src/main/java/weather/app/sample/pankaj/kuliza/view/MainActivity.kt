package weather.app.sample.pankaj.kuliza.view

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.loader_layout.*
import kotlinx.android.synthetic.main.main_layout.*
import retrofit2.Response
import weather.app.sample.pankaj.kuliza.R
import weather.app.sample.pankaj.kuliza.model.WeatherData
import weather.app.sample.pankaj.kuliza.presenter.WeatherPresenter
import weather.app.sample.pankaj.kuliza.utils.AppUtils
import weather.app.sample.pankaj.kuliza.view.Adapters.WeatherListAdapter
import java.util.*

class MainActivity : AppCompatActivity(), WeatherPresenter.WeatherDataListener<Response<WeatherData>> {

    private var searchString: String? = null
    private var compositeDisposable: CompositeDisposable? = null

    private var bottomSheetBehaviour: BottomSheetBehavior<*>? = null
    private var weatherListAdapter: WeatherListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppUtils.rotateLoader(this@MainActivity, iv_loader)

        compositeDisposable = CompositeDisposable()
        bottomSheetBehaviour = BottomSheetBehavior.from<View>(view_bottom_sheet)
        setUpBottomSheet()
        btn_get_info.setOnClickListener {
            if (!AppUtils.isNetworkAvailable(this@MainActivity)) {
                AppUtils.showToast(this@MainActivity, R.string.no_internet)
                return@setOnClickListener
            }
            AppUtils.hideKeyboard(it, this@MainActivity)
            searchString = et_loader?.text.toString()
            if (WeatherPresenter.isSearchStringValid(it.context, searchString)) {
                WeatherPresenter.fetchWeatherData(searchString?.trim()!!, this@MainActivity)
                et_loader?.setText("")
                showLoaderView()
            } else {
                showMainView()
            }
        }

        et_loader.postDelayed({
            AppUtils.hideKeyboard(et_loader, this@MainActivity)
        }, 100)
    }

    private fun showMainView() {
        view_main_layout.visibility = View.VISIBLE
        view_loader_layout.visibility = View.GONE
    }

    private fun showLoaderView() {
        view_main_layout.visibility = View.GONE
        view_loader_layout.visibility = View.VISIBLE
    }

    private fun setUpBottomSheet() {
        bottomSheetBehaviour?.peekHeight = 48
        bottomSheetBehaviour?.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    showMainView()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    override fun onSuccess(response: Response<WeatherData>) {
        if (response.isSuccessful) {
            AppUtils.stopAnimationAndHideLoaderView(iv_loader, tv_loader_text)
            toggleBottomSheetState()
            response.body()?.let {
                setValuesForCurrentTemperature(it)
                setUpAdapter(it)
            } ?: run {
                AppUtils.showToast(this@MainActivity, R.string.try_again)
                showMainView()
            }
        }
    }

    override fun onError() {
        AppUtils.showToast(this@MainActivity, R.string.try_again)
        showMainView()
    }

    override fun onSubscribe(d: Disposable) {
        compositeDisposable?.add(d)
    }

    override fun onDestroy() {
        compositeDisposable?.dispose()
        super.onDestroy()
    }

    private fun toggleBottomSheetState() {
        if (bottomSheetBehaviour?.state == BottomSheetBehavior.STATE_EXPANDED)
            bottomSheetBehaviour?.setState(BottomSheetBehavior.STATE_COLLAPSED)
        else
            bottomSheetBehaviour?.setState(BottomSheetBehavior.STATE_EXPANDED)
    }

    private fun setValuesForCurrentTemperature(weatherData: WeatherData) {
        tv_current_temp?.text = getString(R.string.current_temp, weatherData.current?.currentTemp)
        tv_city?.text = searchString
    }

    private fun setUpAdapter(weatherData: WeatherData) {
        rv_weather.layoutManager = LinearLayoutManager(this)
        val forecastdayList = weatherData.forecast?.forecastDayList
        Collections.sort(forecastdayList, AppUtils.comparator)
        if (weatherListAdapter == null) {
            weatherListAdapter = WeatherListAdapter(this@MainActivity, forecastdayList)
            rv_weather.adapter = weatherListAdapter
        } else {
            weatherListAdapter?.setList(forecastdayList)
        }
    }
}
