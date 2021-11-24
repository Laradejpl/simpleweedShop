package cereal.company.weedsimple

import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import cereal.company.weedsimple.Person.Companion.email
import cereal.company.weedsimple.Sqlite.DatabaseManager
import cereal.company.weedsimple.utils.BaseActivity
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_version_detail.*
import kotlinx.android.synthetic.main.alert_add_fav.view.*
import kotlinx.android.synthetic.main.e_product_row.*
import kotlinx.android.synthetic.main.layout_avis_alert.*
import kotlinx.android.synthetic.main.layout_avis_alert.view.*

class VersionDetail : BaseActivity  () {

    private var idDuProduit:String?=null
    private var coeur = false
    var checkisDone = false
    val db = DatabaseManager(this@VersionDetail)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_version_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var toolbarC :Toolbar = findViewById(R.id.toobarlayoutv)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val titleProduct = intent.getStringExtra("title")
        val prixDuProduit = intent.getIntExtra("prix",0)
        val urlImage = intent.getStringExtra("nomphoto")
        idDuProduit=intent.getStringExtra("id")
        print(" email du user : $email")

 val selectedId = intent.getStringExtra("id").toString()

 val bitmap:Bitmap = BitmapFactory.decodeResource(resources,R.drawable.blondehair)
 Palette.from(bitmap).generate { palette ->

     if (palette != null){

         collapsingbar.setContentScrimColor(palette.getMutedColor(R.attr.colorAccent))

     }

 }

 cadiImg.setOnClickListener {



     Person.addToCartProductID = intent.getStringExtra("id")?.toInt()!!
     val amountFragment = AmountFragment()
     val fragmentManager = (this@VersionDetail).fragmentManager
     amountFragment.show(fragmentManager, "TAG")
 }


//@TODO ajout aux favoris
 //ont regarde si le produit est deja dans les favoris

        if (email.isEmpty()){



        }else{

            val cursor: Cursor

            val favoritProductAdd = db.checkFavoris(email,titleProduct)

            if(favoritProductAdd){

                ajoutImg.setImageResource(R.drawable.ic_favorite_green)
                coeur = true
            }else{

                ajoutImg.setImageResource(R.drawable.ic_favorite_red)
                coeur = false
            }


        }


        ajoutImg.setOnClickListener{

     // dialogue  pour ajouter les favoris

     val view = View.inflate(this@VersionDetail,R.layout.alert_add_fav,null)
     var builder = android.app.AlertDialog.Builder(this@VersionDetail)
     builder.setView(view)
     val dialog =builder.create()
     dialog.show()
     view.oui_btn_film_add.setOnClickListener {
         //@TODO ENREGISTREMENT DANS BASE SQLITE,Remplacement de l'etoile par un coeur

    if (email.equals("")){

        showErrorSnackBar("Vous netes pas connecter",true)
    }else{
        db.insertProductDataBase(titleProduct,urlImage, email,prixDuProduit)
        showErrorSnackBar("produit ajouter",false)
        if (checkisDone){

            checkedone.speed = -1f
            checkedone.playAnimation()
            checkisDone =false

            println("ca marche")


        }else{
            checkedone.speed = 1f
            checkedone.playAnimation()
            checkisDone =true
            println("ca marche pas")
        }




    }
         dialog.dismiss()
     }

     view.non_btn_film_add.setOnClickListener {


         dialog.dismiss()
     }

 }

//Requete pour  avg avis

        val reqavg: RequestQueue = Volley.newRequestQueue(this)
        id_product_fetchone_tv_V.text = "$selectedId"
        val starUrl = "https://reggaerencontre.com/averagektl.php?id_produit=$selectedId"
        val jsonORStar = JsonObjectRequest(
            Request.Method.GET,starUrl,null,
            { response ->

                val starAvg = response.getInt("Note_produit").toFloat()
                rtgstar.rating = starAvg
                println(starAvg)

            }, { error ->

               println(error.message)

            })
        reqavg.add(jsonORStar )

//FIN Requete pour  avg avis
        val picURL = "https://reggaerencontre.com/"

 val rq: RequestQueue = Volley.newRequestQueue(this)

 id_product_fetchone_tv_V.text = "$selectedId"
 val prodUrl1 ="https://reggaerencontre.com/fetch_one_product.php?id=$selectedId"
       // val requestQp: RequestQueue = Volley.newRequestQueue(this)

 val jsonOR = JsonObjectRequest(
     Request.Method.GET,prodUrl1,null,
     { response ->

         val prixDuProduit =response.getInt("price").toString()
         val referenceProduit = response.getString("id")

         id_product_fetchone_tv_V.text=" ref: ${ referenceProduit}"
         name_product_fetchone_tv_V.text=response.getString("name")
         description_product_tv_V.text=response.getString("description")

         supportActionBar?.setDisplayHomeAsUpEnabled(true)
         var toolbarC :Toolbar = findViewById(R.id.toobarlayoutv)
         setSupportActionBar(toolbarC)
         toolbarC.setTitle(response.getString("name"))


         price_product_fetchone_tv_V.text="${ prixDuProduit } €"


        if (response.getString("picture") != null
            && response.getString("picture1") != null
            && response.getString("picture2") != null
            && response.getString("picture3") != null){


         Picasso.with(this).load(picURL + response.getString("picture")).into(image_film_detail_v_V)

         Picasso.with(this).load(picURL + response.getString("picture1")).into(miniatureImage)

         Picasso.with(this).load(picURL + response.getString("picture2")).into(miniature1)

         Picasso.with(this).load(picURL + response.getString("picture3")).into(miniature2)

        }else{

            slider_detail_photo.visibility = View.GONE
        }


     }, { error ->


     })
        rq.add(jsonOR )


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

     ratiStars.setOnRatingBarChangeListener { p0, p1, p2 ->
         Toast.makeText(this@VersionDetail, "Given rating is: $p1", Toast.LENGTH_SHORT).show()

         Person.etoileRating = p1.toInt()
     }

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

                 //Toast.makeText(this@VersionDetail, response, Toast.LENGTH_SHORT).show()
                 showErrorSnackBar(response,false)
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
                            response.getJSONObject(x).getInt("rating_star"),
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


