package cereal.company.weedsimple

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import cereal.company.weedsimple.Person.Companion.tourOfSpin
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.ads.*
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONObject
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

class SpinActivity : AppCompatActivity(), Animation.AnimationListener {
    // ADMOB
    private var mRewardedAd: RewardedAd? = null
    private final var TAG = "SpinActivity"
    private var count = 0
    private var toursSpins = 0
    private var flag = false
    val userEmailConnected = Person.email
    var valeurOfPoint : Double = 0.00000025
    private var powerButton: ImageView? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spin)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // AFFICHER  LES POINTS DE FIDELITES
        val urlPts = "https://reggaerencontre.com/fetchPointsF.php?email_users_pts=$userEmailConnected"

        val requestPoints: RequestQueue = Volley.newRequestQueue(this)
        val stringR= JsonObjectRequest(Request.Method.GET,urlPts,null,{

            response->
                pts_user.text= response.getInt("points").toString()
                tour_user.text= response.getInt("tour_spin").toString()
               var spinRide = response.getInt("tour_spin")

                val df = DecimalFormat("#.###")
                df.roundingMode = RoundingMode.CEILING
                val euroPpoint =((valeurOfPoint * response.getInt("points"))/1)
                val convPtE =df.format(euroPpoint)
                convert_points.text= "${ convPtE } €"
               tourOfSpin = response.getInt("tour_spin")
               powerButton = findViewById(R.id.powerButton)
               powerButton!!.setOnTouchListener(PowerTouchListener())
               intSpinner()
               spinEnabled()


            //ADMOB

            ad_btn_spin.setOnClickListener {

                var adRequest = AdRequest.Builder().build()


                // INITIALIZE ADMOB
                MobileAds.initialize(
                    this
                ) { }

                // ca-app-pub-5223452786617280/6850613721   pour production
                RewardedAd.load(this,"ca-app-pub-3940256099942544/5224354917", adRequest, object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.d(TAG, adError?.message)
                        mRewardedAd = null
                    }

                    override fun onAdLoaded(rewardedAd: RewardedAd) {
                        Log.d(TAG, "Ad was loaded.")
                        mRewardedAd = rewardedAd

                    }
                })



                if (mRewardedAd != null) {
                    mRewardedAd?.show(this, OnUserEarnedRewardListener() {

                        //TODO gerer les tours de spin
                        alert(getString(R.string.recompense_modal))
                        spinRide ++
                        dissabledSpin()
                        ad_btn_spin.setEnabled(false)
                        adRequest = AdRequest.Builder().build()


                        tour_user.text=(toursSpins +1).toString()
                        //AJOUT UN TOUR DE SPIN
                        val TourSpinURL = "https://reggaerencontre.com/tourSpin.php?email_users_pts=" +
                                userEmailConnected
                        val requestTspin: RequestQueue = Volley.newRequestQueue(this)

                        val stringRequest = StringRequest(
                            Request.Method.GET,TourSpinURL,{
                                    response ->

                            }, { error ->


                                val dialogBuilder = AlertDialog.Builder(this)
                                dialogBuilder.setTitle("Message")
                                dialogBuilder.setMessage("error.message")
                                dialogBuilder.create().show()
                            })

                        requestTspin.add(stringRequest)

                        //startActivity(Intent(this@SpinActivity,SpinActivity::class.java))

                       var lesTours = toursSpins +1
                        println(lesTours)





                    })
                } else {
                    Log.d(TAG, "The rewarded ad wasn't ready yet.")
                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setTitle("Message")
                    dialogBuilder.setMessage(getString(R.string.pub_no_ready))
                    dialogBuilder.create().show()

                }



            }
            //ADMOB FIN

        }, { error ->


                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage("error.message")
                dialogBuilder.create().show()
            })

        requestPoints.add(stringR)


                /**get Id*/

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


    // MISE A JOUR DES POINTS EN BASE, ENLEVE UN TOUR
 if(prizes[prizeIndex] == 400 ||
     prizes[prizeIndex] == 500
     || prizes[prizeIndex] == 250
 ){



     winAnimation_lott.visibility = View.VISIBLE
 }

        val pointURL = "https://reggaerencontre.com/pts_ktl.php?email_users_pts=" +
                userEmailConnected +
                "&points=" + prizes[prizeIndex]


        val requestPts: RequestQueue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(
            Request.Method.GET,pointURL,{
                    response ->
                println( tourOfSpin)
                tour_user.text=tourOfSpin.toString()



            }, { error ->


                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage("error.message")
                dialogBuilder.create().show()
            })

        requestPts.add(stringRequest)

        prizeText = getString(R.string.prize) + prizes[prizeIndex]
        spinEnabled()
        Handler().postDelayed(

            {

                //lance le mainActivity
                val intent = Intent(this@SpinActivity, MainActivity::class.java )
                intent.putExtra("dispoPub", false)
                startActivity(intent)
                finish()

            },7000
        )

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
        infoText!!.text = getString(R.string.touner_spin)
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

    private fun alert(message: String) {
        val unealert = AlertDialog.Builder(this@SpinActivity, R.style.AlertDialogTheme)
            .setTitle(getString(R.string.free_ride))
            .setMessage(message)
            .setPositiveButton(
                "X"
            ) { dialog, which ->
                dialog.dismiss()
               ;
            }
            .create()
        unealert.window!!.setLayout(700, 500)
        unealert.show()
    }

    private fun spinEnabled(){
        if(tourOfSpin <1){

            powerButton?.setEnabled(false)
            ad_btn_spin.setEnabled(true)

        }
    }

    private fun dissabledSpin(){

            powerButton?.setEnabled(true)

    }






}


