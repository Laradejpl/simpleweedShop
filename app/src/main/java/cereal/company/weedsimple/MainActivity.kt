package cereal.company.weedsimple

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)






        //variables de menu
        val menuSide = findViewById<NavigationView>(R.id.side_menu)
        val loginImg  = findViewById<ImageView>(R.id.login_iv)
        val homeImg  = findViewById<ImageView>(R.id.home_iv)
        val closetxt =findViewById<TextView>(R.id.close_btn_side_menu)


            loginImg.setOnClickListener {

                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)

            }


        homeImg.setOnClickListener {
            menuSide.visibility = View.VISIBLE

        }

        closetxt.setOnClickListener {

            menuSide.visibility = View.GONE

        }



    }
}