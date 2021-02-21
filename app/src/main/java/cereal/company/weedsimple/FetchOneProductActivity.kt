package cereal.company.weedsimple

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import kotlinx.android.synthetic.main.activity_fetch_one_product.*
import kotlinx.android.synthetic.main.activity_sign_up_layout.*

class FetchOneProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_one_product)
        var picURL = "https://reggaerencontre.com/"

        var rq: RequestQueue = Volley.newRequestQueue(this)
        val selectedId = intent.getStringExtra("id").toString()
        id_product_fetchone_tv.text = "$selectedId"
        var prodUrl ="https://reggaerencontre.com/fetch_one_product.php?id=$selectedId"

        var jsonFile= JsonObjectRequest(Request.Method.GET,prodUrl,null,
                { response ->



                    name_product_fetchone_tv.text=response.getString("name")

                    price_product_fetchone_tv.text=response.getInt("price").toString()
                    Picasso.with(this).load(picURL + response.getString("picture")).into(image_du_produit)



                }, { error ->

            name_product_fetchone_tv.text=error.message


        })
        rq.add(jsonFile)
    }
}




