package cereal.company.weedsimple

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);


        backarrow_fetch_product_search.setOnClickListener {

            finish()
        }

        back_search.setOnClickListener {

            val intent = Intent(this@SearchActivity, MainActivity::class.java)
            startActivity(intent)
        }





        title_search_text_view.text =  intent.getStringExtra("motclef")

        var keyWord = intent.getStringExtra("motclef")

        var productsListsearch = ArrayList<EProduct>()
        val searchUrl = "https://mobileandweb.alwaysdata.net/search_kotlin.php?keyword=$keyWord"
        val requestQ: RequestQueue = Volley.newRequestQueue(this)
        var jsonAR = JsonArrayRequest(Request.Method.GET, searchUrl,null, {


            response ->

            for (productJOIndex in 0.until(response.length())){


                productsListsearch.add(EProduct(response.getJSONObject(productJOIndex).getInt("id") , response.getJSONObject(productJOIndex).getString("name"),
                        response.getJSONObject(productJOIndex).getInt("price"),response.getJSONObject(productJOIndex).getString("picture"),
                    response.getJSONObject(productJOIndex).getInt("stock")
                ))

            }


            val pAdapter = EProductAdapter(this@SearchActivity, productsListsearch)
            productsRVsearch.layoutManager = LinearLayoutManager(this@SearchActivity)
            productsRVsearch.adapter = pAdapter





        } ,{


            error ->


            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Message")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()




        })



        requestQ.add(jsonAR)





    }
}