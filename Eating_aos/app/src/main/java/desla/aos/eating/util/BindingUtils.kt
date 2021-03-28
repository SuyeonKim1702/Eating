package desla.aos.eating.util

import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import desla.aos.eating.R


@BindingAdapter("image")
fun loadImageview(view : ImageView, url: String?){
    if(url != null){
        Glide.with(view)
                .load(url)
                .placeholder(R.color.white)
                .fitCenter()
                .centerCrop()
                .into(view)
    }
}

@BindingAdapter("deliveryFeeByHost")
fun deliveryFeeByHost(view : TextView, num: Int){
    //배달비비

    view.text =  when(num){
        0 -> "본인부담"
        1 -> "1/N"
        else -> "없음"
    }

}

@BindingAdapter("meetLocation")
fun meetLocation(view : TextView, num: Int){

    view.text =  when(num){
        0 -> "호스트 쪽"
        1 -> "중간 지점"
        else -> "장소협의"
    }

}

@BindingAdapter("showCount_num", "showCount_pos")
fun showCount(view : ImageView, num: Int,  pos: Int){

    view.isSelected = num >= pos

}

@BindingAdapter("time")
fun time(view : TextView, oldTime: String){

    view.text = MyTimeUtils.twoDatesBetweenTime(oldTime)

}




