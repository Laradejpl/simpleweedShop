package cereal.company.weedsimple

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_main.*
import java.math.RoundingMode
import java.text.DecimalFormat
import java.lang.reflect.Method
import java.math.BigDecimal

class SpinActivity : AppCompatActivity(), Animation.AnimationListener {

    private var count = 0
    private var flag = false
    val userEmailConnected = Person.email
    var valeurOfPoint : Double = 0.000025
    private var powerButton: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spin)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // AFFICHER  LES POINTS DE FIDELITES
        val urlPts = "https://reggaerencontre.com/fetchPointsF.php?email_users_pts=$userEmailConnected"

        val requestPoints: RequestQueue = Volley.newRequestQueue(this)
        val stringR = StringRequest(
            Request.Method.GET,urlPts,{

            response->
                pts_user.text= "${ response.toString()} Points"
                val df = DecimalFormat("#.###")
                df.roundingMode = RoundingMode.CEILING


                val euroPpoint =((valeurOfPoint * response.toLong())/1)
                val convPtE =df.format(euroPpoint)


                convert_points.text= "${ convPtE } €"

        }, { error ->


                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage("error.message")
                dialogBuilder.create().show()
            })

        requestPoints.add(stringR)

                /**get Id*/
        powerButton = findViewById(R.id.powerButton)
        powerButton!!.setOnTouchListener(PowerTouchListener())
        intSpinner()
    }

    /**
     * All the vars you need
     * */
    val prizes = intArrayOf(0,0,0,0,500,250,0,0,0,400,0,12000)

    private var mSpinDuration :Long = 0
    private var mSpinRevolution = 0f
    var pointerImageView:ImageView? = null
    var infoText: TextView? = null
    var prizeText = "N/A"

    private fun intSpinner() {
        pointerImageView = findViewById(R.id.imageWheel)
        infoText = findViewById(R.id.infoText)
    }

    fun startSpinner() {
        mSpinRevolution = 3600f
        mSpinDuration = 5000

        if (count >= 30){
            mSpinDuration = 1000
            mSpinRevolution = (3600 * 2).toFloat()
        }
        if (count >= 60){
            mSpinDuration = 15000
            mSpinRevolution = (3600 * 3).toFloat()

        }

        // Final point of rotation defined right here

        val end = Math.floor(Math.random() * 3600).toInt() // random : 0-360
        val numOfPrizes = prizes.size // quantity of prize
        val degreesPerPrize = 360/numOfPrizes // size of sector per prize in degrees
        val shift = 0 // shit where the arrow points
        val prizeIndex = (shift + end) % numOfPrizes




        val pointURL = "https://reggaerencontre.com/pts_ktl.php?email_users_pts=" +
                userEmailConnected +
                "&points=" + prizes[prizeIndex]

        val requestPts: RequestQueue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(
            Request.Method.GET,pointURL,{
                    response ->



            }, { error ->


                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage("error.message")
                dialogBuilder.create().show()
            })

        requestPts.add(stringRequest)

        prizeText = "Le Prix est : ${prizes[prizeIndex]}"
        val rotateAnim = RotateAnimation(
            0f,mSpinRevolution + end,
            Animation.RELATIVE_TO_SELF,
            0.5f,Animation.RELATIVE_TO_SELF,0.5f
        )
        rotateAnim.interpolator = DecelerateInterpolator()
        rotateAnim.repeatCount = 0
        rotateAnim.duration = mSpinDuration
        rotateAnim.setAnimationListener(this)
        rotateAnim.fillAfter = true
        pointerImageView!!.startAnimation(rotateAnim)

    }

    override fun onAnimationStart(p0: Animation?) {
        infoText!!.text = "Tournez..."
    }

    override fun onAnimationEnd(p0: Animation?) {
        infoText!!.text = prizeText
    }

    override fun onAnimationRepeat(p0: Animation?) {}

    private inner class PowerTouchListener: View.OnTouchListener {
        override fun onTouch(p0: View?, motionEvent: MotionEvent?): Boolean {

            when(motionEvent!!.action){
                MotionEvent.ACTION_DOWN ->{
                    flag = true
                    count = 0
                    Thread{
                        while (flag){
                            count++
                            if (count == 100){
                                try {
                                    Thread.sleep(100)
                                }catch (e: InterruptedException) {
                                    e.printStackTrace()
                                }
                                count = 0
                            }
                            try {
                                Thread.sleep(10)
                            }
                            catch (e: InterruptedException){
                                e.printStackTrace()
                            }
                        }
                    }.start()
                    return true
                }
                MotionEvent.ACTION_UP ->{
                    flag = false
                    startSpinner()
                    return false
                }

            }


            return false
        }

    }
}