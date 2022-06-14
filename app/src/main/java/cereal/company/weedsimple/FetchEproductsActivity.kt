package cereal.company.weedsimple

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cereal.company.weedsimple.utils.BaseActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_fetch_eproducts.*
import kotlinx.android.synthetic.main.alert_add_fav.view.*
import kotlinx.android.synthetic.main.bar_bottom.*
import kotlinx.android.synthetic.main.e_product_row.*
import kotlinx.android.synthetic.main.e_product_row.view.*
import kotlinx.android.synthetic.main.header_menu.*
import kotlinx.android.synthetic.main.side_menu_nav.*

class FetchEproductsActivity : BaseActivity() {
    private val TAG = "MainActivity"
    private lateinit var mProgressDialog: Dialog

    lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_eproducts)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //@TODO ADMOB intégration
        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)



        backarrow_fetch_product.setOnClickListener {

            finish()
        }

        home_iv.setOnClickListener {
            val intent = Intent(this@FetchEproductsActivity, MainActivity::class.java)
            startActivity(intent)
        }

        cart_iv.setOnClickListener {
            val intent = Intent(this@FetchEproductsActivity, CartProductsActivity::class.java)
            startActivity(intent)

        }
        login_iv.setOnClickListener {

            if (Person.email != ""){
                val intent = Intent(this@FetchEproductsActivity, ProfileActivity::class.java)
                startActivity(intent)

            }else {
                showErrorSnackBar("Vous devez etre connecter",true)

            }

        }

        backhome_search.setOnClickListener {

            val intent = Intent(this@FetchEproductsActivity, CartProductsActivity::class.java)
            startActivity(intent)
        }

        if (Person.counter_panier > 0){
            numberProduct_tv.visibility = View.VISIBLE
           var basketResult = Person.counter_panier.toString()
            numberProduct_tv.text = basketResult
        }else{
            numberProduct_tv.visibility = View.GONE
        }

        val selectedBrand = intent.getStringExtra("BRAND")
        title_brand_text_view.text = "$selectedBrand"

        var productsList = ArrayList<EProduct>()
        var productsUrl = "https://mobileandweb.alwaysdata.net/fetch_eproducts.php?brand=$selectedBrand"
        val requestQ: RequestQueue = Volley.newRequestQueue(this)
        var jsonAR = JsonArrayRequest(Request.Method.GET, productsUrl,null, {
                response ->
            showProgressDialog("Patientez un peu...")

            for (productJOIndex in 0.until(response.length())){


                productsList.add(EProduct(response.getJSONObject(productJOIndex).getInt("id") , response.getJSONObject(productJOIndex).getString("name"),
                    response.getJSONObject(productJOIndex).getInt("price"),response.getJSONObject(productJOIndex).getString("picture"),
                    response.getJSONObject(productJOIndex).getInt("stock")


                ))



            }

            val pAdapter = EProductAdapter(this@FetchEproductsActivity, productsList)
            productsRV.layoutManager = LinearLayoutManager(this@FetchEproductsActivity)
            productsRV.adapter = pAdapter


            hideProgressDialog()


        }, { error ->

            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Message")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()







        })

        requestQ.add(jsonAR)
    }
}