package cereal.company.weedsimple

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_adress.*
import kotlinx.android.synthetic.main.activity_test_volley.*
import kotlinx.android.synthetic.main.activity_test_volley.backFleche_adrss
import kotlinx.android.synthetic.main.activity_test_volley.btn_street
import kotlinx.android.synthetic.main.activity_test_volley.editTextPhone
import kotlinx.android.synthetic.main.activity_test_volley.edt_PostalAddress
import kotlinx.android.synthetic.main.activity_test_volley.email_connect_tv
import kotlinx.android.synthetic.main.activity_test_volley.street_edt
import kotlinx.android.synthetic.main.activity_test_volley.ville_edt

class AdressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adress)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        backFleche_adrss.setOnClickListener {

            finish()
        }

        val spinner: Spinner = findViewById(R.id.planets_spinner)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.planets_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        email_connect_tv.setText(Person.email)



        btn_street.setOnClickListener {


            val strUrl = "https://mobileandweb.alwaysdata.net/volley.php?nom=" +
                    nom_adress_edt.text.toString() + "&prenom=" +  prenom_adress_edt.text.toString() +
                     "&street=" + street_edt.text.toString() +
                    "&ville=" +ville_edt.text.toString() +
                    "&telephone=" + editTextPhone.text.toString() +
                    "&email=" + Person.email + "&cp=" +
                    edt_PostalAddress.text.toString() + "&pays=" + spinner.getSelectedItem()


            val requestQ: RequestQueue = Volley.newRequestQueue(this)

            val stringRequest = StringRequest(Request.Method.GET,strUrl, {


                    response ->


                if (response.equals("votre enregistrement est effectuer")){


                    //Toast.makeText(this@TestVolley,response, Toast.LENGTH_LONG).show()

                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setTitle("Bravo")
                    dialogBuilder.setMessage(response)
                    dialogBuilder.create().show()
                } else if (response.equals("pas email")){


                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setTitle("ooh")
                    dialogBuilder.setMessage("Email missing")
                    dialogBuilder.create().show()
                }else if (response.equals("street missing")){


                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setTitle("ooh")
                    dialogBuilder.setMessage("Street missing")
                    dialogBuilder.create().show()
                }
                else if (response.equals("ville missing")){


                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setTitle("ooh")
                    dialogBuilder.setMessage("ville missing")
                    dialogBuilder.create().show()
                }
                else if (response.equals("cp missing")){


                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setTitle("ooh")
                    dialogBuilder.setMessage("cp missing")
                    dialogBuilder.create().show()
                }
                else if (response.equals("telephone missing")){


                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setTitle("ooh")
                    dialogBuilder.setMessage("telephone missing")
                    dialogBuilder.create().show()
                }
                else if (response.equals("le cp est mal formaté")){


                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setTitle("ooh")
                    dialogBuilder.setMessage("le cp est mal formaté")
                    dialogBuilder.create().show()
                }








            },{

                    error->


                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("alert")
                dialogBuilder.setMessage(error.message )
                dialogBuilder.create().show()


            })
            requestQ.add(stringRequest)
        }
    }



}