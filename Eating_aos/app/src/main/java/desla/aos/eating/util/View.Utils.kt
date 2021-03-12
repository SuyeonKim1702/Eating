package desla.aos.eating.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import desla.aos.eating.ui.MainActivity
import desla.aos.eating.ui.post.PostActivity
import net.daum.android.map.MapActivity

fun Context.getActivity(): Activity? =
    when (this) {
        is Activity -> this
        is ContextWrapper -> this.baseContext.getActivity()
        else -> null
    }

fun Context.startGPSSettingActivityForResult() =
        Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }

fun Context.startMapActivity() =
        Intent(this, MapActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }

fun Context.startMainActivity() =
        Intent(this, MainActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }

fun Context.startPostActivity() =
    Intent(this, PostActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }