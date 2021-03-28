package desla.aos.eating.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import desla.aos.eating.R
import kotlinx.android.synthetic.main.filter_btn_custom.view.*

open class CustomFilterButton @JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyleAttr: Int=0)
    : ConstraintLayout(context, attrs, defStyleAttr) {


    init {

        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.CustomFilterButton)
        typedArray.getBoolean(R.styleable.CustomFilterButton_isLarge, false).let {
            if(it){
                View.inflate(context, R.layout.filter_btn_custom_large, this)
            }else{
                View.inflate(context, R.layout.filter_btn_custom, this)
            }
        }
        typedArray.getString(R.styleable.CustomFilterButton_filter_text).let {
            btn_text.text = it
        }
        typedArray.recycle()


    }

    fun setNotSelected(value: Boolean){
        if(value){
            btn_background.isSelected = true
            btn_img_ic.isSelected = true
            btn_text.isSelected = true
        }else{
            btn_background.isSelected = false
            btn_img_ic.isSelected = false
            btn_text.isSelected = false
        }

    }

    fun getSelected(): Boolean{
        return btn_background.isSelected
    }

    fun getName(): String{
        return btn_text.text.toString()
    }

}