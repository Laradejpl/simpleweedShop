package cereal.company.weedsimple

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import cereal.company.weedsimple.utils.BaseActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_paypal_earn.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.layout_oublie_dialog.view.*
import kotlinx.android.synthetic.main.layout_paypal_adress.view.*
import kotlinx.android.synthetic.main.side_menu_nav.*
import java.math.RoundingMode
import java.text.DecimalFormat

class PaypalEarnActivity : BaseActivity() {
    private var valeurOfPoints : Double = 0.0000025
    val emailConnectedPaypal = Person.email
    var valueCart = 0.0;
    var newCagnotte = 0.0
    var pointsRestant = 0
    var newCagnotteToString = ""
    var carteid = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paypal_earn)


        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bbacArrow.setOnClickListener {

            finish()
        }

        val urlPoints = "https://reggaerencontre.com/fetchPointsF.php?email_users_pts=$emailConnectedPaypal"

        val requestPtss: RequestQueue = Volley.newRequestQueue(this)
        val stringRqs= JsonObjectRequest(Request.Method.GET,urlPoints,null, {

                response ->

            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            val euroPpoints = ((valeurOfPoints * response.getInt("points")) / 1)
            val convPtEs = df.format(euroPpoints)
            cagnotte_paypal_tv.text = "${convPtEs} €"

            //compare la valeur des carte cliquer  paypal avec la cagnotte
            println(response.getInt("points"))


            if(response.getInt("points") >= 2000  ){
                paypal_cart_cup_1.setImageResource(R.drawable.qzzvert)
                if (response.getInt("points") >= 1002000){
                    paypal_cart_cup_2.setImageResource(R.drawable.pinkcup)
                }
                if (response.getInt("points") >= 6002000){
                    paypal_cart_cup_3.setImageResource(R.drawable.coffretour)
                }
            }
            else{
                paypal_cart_cup_1.setImageResource(R.drawable.qzzcadena)

            }


            paypal_cart_earn_1.setOnClickListener {
                valueCart = 0.50
                carteid = 0.50

                if(euroPpoints > valueCart){
                    fetchAdressPayPal()

                    newCagnotte =  euroPpoints - valueCart
                    newCagnotteToString = df.format(newCagnotte)


                }else{
                    //snackbar alert
                    showErrorSnackBar("Vous avez pas encore assez de cagnotte",true)

                }

            }
            paypal_cart_earn_2.setOnClickListener {
                valueCart = 5.0
                carteid = 5.0

                if(euroPpoints > valueCart){
                    fetchAdressPayPal()

                    newCagnotte =  euroPpoints - valueCart
                    newCagnotteToString = df.format(newCagnotte)


                }else{
                    //snackbar alert
                    showErrorSnackBar("Vous avez pas encore assez de cagnotte",true)

                }

            }
            paypal_cart_earn_3.setOnClickListener {
                valueCart = 10.0
                carteid = 10.0

                if(euroPpoints > valueCart){
                    fetchAdressPayPal()

                    newCagnotte =  euroPpoints - valueCart
                    newCagnotteToString = df.format(newCagnotte)


                }else{
                    //snackbar alert
                    showErrorSnackBar("Vous avez pas encore assez de cagnotte",true)

                }

            }
            paypal_cart_earn_4.setOnClickListener {
                valueCart = 15.0
                carteid = 15.0

                if(euroPpoints > valueCart){
                    fetchAdressPayPal()

                    newCagnotte =  euroPpoints - valueCart
                    newCagnotteToString = df.format(newCagnotte)


                }else{
                    //snackbar alert
                    showErrorSnackBar("Vous avez pas encore assez de cagnotte",true)

                }

            }
            paypal_cart_earn_5.setOnClickListener {
                valueCart = 20.0
                carteid = 20.0

                if(euroPpoints > valueCart){
                    fetchAdressPayPal()

                    newCagnotte =  euroPpoints - valueCart
                    newCagnotteToString = df.format(newCagnotte)


                }else{
                    //snackbar alert
                    showErrorSnackBar("Vous avez pas encore assez de cagnotte",true)

                }

            }
            paypal_cart_earn_6.setOnClickListener {
                valueCart = 30.0
                carteid = 30.0

                if(euroPpoints > valueCart){
                    fetchAdressPayPal()

                    newCagnotte =  euroPpoints - valueCart
                    newCagnotteToString = df.format(newCagnotte)


                }else{
                    //snackbar alert
                    showErrorSnackBar("Vous avez pas encore assez de cagnotte",true)

                }

            }



        },{ error ->



        })

        requestPtss.add(stringRqs)




    }


    fun UpdatePoints(){

        val pointUpdateURL = "https://reggaerencontre.com/updatePointFromPP.php?email_users_pts=" +
                emailConnectedPaypal +
                "&points=" + pointsRestant + "&carte=" + carteid


        val requestUPts: RequestQueue = Volley.newRequestQueue(this)

        val stringRequestU = StringRequest(
            Request.Method.GET,pointUpdateURL,{
                    response ->





            }, { error ->


                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage("error.message")
                dialogBuilder.create().show()
            })

        requestUPts.add(stringRequestU)

    }

    fun fetchAdressPayPal(){

        val payPalAdressURL = "https://reggaerencontre.com/requestPayPAdress.php?email=" +
                emailConnectedPaypal

        val requestUPp: RequestQueue = Volley.newRequestQueue(this)

        val stringRequestPp = StringRequest(
            Request.Method.GET,payPalAdressURL,{
                    response ->

                if (response.equals("pas Paypal adress")){
                    // dialog pour rentrez l'identifiant paypal.me

                    val view = View.inflate(this@PaypalEarnActivity,R.layout.layout_paypal_adress,null)
                    var builder = AlertDialog.Builder(this@PaypalEarnActivity)
                    builder.setView(view)
                    val dialog =builder.create()
                    dialog.show()
                    // dialog.setIcon(R.drawable.registration)
                    dialog.window?.setBackgroundDrawableResource(R.drawable.bg_transparent)
                    var layout : ConstraintLayout = view.findViewById(R.id.paypal_dialog)




                    view.btn_paypal_adress.setOnClickListener {

                        var adressPayPal = view.edt_paypal.text.toString()
                        val adressPayPalUrl = "https://reggaerencontre.com/insertPaypalAd.php?email=" + emailConnectedPaypal  + "&Paypal_adress=" + adressPayPal
                        val requestQPpal: RequestQueue = Volley.newRequestQueue(this)
                        val stringRequestPayp = StringRequest(Request.Method.GET,adressPayPalUrl, {
                                response ->

                            if (response.equals("Votre Paypal adress a été bien enregistré")){
                                // pour garder une trace de la personne qui s'est logger ou enregistrer.
                                //Toast.makeText(this@LoginActivity, response, Toast.LENGTH_SHORT).show()
                                UpdatePoints()
                                showErrorSnackBar(response,false)
                                dialog.dismiss()

                            } else{

                                val dialogBuilder = AlertDialog.Builder(this)
                                dialogBuilder.setTitle("Message")
                                dialogBuilder.setMessage(response)
                                dialogBuilder.create().show()

                            }

                        }, { error ->


                            val dialogBuilder = AlertDialog.Builder(this)
                            dialogBuilder.setTitle("Message")
                            dialogBuilder.setMessage("error.message")
                            dialogBuilder.create().show()
                        })

                        requestQPpal.add(stringRequestPayp)

                    }


                }else{
                    pointsRestant = ((newCagnotte * 1)/ valeurOfPoints).toInt()
                    cagnotte_paypal_tv.text = newCagnotteToString
                    UpdatePoints()
                    showErrorSnackBar("Vous avez gagnez $carteid Euro  de paypal",false)
                }

            }, { error ->


                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage("error.message")
                dialogBuilder.create().show()
            })

        requestUPp.add(stringRequestPp)



    }
}