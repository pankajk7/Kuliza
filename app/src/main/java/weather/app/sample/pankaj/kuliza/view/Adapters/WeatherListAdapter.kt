package weather.app.sample.pankaj.kuliza.view.Adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.row_weather_info_layout.view.*
import weather.app.sample.pankaj.kuliza.R
import weather.app.sample.pankaj.kuliza.model.Forecastday
import weather.app.sample.pankaj.kuliza.utils.AppUtils
import java.util.*

class WeatherListAdapter(private val context: Context,
                         private var forecastDayList: List<Forecastday>?) :
        RecyclerView.Adapter<WeatherListAdapter.ListingViewHolder>() {

    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val currentTimeInMilliSecond = Calendar.getInstance().timeInMillis
    private val imageUrlPrefix = "http:"

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
        holder.itemView.tv_type.text = condition?.text
        setImage(imageUrlPrefix + condition?.icon, holder.itemView.tv_type)
        holder.itemView.tv_temperature.text = context.getString(R.string.temperature,
                day?.minTemp, day?.maxTemp)
        holder.itemView.tv_day.text = AppUtils.longToDateString(
                forecastday?.dateString ?: AppUtils.getCurrentDateString())
    }

    private fun setImage(imageUrl: String, textView: TextView) {
        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                        val drawable = BitmapDrawable(context.resources, bitmap)
                        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                    }
                })
    }

    class ListingViewHolder constructor(itemView: View?) : RecyclerView.ViewHolder(itemView)
}