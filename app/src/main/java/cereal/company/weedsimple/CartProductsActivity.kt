package cereal.company.weedsimple

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import cereal.company.weedsimple.utils.BaseActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_cart_products.*
import kotlinx.android.synthetic.main.side_menu_nav.*

class CartProductsActivity : BaseActivity() {
     //private var valeurOfPoints : Double = 0.000025
     var shippingPayed = false
    var lowCostDelivery = false
    var highCostDelivery = false
     var totalTemporary=0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_products)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        val btnShippingShort = findViewById<TextView>(R.id.short_delivery_txt)
        val btnShippingLong =   findViewById<TextView>(R.id.long_delivery_txt)

        btnShippingShort.visibility=View.GONE
        btnShippingLong.visibility=View.GONE









        long_delivery_txt.setOnClickListener {

            lowFeeShipping()
        }
        short_delivery_txt.setOnClickListener {
            HighFeeShipping()
        }

        disCarte.setOnClickListener {

            val deleteUrl = "https://reggaerencontre.com/decline_order.php?email=${Person.email}"
            val requestQ = Volley.newRequestQueue(this@CartProductsActivity)
            val stringRequest = StringRequest(Request.Method.GET, deleteUrl, {
                    response ->
                  Person.counter_panier = 0

                var intent = Intent(this, CartProductsActivity::class.java)
                startActivity(intent)

            }, {
                    error ->



            })

            requestQ.add(stringRequest)
        }
        backThome_img.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



        acceptCarte.setOnClickListener {
        if (Person.email != "" && shippingPayed ){
            acceptOrder ()
        }
        else if (Person.email == "" ){

            showErrorSnackBar("Vous devez etre connecter",true)

        }
        else if (!shippingPayed){

            showErrorSnackBar("Choisissez un Mode d'expedition",true)

        }




        }


        backarrow_fetch_product_V.setOnClickListener {

            var intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }




        var cartProductsUrl = "https://reggaerencontre.com/fetch_temporary_order.php?email=${Person.email}"
        var cartProductsList = ArrayList<String>()
        var requestQ = Volley.newRequestQueue(this@CartProductsActivity)
        var jsonAR = JsonArrayRequest(Request.Method.GET, cartProductsUrl, null, { response ->


            for (joIndex in 0.until(response.length())) { // id, name, price, email, amount

                cartProductsList.add(
                    " Id: ${response.getJSONObject(joIndex).getInt("id")}" +
                            " \n Name: ${response.getJSONObject(joIndex).getString("name")}" +
                            " \n Price: ${response.getJSONObject(joIndex).getInt("price")} €" +
                            " \n Email: ${response.getJSONObject(joIndex).getString("email")}" +
                            " \n Amount:  ${response.getJSONObject(joIndex).getInt("amount")}"
                )

                totalTemporary =(response.getJSONObject(joIndex).getInt("price")
                        * response.getJSONObject(joIndex).getInt("amount") ) + totalTemporary
                soustotal_tempo.text = totalTemporary.toString() + " €"


                if (totalTemporary > 50){

                    shippingPayed = true
                    btnShippingShort.visibility=View.GONE
                    btnShippingLong.visibility=View.GONE
                    freeShipping_txt.visibility=View.VISIBLE
                    Person.tarifExpedition = 0

                }else{

                    btnShippingShort.visibility=View.VISIBLE
                    btnShippingLong.visibility=View.VISIBLE




                }




            }

            var cartProductsAdapter = ArrayAdapter(
                this@CartProductsActivity,
                android.R.layout.simple_list_item_1,
                cartProductsList
            )

            cartProductsListView.adapter = cartProductsAdapter

        }, { error ->


        })


        requestQ.add(jsonAR)

    }
 private fun lowFeeShipping(): Int {

      shippingPayed = true
     //lowCostDelivery = true

     showErrorSnackBar("Vous avez choisi votre expedition,validez votre panier.",false)
     long_delivery_txt.setBackgroundColor(Color.parseColor("#89D7FB"))
     short_delivery_txt.setBackgroundColor(Color.parseColor("#0237F8"))
     Person.tarifExpedition = 8
     var ttpWithFees = totalTemporary + Person.tarifExpedition
     if (ttpWithFees > totalTemporary + 8){
         ttpWithFees -= 8
     }
     soustotal_tempo.text = ttpWithFees.toString() + " €"
     return Person.tarifExpedition


 }

    private fun HighFeeShipping(): Int {

        shippingPayed = true

        showErrorSnackBar("Vous avez choisi votre expedition,validez votre panier.",false)
        short_delivery_txt.setBackgroundColor(Color.parseColor("#89D7FB"))
        long_delivery_txt.setBackgroundColor(Color.parseColor("#0237F8"))
         Person.tarifExpedition =15

        var ttpWithFees = totalTemporary + Person.tarifExpedition
        if (ttpWithFees > totalTemporary + 15){
            ttpWithFees -= 15

        }
        soustotal_tempo.text = ttpWithFees.toString() + " €"
        return Person.tarifExpedition
    }




    private  fun acceptOrder ()
    {

        val verifyOrderUrl = "https://reggaerencontre.com/verify_order.php?email=${Person.email}"
        val requestQ = Volley.newRequestQueue(this@CartProductsActivity)
        val stringRequest = StringRequest(Request.Method.GET, verifyOrderUrl, {
                response ->
            var expeditionRate = Person.tarifExpedition
            println("le tarif expedition: " + Person.tarifExpedition )



            val intent = Intent(this@CartProductsActivity, FinalizeShoppingActivity::class.java)
            Toast.makeText(this, response, Toast.LENGTH_LONG).show()
            intent.putExtra("LATEST_INVOICE_NUMBER", response)
            intent.putExtra("expeditionTax", expeditionRate)
            startActivity(intent)


        }, { error ->  })



        requestQ.add(stringRequest)


    }



}