package cereal.company.weedsimple

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_fetch_one_product.*
import kotlinx.android.synthetic.main.activity_location.*
import kotlinx.android.synthetic.main.bar_bottom.*

class LocationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);



        settings_profil_tv_layout.setOnClickListener {

            val intentProfil = Intent(this@LocationActivity, TestVolley::class.java)
            startActivity(intentProfil)
        }





         val infoUrl = "https://mobileandweb.alwaysdata.net/profil_kotlin_user.php?email="+ Person.email

        email_profil_tv_layout.text= Person.email
        var rq: RequestQueue = Volley.newRequestQueue(this)

        var jsonFile= JsonObjectRequest(Request.Method.GET,infoUrl,null,
            { response ->



                tel_profil_tv_layout.text=response.getString("telephone")
                rue_profil_tv_layout.text=response.getString("street")
                ville_profil_tv_layout.text=response.getString("ville")

                cp_profil_tv_layout.text=response.getString("cp")

                pays_profil_tv_layout.text=response.getString("pays")





            }, { error ->


                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("INFO")
                dialogBuilder.setMessage("No adress yet")
                dialogBuilder.create().show()

                //Toast.makeText(this@LocationActivity,"No adress yet ", Toast.LENGTH_SHORT).show()



            })
        rq.add(jsonFile)





    }
}