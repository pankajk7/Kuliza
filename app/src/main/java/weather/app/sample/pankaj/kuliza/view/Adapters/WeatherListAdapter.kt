package weather.app.sample.pankaj.kuliza.view.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_weather_info_layout.view.*
import weather.app.sample.pankaj.kuliza.R
import weather.app.sample.pankaj.kuliza.model.Forecastday
import weather.app.sample.pankaj.kuliza.utils.AppUtils
import java.util.*

class WeatherListAdapter(private val context: Context,
                         private var forecastDayList: List<Forecastday>?) :
        RecyclerView.Adapter<WeatherListAdapter.ListingViewHolder>() {

    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val currentTimeInMilliSecong = Calendar.getInstance().timeInMillis
    private val celciusUnicodeString = "(char) 0x00B0"

    fun setList(forecastDayList: List<Forecastday>?) {
        this.forecastDayList = forecastDayList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder {
        return ListingViewHolder(layoutInflater.inflate(R.layout.row_weather_info_layout,
                parent, false))
    }

    override fun getItemCount(): Int {
        return forecastDayList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        val forecastday = forecastDayList?.get(position)
        val day = forecastday?.day
        val condition = day?.condition
        holder.itemView.tv_day.text = AppUtils.longToDateString(forecastday?.epochTime
                ?: currentTimeInMilliSecong)
        holder.itemView.tv_type.text = condition?.text

        holder.itemView.tv_temperature.text = context.getString(R.string.temperature,
                day?.minTemp, day?.maxTemp)
    }

    class ListingViewHolder constructor(itemView: View?) : RecyclerView.ViewHolder(itemView)
}