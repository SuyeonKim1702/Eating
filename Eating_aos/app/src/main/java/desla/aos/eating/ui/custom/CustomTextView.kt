package desla.aos.eating.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import desla.aos.eating.R
import kotlinx.android.synthetic.main.tv_custom.view.*

open class CustomTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyleAttr: Int=0)
    : ConstraintLayout(context, attrs, defStyleAttr) {


    init {
        View.inflate(context, R.layout.tv_custom, this)


        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.CustomTextView)
        typedArray.getString(R.styleable.CustomTextView_text).let {
            text.text = it
        }
        typedArray.recycle()
    }



}