package cereal.company.weedsimple

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.side_menu_nav.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var dotsLayout: LinearLayout
    lateinit var mpager: ViewPager
    lateinit var toolbar : androidx.appcompat.widget.Toolbar
    var path:IntArray = intArrayOf(R.drawable.teat,R.drawable.bocalcbd,R.drawable.fruitcbd,R.drawable.blondehair)
    lateinit var dots:Array<ImageView>
    lateinit var adapter: PageView
    var currentPage: Int = 0
    lateinit var timer: Timer
    val DELAY_MS: Long = 5000
    val PERIOD_MS: Long = 5000






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





                       //variables de menu

        val hamenu = findViewById<ImageView>(R.id.hamburger)
        val menuSide = findViewById<NavigationView>(R.id.side_menu)
        val loginImg  = findViewById<ImageView>(R.id.login_iv)
        val closetxt =findViewById<TextView>(R.id.close_btn_side_menu)
        val instalink = findViewById<ImageView>(R.id.insta)
        val facebooklink = findViewById<ImageView>(R.id.facbook)
        val whatsapplink = findViewById<ImageView>(R.id.whatapp)
        val telephonelink = findViewById<ImageView>(R.id.phone)
        val phoneNumber = "0769754123"



        //toolbar = findViewById(R.id.toolbar)
        //setSupportActionBar(toolbar)

        mpager = findViewById(R.id.pager) as ViewPager
        adapter  = PageView(this,path)
        mpager.adapter = adapter
        dotsLayout = findViewById(R.id.dotsLayout) as LinearLayout



        createDots(0)

        updatePage()

        mpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                currentPage = position
                createDots(position)

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })


        instalink.setOnClickListener {
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.instagram.com/uwallandroidapps/")
            startActivity(openURL)
        }
        facebooklink.setOnClickListener {
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.facebook.com/")
            startActivity(openURL)
        }

        whatsapplink.setOnClickListener {
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.whatsapp.com/")
            startActivity(openURL)
        }

        login_tv_side_menu.setOnClickListener {

            val intentLog = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intentLog)
        }


        sign_up_tv_side_menu.setOnClickListener {

            val intentSign = Intent(this@MainActivity, SignUpLayout::class.java)
            startActivity(intentSign)
        }



        loginImg.setOnClickListener {

                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)

            }




        hamenu.setOnClickListener {
            menuSide.visibility = View.VISIBLE

        }

        closetxt.setOnClickListener {

            menuSide.visibility = View.GONE

        }

        telephonelink.setOnClickListener {

            dialPhoneNumber()
        }



    }



    fun updatePage()
    {


        var handler= Handler()
        val Update: Runnable = Runnable {

            if (currentPage == path.size){

                currentPage = 0
            }

            mpager.setCurrentItem(currentPage++,true)
        }

        timer = Timer()
        timer.schedule(object : TimerTask(){
            override fun run() {
               handler.post(Update)
            }


        },DELAY_MS,PERIOD_MS )
    }

    fun createDots(position : Int){

        if (dotsLayout != null){

            dotsLayout.removeAllViews()
        }

        dots = Array(path.size,{i -> ImageView(this)})

        for (i in 0..path.size -1){


            dots[i] = ImageView(this)
            if (i == position){


                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots))
            }else{

                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.inactive_dots))
            }

            var params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            params.setMargins(4,0,4,0)
            dotsLayout.addView(dots[i],params)

        }


    }

    fun dialPhoneNumber(phoneNumber: String ="0769754123") {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

}