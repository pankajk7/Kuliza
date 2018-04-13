package weather.app.sample.pankaj.kuliza.utils

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import weather.app.sample.pankaj.kuliza.R
import weather.app.sample.pankaj.kuliza.model.Forecastday
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Pankaj on 11/04/18.
 */
object AppUtils {

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
        calendar.timeZone = TimeZone.getTimeZone("UTC")
        val date = Date(dateLong)
        calendar.time = date
        if (areSameDays(calendar, Calendar.getInstance())) {
            timeString = "Today"
        } else if (isTomorrow(calendar.clone() as Calendar)) {
            timeString = "Tomorrow"
        } else {
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)
            timeString = dateFormat.format(calendar.time)
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

    fun hideKeyboard(view: View?, context: Context?) {
        if (view == null || context == null) return
        val imm = context.getSystemService(
                Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    val comparator = Comparator<Forecastday> { o1, o2 ->
        o1?.epochTime?.compareTo(o2?.epochTime!!)!!
    }
}