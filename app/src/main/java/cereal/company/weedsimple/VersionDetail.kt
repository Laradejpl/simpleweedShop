package cereal.company.weedsimple

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
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


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var toolbarC :Toolbar = findViewById(R.id.toobarlayoutv)


        /*toolbarC.setNavigationOnClickListener {

            finish()
        }*/
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        rtgstar.setOnRatingBarChangeListener(object : RatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
                Toast.makeText(this@VersionDetail, "Given rating is: $p1", Toast.LENGTH_SHORT).show()
            }
        })



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

                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                var toolbarC :Toolbar = findViewById(R.id.toobarlayoutv)
                setSupportActionBar(toolbarC)
                toolbarC.setTitle(response.getString("name"))




                price_product_fetchone_tv_V.text="${ prixDuProduit } €"
                Picasso.with(this).load(picURL + response.getString("picture")).into(image_film_detail_v_V)

                Picasso.with(this).load(picURL + response.getString("picture1")).into(miniatureImage)

                Picasso.with(this).load(picURL + response.getString("picture2")).into(miniature1)

                Picasso.with(this).load(picURL + response.getString("picture3")).into(miniature2)




            }, { error ->

                name_product_fetchone_tv.text=error.message


            })
        rq.add(jsonFile)




        avis_btn.setOnClickListener {

            val view = View.inflate(this@VersionDetail,R.layout.layout_avis_alert,null)
            var builder = AlertDialog.Builder(this@VersionDetail)
            builder.setView(view)
            val dialog =builder.create()
            dialog.show()
            // dialog.setIcon(R.drawable.registration)
            dialog.window?.setBackgroundDrawableResource(R.drawable.bg_transparent)
            var layout : ConstraintLayout = view.findViewById(R.id.registerConstraint_Avis)



        }

    }


}