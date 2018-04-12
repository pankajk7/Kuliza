package weather.app.sample.pankaj.kuliza.view

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response
import weather.app.sample.pankaj.kuliza.R
import weather.app.sample.pankaj.kuliza.model.WeatherData
import weather.app.sample.pankaj.kuliza.presenter.WeatherPresenter
import weather.app.sample.pankaj.kuliza.utils.UiUtils

class MainActivity : AppCompatActivity(), WeatherPresenter.WeatherDataListener<Response<WeatherData>> {

    private var searchString: String? = null
    private var compositeDisposable: CompositeDisposable? = null

    private var loaderImageView: ImageView? = null
    private var loaderTextView: TextView? = null
    private var editText: EditText? = null
    private var button: Button? = null
    private var currentTempTextView: TextView? = null
    private var cityTextView: TextView? = null
    private var bottomSheetBehaviour: BottomSheetBehavior<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loaderImageView = view_loader_layout.findViewById(R.id.iv_loader)
        loaderTextView = view_loader_layout.findViewById(R.id.tv_loader_text)
        editText = view_main_layout.findViewById(R.id.et_loader)
        button = view_main_layout.findViewById(R.id.btn_get_info)
        currentTempTextView = view_bottom_sheet.findViewById(R.id.tv_current_temp)
        cityTextView = view_bottom_sheet.findViewById(R.id.tv_city)

        UiUtils.rotateLoader(this@MainActivity, loaderImageView)

        compositeDisposable = CompositeDisposable()
        bottomSheetBehaviour = BottomSheetBehavior.from<View>(view_bottom_sheet)
        setUpBottomSheet()
        button?.setOnClickListener {
            UiUtils.hideKeyboard(it, this@MainActivity)
            searchString = editText?.text.toString()
            if (WeatherPresenter.isSearchStringValid(it.context, searchString)) {
                WeatherPresenter.fetchWeatherData(searchString?.trim()!!, this@MainActivity)
                editText?.setText("")
                showLoaderView()
            } else {
                showMainView()
            }
        }
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
            UiUtils.stopAnimationAndHideLoaderView(loaderImageView, loaderTextView)
            toggleBottomSheetState()
            response.body()?.let {
                setValuesForCurrentTemperature(it)
            } ?: run {
                Toast.makeText(this@MainActivity, getString(R.string.try_again),
                        Toast.LENGTH_LONG).show()
                showMainView()
            }
        }
    }

    override fun onError() {
        Toast.makeText(this@MainActivity, getString(R.string.try_again),
                Toast.LENGTH_LONG).show()
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
        currentTempTextView?.text = weatherData.current?.currentTemp
        cityTextView?.text = searchString
    }
}
