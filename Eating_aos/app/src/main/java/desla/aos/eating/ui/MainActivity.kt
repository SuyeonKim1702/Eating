package desla.aos.eating.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import desla.aos.eating.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
            param(FirebaseAnalytics.Param.ITEM_ID, "id")
            param(FirebaseAnalytics.Param.ITEM_NAME, "name")
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
        }

        setupViews()
        updateStatusBarColor("#ffffff")
    }


    private fun setupViews()
    {
        // Finding the Navigation Controller
        var navController = findNavController(R.id.fragNavHost)

        // Setting Navigation Controller with the BottomNavigationView
        bottomNavView.setupWithNavController(navController)

        bottomNavView.itemIconTintList = null

    }

    private fun updateStatusBarColor(color: String) { // Color must be in hexadecimal fromat
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor(color)
        }
    }

    fun setVisibilityBottomNavigation(isShow: Boolean){
        if(bottomNavView == null) return
        if(isShow) bottomNavView.visibility = View.VISIBLE
        else bottomNavView.visibility = View.GONE
    }
}