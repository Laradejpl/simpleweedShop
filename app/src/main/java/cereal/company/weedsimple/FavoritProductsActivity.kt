package cereal.company.weedsimple

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import cereal.company.weedsimple.Sqlite.DatabaseManager
import cereal.company.weedsimple.Sqlite.FavoriteProducts
import kotlinx.android.synthetic.main.activity_favorit_products.*

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

        showFavMovies()
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