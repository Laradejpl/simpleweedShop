package cereal.company.weedsimple

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_coin_payment.*


class CoinPaymentActivity : AppCompatActivity() {


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_payment)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );



        // ONT RECUPERE LE PRIX TOTAL A PAYER
        var prixApayer = intent.getLongExtra("prixtotal",0)


      //GENERER UN NOM DE TRANSACTION ALEATOIRE
        val baseNonDeTransaction = "CoinPaymentWEEDSIMPLE"
        val nbAlea = (Math.random() * 999)
        var nameT=baseNonDeTransaction + nbAlea

        println(prixApayer)
        println(nameT)


        webview.setWebViewClient( WebViewClient())
         val ws =webview.settings
        ws.javaScriptEnabled=true
        webview.loadUrl("https://www.coinpayments.net/index.php?cmd=_pos&reset=1&merchant=09d8a53121db9fdfaf0a7926d41c9b34&item_name=$nameT&currency=BTC&amountf=$prixApayer&allow_currency=0&")


    }

}