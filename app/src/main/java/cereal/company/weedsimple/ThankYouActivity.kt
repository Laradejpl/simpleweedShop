package cereal.company.weedsimple

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_thank_you.*

class ThankYouActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thank_you)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //@TODO envoyer mail de confirmation de la commande
        val userEmailForDelivery = Person.email
        val forEmailConfirmURL = "https://mobileandweb.alwaysdata.net/deliveryMailKt.php?email=" +
                userEmailForDelivery
        val requestDeli: RequestQueue = Volley.newRequestQueue(this)

        val stringRequestD = StringRequest(
            Request.Method.GET,forEmailConfirmURL,{
                    response ->

            }, { error ->


                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage("error.message")
                dialogBuilder.create().show()
            })

        requestDeli.add(stringRequestD)

        Handler().postDelayed(

            {

                //lance le mainActivity
                startActivity(Intent(this@ThankYouActivity,MainActivity::class.java))
                finish()

            },4000
        )

        politic_tv.setOnClickListener {
            val intent = Intent(this@ThankYouActivity, PoliticActivity::class.java )
            startActivity(intent)
        }





    }
}