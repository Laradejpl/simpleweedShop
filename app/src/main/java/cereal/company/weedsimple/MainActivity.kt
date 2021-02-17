package cereal.company.weedsimple

import android.content.Intent
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
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var dotsLayout: LinearLayout
    lateinit var mpager: ViewPager
    lateinit var toolbar : androidx.appcompat.widget.Toolbar
    var path:IntArray = intArrayOf(R.drawable.teat,R.drawable.bocalcbd,R.drawable.marleybeer,R.drawable.blondehair)
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


        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

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

}