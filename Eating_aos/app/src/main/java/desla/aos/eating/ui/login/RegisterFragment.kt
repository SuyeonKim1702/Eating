package desla.aos.eating.ui.login

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.R
import desla.aos.eating.data.repositories.UserRepository
import desla.aos.eating.databinding.FragmentRegisterBinding
import desla.aos.eating.ui.base.BaseFragment
import desla.aos.eating.util.CameraUtil
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment :  BaseFragment<FragmentRegisterBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_register

    private lateinit var viewModel: LoginViewModel



    private val TAG = "RegisterFragment"

    override fun initStartView() {
        val repository = UserRepository()

        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        viewDataBinding.vm = viewModel
    }

    override fun initDataBinding() {

        profile_img.setOnClickListener {
            viewModel.setPermission(it)
        }


    }

    override fun initAfterBinding() {
        viewDataBinding.profileImg.background = ShapeDrawable(OvalShape())
        viewDataBinding.profileImg.clipToOutline = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == viewModel.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //사진찍은거 파일로 저장하고 가져오기
            val uri = CameraUtil.getInstance(requireContext()).makeBitmap(viewDataBinding.profileImg)
            CameraUtil.getInstance(requireContext()).setImageView(viewDataBinding.profileImg, uri)

            println("사진 $uri")

        }

        //사진을 갖고왔을 때
        if (resultCode == Activity.RESULT_OK && requestCode == viewModel.REQUEST_IMAGE_PICK && data != null) {
            val uri = data.data!!
            CameraUtil.getInstance(requireContext()).setImageView(viewDataBinding.profileImg, uri)
        }

    }

    // 실제 경로 찾기
    private fun getPath(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().managedQuery(uri, projection, null, null, null)
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }



}