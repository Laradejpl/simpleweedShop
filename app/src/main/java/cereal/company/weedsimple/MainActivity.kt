package cereal.company.weedsimple

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import cereal.company.weedsimple.ui.RobotActivity
import cereal.company.weedsimple.utils.BaseActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bar_bottom.*
import kotlinx.android.synthetic.main.header_menu.*
import kotlinx.android.synthetic.main.search_bar_layout.*
import kotlinx.android.synthetic.main.side_menu_nav.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.util.*

class MainActivity : BaseActivity() {

    lateinit var dotsLayout: LinearLayout
    lateinit var mpager: ViewPager
    lateinit var toolbar : androidx.appcompat.widget.Toolbar
    var path:IntArray = intArrayOf(R.drawable.euforia,R.drawable.bocalcbd,R.drawable.fruitcbd,R.drawable.drhemp)
    lateinit var dots:Array<ImageView>
    lateinit var adapter: PageView
    var currentPage: Int = 0
    lateinit var timer: Timer
    val DELAY_MS: Long = 5000
    val PERIOD_MS: Long = 5000
    lateinit var bransName:String;
    val URL = "https://api.coindesk.com/v1/bpi/currentprice.json"
    var okHttpClient: OkHttpClient = OkHttpClient()
    var panierText = ""





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

                       //variables de menu

        val hamenu = findViewById<ImageView>(R.id.hamburger)
        val menuSide = findViewById<NavigationView>(R.id.side_menu)
        val loginImg  = findViewById<ImageView>(R.id.login_iv)
        val closetxt =findViewById<TextView>(R.id.close_btn_side_menu)
        val instalink = findViewById<ImageView>(R.id.insta)
        val facebooklink = findViewById<ImageView>(R.id.facbook)
        val whatsapplink = findViewById<ImageView>(R.id.whatapp)
        val telephonelink = findViewById<ImageView>(R.id.phone)
        val phoneNumber = "0769754123"
        //compteur du panieer
        if (Person.counter_panier > 0){
            numberProduct_ajouT_tv.visibility = View.VISIBLE
            panierText = Person.counter_panier.toString()
            numberProduct_ajouT_tv.text = panierText
        }else{
            numberProduct_ajouT_tv.visibility = View.GONE
        }

        // volley pour les categorie du sidemenu

        // AFFICHE LE COURS DU BTC ET EURO ,DOLLAR
        loadBitcoinPrice()
        var brandsUrl = "https://reggaerencontre.com/fetch_brands.php"
        var brandsList = ArrayList<String>()
        val requestQ: RequestQueue = Volley.newRequestQueue(this)

        var jsonAR = JsonArrayRequest(Request.Method.GET, brandsUrl,null, {
            response ->

            for (jsonObject in 0.until(response.length())){

                brandsList.add(response.getJSONObject(jsonObject).getString("brand"))

            }


            var brandsListAdapter = ArrayAdapter(this@MainActivity,R.layout.brand_item_text_view, brandsList)
            brandsListView.adapter = brandsListAdapter


        }, { error ->

            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Message")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()

        })

        requestQ.add(jsonAR)

        brandsListView.setOnItemClickListener { adapterView, view, i, l ->

            val  tappedBrand = brandsList.get(i)
            val intent = Intent(this@MainActivity, FetchEproductsActivity::class.java )

            intent.putExtra("BRAND" , tappedBrand)
            startActivity(intent)

        }

        //toolbar = findViewById(R.id.toolbar)
        //setSupportActionBar(toolbar)

        mpager = findViewById(R.id.pager) as ViewPager
        adapter  = PageView(this,path)
        mpager.adapter = adapter
        dotsLayout = findViewById(R.id.dotsLayout) as LinearLayout



        createDots(0)

        updatePage()

        mpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                currentPage = position
                createDots(position)

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        //LES CATEORIES
        tea_brand.setOnClickListener {
            StartCategory("TEA")

        }

        vap_brand.setOnClickListener {

            StartCategory("VAP")
        }

       flowers_brand.setOnClickListener {

           StartCategory("FLOWERS")
        }

        hash_brand.setOnClickListener {

            StartCategory("HASH")
        }

        food_brand.setOnClickListener {

            StartCategory("FOODS")
        }

        bodyheath_brand.setOnClickListener {

            StartCategory("BODY")
        }


        instalink.setOnClickListener {
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.instagram.com/uwallandroidapps/")
            startActivity(openURL)
        }
        facebooklink.setOnClickListener {
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.facebook.com/")
            startActivity(openURL)
        }

        whatsapplink.setOnClickListener {
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.whatsapp.com/")
            startActivity(openURL)
        }

         //ont affiche l'email du user connecter
        val emailConnected = Person.email
        email_tv_side_menu.text = emailConnected

       // ont verifie si il ya un email ou une connection pour afficher les addresses
      if (emailConnected !=  "" ){

          adresses.visibility = View.VISIBLE
          profil_tv.visibility = View.VISIBLE
          favori_tv_profile.visibility = View.VISIBLE

      }

        // BOT
        botbtn.setOnClickListener {
            val intentBot = Intent(this@MainActivity, RobotActivity::class.java)

            startActivity(intentBot)

        }


        favori_tv_profile.setOnClickListener {

            val intentProfil = Intent(this@MainActivity,FavoritProductsActivity::class.java)
            startActivity(intentProfil)
        }

        profil_tv.setOnClickListener {

            val intentProfil = Intent(this@MainActivity,ProfileActivity::class.java)
            startActivity(intentProfil)
        }


        adresses.setOnClickListener {

            val intentAdrss = Intent(this@MainActivity, TestVolley::class.java)
            startActivity(intentAdrss)
        }




        login_tv_side_menu.setOnClickListener {

            val intentLog = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intentLog)
        }


        sign_up_tv_side_menu.setOnClickListener {

            val intentSign = Intent(this@MainActivity, SignUpLayout::class.java)
            startActivity(intentSign)
        }



        loginImg.setOnClickListener {

            if (emailConnected !=  "" ){
                val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(intent)
            }else {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }

            }


        cart_iv.setOnClickListener {

            val intentcart = Intent(this@MainActivity, CartProductsActivity::class.java)
            startActivity(intentcart)

        }

        hamenu.setOnClickListener {
            menuSide.visibility = View.VISIBLE

        }

        settings_imgv.setOnClickListener {
            header_nav_layout.visibility= View.GONE
            searching_bar.visibility = View.VISIBLE

        }

        //serch button
        submit_search.setOnClickListener {

           //var searchWord = edt_searching.text
            var i = Intent(this,SearchActivity::class.java)
            i.putExtra("motclef",edt_searching.text.toString())//comment faire passer une variable dans une autres activity
            startActivity(i)


        }

        closetxt.setOnClickListener {

            menuSide.visibility = View.GONE

        }

        telephonelink.setOnClickListener {

            dialPhoneNumber()
        }


        //les produits clicker

        marley_pict.setOnClickListener {
          imageClikcable("marley")
        }


        cdbhead_pict.setOnClickListener {
           imageClikcable("saur")
        }

        tea_pict.setOnClickListener {
            imageClikcable("high tea")
        }

        oil_pict.setOnClickListener {
            imageClikcable("sleep well")
        }
        strawberry_pict.setOnClickListener {
            imageClikcable("royal cbd")
        }
        cookie1_pict.setOnClickListener {
            imageClikcable("haze")
        }
        cookie2_pict1.setOnClickListener {
            imageClikcable("haze")
        }

    }


    fun updatePage()
    {


        var handler= Handler()
        val Update: Runnable = Runnable {

            if (currentPage == path.size){

                currentPage = 0
            }

            mpager.setCurrentItem(currentPage++,true)
        }

        timer = Timer()
        timer.schedule(object : TimerTask(){
            override fun run() {
               handler.post(Update)
            }


        },DELAY_MS,PERIOD_MS )
    }

    fun createDots(position : Int){

        if (dotsLayout != null){

            dotsLayout.removeAllViews()
        }

        dots = Array(path.size,{i -> ImageView(this)})

        for (i in 0..path.size -1){


            dots[i] = ImageView(this)
            if (i == position){


                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots))






            }else{

                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.inactive_dots))
            }

            var params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            params.setMargins(4,0,4,0)
            dotsLayout.addView(dots[i],params)

        }


    }

    fun dialPhoneNumber(phoneNumber: String ="0769754123") {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    fun StartCategory(laCategori:String){

        val intent = Intent(this@MainActivity, FetchEproductsActivity::class.java )
        intent.putExtra("BRAND" , laCategori)
        startActivity(intent)
    }







    private fun loadBitcoinPrice(){

        val request:okhttp3.Request = okhttp3.Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {

                bitcoinValues.text ="le cours du bitcoin est indisponible"

            }

            override fun onResponse(call: Call, response: Response) {
               val json = response?.body?.string()
                val usRate = (JSONObject(json).getJSONObject("bpi").getJSONObject("USD")
                        ["rate"] as String).split(",")[0]

                val euroRate = (JSONObject(json).getJSONObject("bpi").getJSONObject("EUR")
                        ["rate"] as String).split(",")[0]

                Person.euroR = euroRate




                runOnUiThread{

                    bitcoinValues.text = "  1 BITCOIN vaut : $ $usRate\n\n  1 BITCOIN vaut :  $euroRate €"
                    Person.euroR = euroRate
                }
            }


        })
    }

    fun imageClikcable(prod: String){

        val intentP = Intent(this@MainActivity, SearchActivity::class.java)
        intentP.putExtra("motclef",prod)
        startActivity(intentP)

    }


}