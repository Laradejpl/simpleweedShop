package cereal.company.weedsimple

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cereal.company.weedsimple.Person.Companion.euroR
import cereal.company.weedsimple.utils.BaseActivity

import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import kotlinx.android.synthetic.main.activity_finalize_shopping.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.lang.NumberFormatException
import java.math.BigDecimal

class FinalizeShoppingActivity : BaseActivity() {

    val URL = "https://api.coindesk.com/v1/bpi/currentprice.json"
    var okHttpClient: OkHttpClient = OkHttpClient()

    var ttPrice: Long = 0
    var priceToS:String = ""
    var prixEnD  = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finalize_shopping)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        login_iv.setOnClickListener {

            if (Person.email != null){

                val intent= Intent(this@FinalizeShoppingActivity, ProfileActivity::class.java)
                startActivity(intent)

            }else{

                showErrorSnackBar(getString(R.string.connec_snack) ,true)

            }
        }
        cart_iv_wb.setOnClickListener {

            val intent= Intent(this@FinalizeShoppingActivity, CartProductsActivity::class.java)
            startActivity(intent)
        }
        fav_iv.setOnClickListener {

                val intent= Intent(this@FinalizeShoppingActivity, FavoritProductsActivity::class.java)
                startActivity(intent)
            }
        home_iv.setOnClickListener {

            val intent= Intent(this@FinalizeShoppingActivity, MainActivity::class.java)
            startActivity(intent)
        }
        backarrow_fetch_product.setOnClickListener { finish() }

        loadBitcoinPriceFinalA()


        val calculateTotalPriceUrl = "https://reggaerencontre.com/calculate_total_price.php?invoice_num=${intent.getStringExtra("LATEST_INVOICE_NUMBER")}"

        val requestQ = Volley.newRequestQueue(this@FinalizeShoppingActivity)
        val stringRequest = StringRequest(Request.Method.GET, calculateTotalPriceUrl, { response ->

          val textPrixPrefix =getString(R.string.prix_payer)
            paiment_reel.setText(textPrixPrefix + response + "€" )
            ttPrice = response.toLong()
            prixEnD = response.toInt()
            priceToS=response


            // CONVERSION EURO TO BITCOIN
            var rateOfEuro = Person.euroR.toDouble()
            var convertionCoin = (prixEnD/rateOfEuro/1000).toFloat()
            var convStr = convertionCoin.toString()
            val btcPrice =BigDecimal(convStr)
            test_tv.text = "$btcPrice BTC"
            Person.prixApayerEnBtc = btcPrice

        }, { error ->


        })


        requestQ.add(stringRequest)

        var paypalConfig: PayPalConfiguration = PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION).clientId(MyPayPal.clientID)
        var ppService = Intent(this@FinalizeShoppingActivity, PayPalService::class.java)
        ppService.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig)
        startService(ppService)

// PAYPAL
        btnPaymentProcessing.setOnClickListener {


            var ppProcessing = PayPalPayment(BigDecimal.valueOf(ttPrice),
                    "EUR", "Online CBD WEED SIMPLE!",
                    PayPalPayment.PAYMENT_INTENT_SALE)
            var paypalPaymentIntent = Intent(this, PaymentActivity::class.java)
            paypalPaymentIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig)
            paypalPaymentIntent.putExtra(PaymentActivity.EXTRA_PAYMENT, ppProcessing)
            startActivityForResult(paypalPaymentIntent, 1000)


        }

        paiment_bouton.setOnClickListener {

            val intentCoin = Intent(this@FinalizeShoppingActivity, CoinPaymentActivity::class.java)
            intentCoin.putExtra("prixtotal",ttPrice)

            startActivity(intentCoin)
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == 1000) {

            if (resultCode == Activity.RESULT_OK) {

                //@TODO mettre a jour le stockage


                var intent = Intent(this, ThankYouActivity::class.java)
                startActivity(intent)

            } else {

                //@TODO mettre a jour le stockage

                Toast.makeText(this, "Sorry! Something went wrong. Try Again", Toast.LENGTH_SHORT).show()

            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()

        stopService(Intent(this, PayPalService::class.java))
    }
    private fun loadBitcoinPriceFinalA(){

        val request:okhttp3.Request = okhttp3.Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

                Btc_tv.text =getString(R.string.indisponiblebtc)

            }

            override fun onResponse(call: Call, response: Response) {
                val json = response?.body?.string()
                val usRate = (JSONObject(json).getJSONObject("bpi").getJSONObject("USD")
                        ["rate"] as String).split(".")[0]

                var euroRate = (JSONObject(json).getJSONObject("bpi").getJSONObject("EUR")
                        ["rate"] as String).split(".")[0]



                runOnUiThread{

                    Btc_tv.text = "  1 BITCOIN vaut : $ $usRate\n\n  1 BITCOIN vaut :  $euroRate €"




                }
            }


        })
    }
}