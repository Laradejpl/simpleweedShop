package cereal.company.weedsimple

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.palette.graphics.Palette
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_fetch_one_product.*

import kotlinx.android.synthetic.main.activity_version_detail.*

class VersionDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_version_detail)

        var toolbarC :Toolbar = findViewById(R.id.toobarlayoutv)

        setSupportActionBar(toolbarC)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val selectedId = intent.getStringExtra("id").toString()

        val bitmap:Bitmap = BitmapFactory.decodeResource(resources,R.drawable.blondehair)
        Palette.from(bitmap).generate { palette ->

            if (palette != null){

                collapsingbar.setContentScrimColor(palette.getMutedColor(R.attr.colorAccent))

            }

        }

        cadiImg.setOnClickListener {

            //@TODO aleert dialog pour mettre dans le panier

            Person.addToCartProductID = intent.getStringExtra("id")?.toInt()!!
            var amountFragment = AmountFragment()
            var fragmentManager = (this@VersionDetail).fragmentManager
            amountFragment.show(fragmentManager, "TAG")
        }

        toolbarC.setNavigationOnClickListener {

            finish()
        }


        var picURL = "https://reggaerencontre.com/"

        var rq: RequestQueue = Volley.newRequestQueue(this)

        id_product_fetchone_tv_V.text = "$selectedId"
        var prodUrl ="https://reggaerencontre.com/fetch_one_product.php?id=$selectedId"

        var jsonFile= JsonObjectRequest(
            Request.Method.GET,prodUrl,null,
            { response ->

                val prixDuProduit =response.getInt("price").toString()
                val referenceProduit = response.getString("id")
                id_product_fetchone_tv_V.text=" ref: ${ referenceProduit }"
                name_product_fetchone_tv_V.text=response.getString("name")
                description_product_tv_V.text=response.getString("description")

                price_product_fetchone_tv_V.text="${ prixDuProduit } €"
                Picasso.with(this).load(picURL + response.getString("picture")).into(image_film_detail_v_V)

                Picasso.with(this).load(picURL + response.getString("picture1")).into(miniatureImage)




            }, { error ->

                name_product_fetchone_tv.text=error.message


            })
        rq.add(jsonFile)



    }


}