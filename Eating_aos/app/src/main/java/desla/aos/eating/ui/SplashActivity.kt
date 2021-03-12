package desla.aos.eating.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import desla.aos.eating.R
import desla.aos.eating.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    val SPLASH_VIEW_TIME: Long = 1000 //1초간 스플래시 화면을 보여줌 (ms)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({ //delay를 위한 handler
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, SPLASH_VIEW_TIME)
    }

}