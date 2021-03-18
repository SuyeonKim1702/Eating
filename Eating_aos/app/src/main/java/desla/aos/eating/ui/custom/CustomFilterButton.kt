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
        View.inflate(context, R.layout.filter_btn_custom, this)


        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.CustomFilterButton)
        typedArray.getString(R.styleable.CustomFilterButton_filter_text).let {
            btn_text.text = it
        }
        typedArray.recycle()

        btn_background.setOnClickListener {
            it.isSelected = !it.isSelected
            btn_img_ic.isSelected = it.isSelected
        }

    }

    override fun isSelected(): Boolean {
        return btn_background.isSelected
    }



}