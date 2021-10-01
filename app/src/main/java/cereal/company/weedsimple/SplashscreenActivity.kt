package cereal.company.weedsimple

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager

class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)




        Handler().postDelayed(

            {

                //lance le mainActivity
                startActivity(Intent(this@SplashscreenActivity,MainActivity::class.java))
                finish()

            },3000
        )
    }
}