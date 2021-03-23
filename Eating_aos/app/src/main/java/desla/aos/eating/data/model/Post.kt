package desla.aos.eating.data.model

import android.view.View
import android.widget.Toast
import java.io.Serializable

data class Post(
    val id: Int,
    val distance: Int,
    val title: String
): Serializable{

    fun onUserClcik(str: String){

    }

}

