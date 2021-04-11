package desla.aos.eating.ui.custom

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import desla.aos.eating.R

class NoFuncDialog(context : Context) {


    private val dlg = Dialog(context, R.style.DialogTheme)   //부모 액티비티의 context 가 들어감


    fun start() {
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.dialog_nofunc)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(true)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함


        val btn_yes = dlg.findViewById<AppCompatButton>(R.id.btn_yes)
        btn_yes.setOnClickListener {
            dlg.dismiss()
        }


        dlg.show()
    }

}