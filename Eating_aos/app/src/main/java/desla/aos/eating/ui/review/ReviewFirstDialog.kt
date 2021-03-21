package desla.aos.eating.ui.review

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import desla.aos.eating.R
import kotlinx.android.synthetic.main.dialog_review_first.*

class ReviewFirstDialog(
        private val clickListener: CategoryClickListener
) : DialogFragment() {

    interface CategoryClickListener {
        fun clickFirstFinish(a1: Int, a2: Int, view: View)
    }

    fun receiveResult(isSuccess: Boolean, message: String){
        if(isSuccess){
            dismiss()
        }else{
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomFullDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_review_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btn_first_review.setOnClickListener {
            clickListener.clickFirstFinish(1,2, it)
        }



    }

    override fun onStart() {
        super.onStart()

        val window: Window = dialog!!.window!!

        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.decorView.setBackgroundResource(android.R.color.transparent)
        window.setDimAmount(0.0f)

        val params: WindowManager.LayoutParams = window.attributes
        // 화면에 가득 차도록
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT

        // 열기&닫기 시 애니메이션 설정
        params.windowAnimations = R.style.AnimationPopupStyle
        window.attributes = params
        // UI 하단 정렬
        window.setGravity(Gravity.BOTTOM)
    }





    private fun hideSoftKeyBoard() {
        activity?.window?.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}