package cereal.company.weedsimple

import android.app.Activity
import android.content.Intent
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_fetch_eproducts.*
import kotlinx.android.synthetic.main.activity_fetch_one_product.*
import kotlinx.android.synthetic.main.activity_login.*

import kotlinx.android.synthetic.main.activity_version_detail.*
import kotlinx.android.synthetic.main.layout_avis_alert.*
import kotlinx.android.synthetic.main.layout_avis_alert.view.*

class VersionDetail : AppCompatActivity() {

    var idDuProduit:String?=null

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

       idDuProduit=intent.getStringExtra("id")

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
        val requestQ: RequestQueue = Volley.newRequestQueue(this)

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

     val ratiStars = dialog.avis_rtgstar_dialog

     ratiStars.setOnRatingBarChangeListener(object : RatingBar.OnRatingBarChangeListener {
         override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
             Toast.makeText(this@VersionDetail, "Given rating is: $p1", Toast.LENGTH_SHORT).show()

             Person.etoileRating =p1.toInt()


         }
     })

     view.avisbutton_btn.setOnClickListener {

         val emailUserAvis = view.edt_email_avis.text.toString()
         val pseudoUserAvis = view.edt_pseudo_avis.text.toString()
         val avisDuUser =  view.edt_Avis_user.text.toString()

         println(Person.etoileRating)
         val avisUrl = "https://reggaerencontre.com/avis_ktl.php?id_produit=" + idDuProduit +
                 "&email=" +
                 emailUserAvis+
                "&pseudo=" + pseudoUserAvis + "&rating_star=" + Person.etoileRating +"&avis="+
                 avisDuUser

                 val requestQ: RequestQueue = Volley.newRequestQueue(this)
         val stringRt = StringRequest(Request.Method.GET,avisUrl, {


                 response ->

             if (response.equals("Merci,votre note à été bien enregistrez.")){
                 // pour garder une trace de la personne qui s'est logger ou enregistrer.




                 Toast.makeText(this@VersionDetail, response, Toast.LENGTH_SHORT).show()
                 dialog.dismiss()


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

         requestQ.add(stringRt )


     }








     }
        //ont recupere les avis
        val url= "https://reggaerencontre.com/fetch_avis_ktl.php?id_produit=$selectedId"
        val list = ArrayList<AvisProduct>()
        var rQ= Volley.newRequestQueue(this)
        var jar  = JsonArrayRequest(Request.Method.GET,url,null,
            { response ->
                for (x in 0..response.length()-1)
                    list.add(
                        AvisProduct(


                            response.getJSONObject(x).getString("pseudo"),
                            response.getJSONObject(x).getString("avis"))
                    )
                var adp = AvisAdapter(this@VersionDetail,list)
                avisRV.adapter=adp
                avisRV.layoutManager= LinearLayoutManager(this@VersionDetail)
            },

            {  })
        rQ.add(jar)



 }

}


