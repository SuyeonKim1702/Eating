package desla.aos.eating.ui.post

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import desla.aos.eating.R
import desla.aos.eating.ui.custom.CustomFilterButton
import kotlinx.android.synthetic.main.dialog_category.*

class CategoryDialog(
        private val clickListener: CategoryClickListener,
        private val num: Int?
) : DialogFragment() {

    interface CategoryClickListener {
        fun inputData(quary: Int, name: String)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomFullDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_category, container, false)
    }

    private fun clicked(view: View, num: Int){
        clickedButton?.setNotSelected(false)
        clickedButton = view as CustomFilterButton
        clickedButton?.setNotSelected(true)
        name = clickedButton?.getName()!!
        str = num
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(num){
            0 -> clicked(btn1, 0)
            1 -> clicked(btn2, 1)
            2 -> clicked(btn3, 2)
            3 -> clicked(btn4, 3)
            4 -> clicked(btn5, 4)
            5 -> clicked(btn6, 5)
            6 -> clicked(btn7, 6)
            7 -> clicked(btn8, 7)
            8 -> clicked(btn9, 8)
            9 -> clicked(btn10, 9)
        }


        btn1.setOnClickListener {
            clicked(it, 0)
        }

        btn2.setOnClickListener {
            clicked(it, 1)
        }

        btn3.setOnClickListener {
            clicked(it, 2)
        }
        btn4.setOnClickListener {
            clicked(it, 3)
        }

        btn5.setOnClickListener {
            clicked(it, 4)
        }

        btn6.setOnClickListener {
            clicked(it, 5)
        }

        btn7.setOnClickListener {
            clicked(it, 6)
        }

        btn8.setOnClickListener {
            clicked(it, 7)
        }
        btn9.setOnClickListener {
            clicked(it, 8)
        }

        btn10.setOnClickListener {
            clicked(it, 9)
        }

        view.findViewById<AppCompatButton>(R.id.btn_finish).setOnClickListener {

            clickListener.inputData(str, name)
            dismiss()
        }


    }

    override fun onStart() {
        super.onStart()

        val window: Window = dialog!!.window!!

        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


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


    var clickedButton: CustomFilterButton? = null
    var str = 0
    var name = ""




}