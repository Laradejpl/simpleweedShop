package cereal.company.weedsimple

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        val btnsubscribe = findViewById<TextView>(R.id.tv_subscribe)
        val btnhome = findViewById<ImageView>(R.id.home_iv)
        val btlogin = findViewById<Button>(R.id.btnLogin)
        btnsubscribe.setOnClickListener{

            val intent = Intent(this@LoginActivity, SignUpLayout::class.java)
            startActivity(intent)


        }

        btnhome.setOnClickListener{

            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)


        }


        btlogin.setOnClickListener {


            val loginURL = "https://reggaerencontre.com/login_app_user.php?email=" +
                    activity_main_edtEmail.text.toString() +
                    "&pass=" + activity_main_edtPassword.text.toString()


            val requestQ: RequestQueue = Volley.newRequestQueue(this)

            val stringRequest = StringRequest(Request.Method.GET,loginURL, {


                response ->

                if (response.equals("Bienvenue au Just CBD Shop")){
                    // pour garder une trace de la personne qui s'est logger ou enregistrer.
                    Person.email = activity_main_edtEmail.text.toString()

                    Toast.makeText(this@LoginActivity, response, Toast.LENGTH_SHORT).show()
                    val homeIntent = Intent(this, MainActivity::class.java)
                    startActivity(homeIntent)


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

            requestQ.add(stringRequest)


        }
    }
}