package weather.app.sample.pankaj.kuliza.utils

import android.content.Context
import android.net.ConnectivityManager
import android.support.annotation.StringRes
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import weather.app.sample.pankaj.kuliza.R
import weather.app.sample.pankaj.kuliza.model.Forecastday
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {

    fun showToast(context: Context, @StringRes string: Int) {
        val toast = Toast.makeText (context, string, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show()
    }

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

    fun longToDateString(dateString: String): String {
        val timeString: String
        val calendar = getCalendarFromDateString(dateString)
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

    fun longToMessageListHeaderDate(dateLong: Long): String {
        val timeString: String
        val calendar = Calendar.getInstance()
        val currentDate = calendar.getTime().getDate()
        calendar.setTimeInMillis(dateLong)
        val inputDate = calendar.getTime().getDate()
        if (inputDate == currentDate) {
            timeString = "Today"
        } else if (inputDate == currentDate - 1) {
            timeString = "Tomorrow"
        } else {
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)
            timeString = dateFormat.format(calendar.getTime())
        }
        return timeString
    }

    val comparator = Comparator<Forecastday> { o1, o2 ->
        o1?.epochTime?.compareTo(o2?.epochTime!!)!!
    }

    fun getCalendarFromDateString(dateString: String): Calendar {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val date = sdf.parse(dateString)
        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }

    fun getCurrentDateString(): String {
        val formatter = SimpleDateFormat("dd MMM yyyy")
        return formatter.format(Date())
    }

    fun isNetworkAvailable(context: Context): Boolean {
        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        } catch (e: Exception) {
        }
        return true
    }
}