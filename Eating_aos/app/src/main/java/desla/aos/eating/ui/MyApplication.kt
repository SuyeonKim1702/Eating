package desla.aos.eating.ui

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.kakao.sdk.common.KakaoSdk
import desla.aos.eating.R

class MyApplication : Application() {

    companion object {
        lateinit var prefs: PreferenceUtil
    }


    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)

        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }
}

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    fun getString(key: String, defValue: String): String
    {
        return prefs.getString(key, defValue).toString()
    }
    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }

    fun setUserInfo2(address: String, latitude: String, longitude: String){
        prefs.edit().putString("address", address).apply()
        prefs.edit().putString("latitude", latitude).apply()
        prefs.edit().putString("longitude", longitude).apply()
    }


    //2: 회원가입 완료 -> MainActivity
    fun isRegister() = prefs.getBoolean("auth", false)
    fun setRegister(){
        prefs.edit().putBoolean("auth", true).apply()
    }
}