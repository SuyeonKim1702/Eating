package desla.aos.eating.ui.post

import android.util.Log
import android.view.View
import desla.aos.eating.ui.base.BaseViewModel
import desla.aos.eating.util.getActivity


class PostViewModel : BaseViewModel() , CategoryDialog.CategoryClickListener{

    private val TAG = "PostViewModel"

    lateinit var categoryDialog: CategoryDialog

    fun showCategoryDialog(v: View){
        val activity = v.context.getActivity() as PostActivity
        val fm = activity.supportFragmentManager
        categoryDialog = CategoryDialog(this)
        categoryDialog.show(fm, "category")
    }

    override fun inputData(email: String, pw: String) {

    }




}