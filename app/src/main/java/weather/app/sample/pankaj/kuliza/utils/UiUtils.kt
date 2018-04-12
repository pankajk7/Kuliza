package weather.app.sample.pankaj.kuliza.utils

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import weather.app.sample.pankaj.kuliza.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Pankaj on 11/04/18.
 */
object UiUtils {

    fun rotateLoader(context: Context, imageView: ImageView?) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.rotate)
        animation.duration = 2000L
        imageView?.startAnimation(animation)
    }

    fun stopAnimationAndHideLoaderView(imageView: ImageView?, textView: TextView?) {
        textView?.visibility = View.GONE
        imageView?.let {
            it.clearAnimation()
            it.visibility = View.GONE
        }
    }

    fun longToDateString(dateLong: Long): String {
        val timeString: String
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateLong
        if (areSameDays(calendar, Calendar.getInstance())) {
            timeString = "Today"
        } else if (isTomorrow(calendar)) {
            timeString = "Tomorrow"
        } else {
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)
            timeString = dateFormat.format(calendar.getTime())
        }
        return timeString
    }

    private fun areSameDays(dayOne: Calendar?, dayTwo: Calendar?): Boolean {
        return !(dayOne == null || dayTwo == null) && (dayOne.get(Calendar.DATE) == dayTwo.get(Calendar.DATE)
                && dayOne.get(Calendar.MONTH) == dayTwo.get(Calendar.MONTH)
                && dayOne.get(Calendar.YEAR) == dayTwo.get(Calendar.YEAR))
    }

    private fun isTomorrow(inDate: Calendar): Boolean {
        val previousDate = Calendar.getInstance()
        previousDate.add(Calendar.DATE, 1)
        return areSameDays(inDate, previousDate)
    }
}