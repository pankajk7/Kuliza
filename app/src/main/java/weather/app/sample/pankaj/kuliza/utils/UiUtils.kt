package weather.app.sample.pankaj.kuliza.utils

import android.content.Context
import android.view.animation.AnimationUtils
import android.widget.ImageView
import weather.app.sample.pankaj.kuliza.R

/**
 * Created by Pankaj on 11/04/18.
 */
object UiUtils {

    public fun rotateLoader(context: Context, imageView: ImageView) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.rotate)
        animation.duration = 2000L
        imageView.startAnimation(animation)
    }
}