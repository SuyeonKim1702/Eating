package desla.aos.eating.ui.login

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

class CameraDialog(context : Context) {


    private val dlg = Dialog(context, R.style.DialogTheme)   //부모 액티비티의 context 가 들어감
    private lateinit var btnCamera: AppCompatButton
    private lateinit var btnGallery : AppCompatButton
    private lateinit var btnCancel : ImageButton

    private var cameraDialogListener: CameraDialogListener? = null

    //인터페이스 설정
    interface CameraDialogListener {
        fun onPositiveClicked(pos: Int)
    }

    //호출할 리스너 초기화
    fun setDialogListener(customDialogListener: CameraDialogListener?) {
        this.cameraDialogListener = customDialogListener
    }



    fun start() {
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.dialog_camera)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        btnCamera = dlg.findViewById(R.id.btn_camera)
        btnCamera.setOnClickListener {
            cameraDialogListener?.onPositiveClicked(1)
            dlg.dismiss()
        }

        btnCamera = dlg.findViewById(R.id.btn_gallery)
        btnCamera.setOnClickListener {
            cameraDialogListener?.onPositiveClicked(2)
            dlg.dismiss()
        }


        btnCancel = dlg.findViewById(R.id.btn_cancle)
        btnCancel.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }

}