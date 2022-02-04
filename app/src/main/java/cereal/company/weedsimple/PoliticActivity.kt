package cereal.company.weedsimple

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

class PoliticActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_politic)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

    }
}