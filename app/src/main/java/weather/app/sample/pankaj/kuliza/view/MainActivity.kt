package weather.app.sample.pankaj.kuliza.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import weather.app.sample.pankaj.kuliza.R
import weather.app.sample.pankaj.kuliza.utils.UiUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        UiUtils.rotateLoader(this@MainActivity, iv_loader)
    }


}
