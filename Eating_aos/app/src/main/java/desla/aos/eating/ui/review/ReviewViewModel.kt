package desla.aos.eating.ui.review

import android.view.View
import androidx.lifecycle.ViewModel
import desla.aos.eating.ui.post.CategoryDialog
import desla.aos.eating.ui.post.PostActivity
import desla.aos.eating.util.getActivity

class ReviewViewModel : ViewModel(), ReviewFirstDialog.CategoryClickListener, ReviewSecondDialog.CategoryClickListener{

    lateinit var firstDialog: ReviewFirstDialog

    var emotion = 0
    var choice = 0

    lateinit var secondDialog: ReviewSecondDialog

    fun showFirstDialog(activity: ReviewActivity){
        val fm = activity.supportFragmentManager
        firstDialog = ReviewFirstDialog(this)
        firstDialog.show(fm, "first")
    }
    fun showSecondDialog(v: View){
        val activity = v.context.getActivity() as ReviewActivity
        val fm = activity.supportFragmentManager
        secondDialog = ReviewSecondDialog(this)
        secondDialog.show(fm, "first")
    }

    override fun clickFirstFinish(a1: Int, a2: Int, view: View) {
        firstDialog.dismiss()
        showSecondDialog(view)
    }

    override fun clickSecondFinish() {
       secondDialog.dismiss()
    }



}