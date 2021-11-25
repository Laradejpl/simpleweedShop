package cereal.company.weedsimple

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import cereal.company.weedsimple.Sqlite.DatabaseManager
import cereal.company.weedsimple.Sqlite.FavoriteProducts
import kotlinx.android.synthetic.main.activity_favorit_products.*
import kotlinx.android.synthetic.main.top_header_fav.*

class FavoritProductsActivity : AppCompatActivity() {

    lateinit var adapter: FavorisProductAdapter
    var id = 0
    var favList = ArrayList<FavoriteProducts>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorit_products)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //val
     val loginImgFav = findViewById<ImageView>(R.id.login_iv)
     val panier_fav =  findViewById<ImageView>(R.id.cart_iv)
     val home_fav_retour = findViewById<ImageView>(R.id.home_iv)
        showFavMovies()

        retourfav.setOnClickListener {

            finish()
        }

        loginImgFav.setOnClickListener {

                val intent = Intent(this@FavoritProductsActivity, ProfileActivity::class.java)
                startActivity(intent)
        }


        panier_fav.setOnClickListener {

            val intent = Intent(this@FavoritProductsActivity,CartProductsActivity::class.java)
            startActivity(intent)
        }

        home_fav_retour.setOnClickListener {

            val intent = Intent(this@FavoritProductsActivity,MainActivity::class.java)
            startActivity(intent)
        }

    }

    fun showFavMovies(){


        //create databasemanager
        var db = DatabaseManager(this)

        //getting the user email
        var utilisateurEmail = Person.email



        //selecting all the favorites from the user
        var cursor =  db.getUserFavs(utilisateurEmail)



        // assigning the values from the database to the Class Movie
        if (cursor.moveToFirst()) {
            do {

                //getting the values from the Database
                var original_title = cursor.getString(1)
                var poster_path = cursor.getString(2)
                var price_produit = cursor.getInt(3)


                var produit = FavoriteProducts (original_title ,poster_path ,price_produit)

                favList.add(produit)

            } while (cursor.moveToNext())
        }


        adapter = FavorisProductAdapter(this@FavoritProductsActivity)
        adapter.SetProductF(favList)
        listfav.adapter = adapter



    }


}