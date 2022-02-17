package cereal.company.weedsimple

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_fetch_one_product.*
import kotlinx.android.synthetic.main.activity_location.*
import kotlinx.android.synthetic.main.activity_location.cp_profil_tv_layout
import kotlinx.android.synthetic.main.activity_location.email_profil_tv_layout
import kotlinx.android.synthetic.main.activity_location.pays_profil_tv_layout
import kotlinx.android.synthetic.main.activity_location.rue_profil_tv_layout
import kotlinx.android.synthetic.main.activity_location.settings_profil_tv_layout
import kotlinx.android.synthetic.main.activity_location.tel_profil_tv_layout
import kotlinx.android.synthetic.main.activity_location.ville_profil_tv_layout
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.alert_remove_product.*
import kotlinx.android.synthetic.main.alert_remove_product.view.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        settings_profil_tv_layout.setOnClickListener {

            val intentProfil = Intent(this@ProfileActivity, AdressActivity::class.java)
            startActivity(intentProfil)
        }
        backFleche.setOnClickListener {

           finish()
        }


        val infoUrl = "https://reggaerencontre.com/profil_kotlin_user.php?email="+ Person.email

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


        delete_tv_profile.setOnClickListener {

           //@TODO alert pour supprimer le user


            val dialogBuilderSpr = AlertDialog.Builder(this)
            dialogBuilderSpr.setTitle("Message")
            dialogBuilderSpr.setMessage(getString(R.string.supprimer_acount_msg))
                .setPositiveButton(android.R.string.yes) { dialog, which ->


                /*Toast.makeText(applicationContext,
                    android.R.string.yes, Toast.LENGTH_SHORT).show()*/

                    val deleteUserUrl = "https://reggaerencontre.com/delete_profil_kotlin_user.php?email="+ Person.email

                    val requestD: RequestQueue = Volley.newRequestQueue(this)

                    val stringRequestD = StringRequest(Request.Method.GET,deleteUserUrl, {


                            response ->


                        if (response.equals("Votre compte a été supprimé.")){


                            //Toast.makeText(this@TestVolley,response, Toast.LENGTH_LONG).show()

                            val dialogBuilder = AlertDialog.Builder(this)
                            dialogBuilder.setTitle("Sorry")
                            dialogBuilder.setMessage(response)
                            dialogBuilder.create().show()
                        }



                    },{

                            error->


                        val dialogBuilder = AlertDialog.Builder(this)
                        dialogBuilder.setTitle("alert")
                        dialogBuilder.setMessage(error.message )
                        dialogBuilder.create().show()


                    })
                    requestD.add(stringRequestD)


            }
                .setNegativeButton(android.R.string.no) { dialog, which ->
                    dialog.dismiss()
                }

            dialogBuilderSpr.create().show()






        }
    }
}