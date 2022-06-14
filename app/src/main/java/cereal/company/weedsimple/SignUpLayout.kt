package cereal.company.weedsimple

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_sign_up_layout.*
import kotlinx.android.synthetic.main.bar_bottom.*
import java.lang.Exception

class SignUpLayout : AppCompatActivity() {
     lateinit var  newLetterCheckBox :CheckBox
     //lateinit var  majorityCheckBox:CheckBox
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_layout)


        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

         val btnhome = findViewById<ImageView>(R.id.home_iv)

        retourhome.setOnClickListener {
           // retour a l'activié davant
            finish()

        }

         btnhome.setOnClickListener {
             val intent = Intent(this@SignUpLayout, MainActivity::class.java)
             startActivity(intent)

         }

         login_iv.setOnClickListener {
             val intent = Intent(this@SignUpLayout, LoginActivity::class.java)
             startActivity(intent)

         }

         cart_iv.setOnClickListener {

             val intentcart = Intent(this@SignUpLayout, CartProductsActivity::class.java)
             startActivity(intentcart)

         }

        sign_up_layout_btn_TextLogin.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)


        }


        sign_up_layout_btnSubmit.setOnClickListener {


            if (sign_up_layout_edtPassword.text.toString().equals(sign_up_layout_edtConfirmPass.text.toString())){

                newLetterCheckBox = findViewById(R.id.checkbox_register)
               // majorityCheckBox = findViewById(R.id.checkbox_18ans)


                var  signUpUrl = "https://mobileandweb.alwaysdata.net/join_new_user.php?email=" +
                        sign_up_layout_edtEmail.text.toString() +
                        "&username=" +
                        sign_up_layout_edtUsername.text.toString() + "&pass=" + sign_up_layout_edtPassword.text.toString()+
                        "&newletter=" + newLetterCheckBox.isChecked.toString()


                val requestQ: RequestQueue = Volley.newRequestQueue(this)
                val stringRequest = StringRequest(Request.Method.GET,signUpUrl, {


                    response ->

                    // the message must be the same as the php file

                    if (response.equals("Un utilisateur avec la meme adresse existe.")){


                      Toast.makeText(this@SignUpLayout,"un email existe déja",Toast.LENGTH_LONG).show()


                    }else{

                        Person.email = sign_up_layout_edtEmail.text.toString()


                        //Toast.makeText(this@SignUpLayout, response, Toast.LENGTH_SHORT).show()

                        val dialogBuilder = AlertDialog.Builder(this)
                        dialogBuilder.setTitle("alert")
                        dialogBuilder.setMessage(response)
                        dialogBuilder.create().show()


                        //val homeIntent = Intent(this, MainActivity::class.java)
                        //startActivity(homeIntent)


                    }



                }, { error->


                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setTitle("alert")
                    dialogBuilder.setMessage(error.message )
                    dialogBuilder.create().show()
                })

                requestQ.add(stringRequest)



            }else{

                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("alert")
                dialogBuilder.setMessage("Password Mismatch")
                dialogBuilder.create().show()


            }


        }



    }
    fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if (errorMessage) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@SignUpLayout,
                    R.color.colorSnackBarError
                )
            )
        }else{
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@SignUpLayout,
                    R.color.colorSnackBarSuccess
                )
            )
        }
        snackBar.show()
    }



}