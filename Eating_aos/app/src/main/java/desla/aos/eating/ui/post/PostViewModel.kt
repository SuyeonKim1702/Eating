package desla.aos.eating.ui.post

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog
import desla.aos.eating.data.repositories.PostRepository
import desla.aos.eating.ui.base.BaseViewModel
import desla.aos.eating.util.getActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat


class PostViewModel(val repository: PostRepository) : BaseViewModel() , CategoryDialog.CategoryClickListener{

    private val TAG = "PostViewModel"

    lateinit var categoryDialog: CategoryDialog


    data class Write(
        var category: Int?,
        var chatLink: String?,
        var deliveryFeeByHost: Int?,
        var description: String?,
        var foodLink: String?,
        var meetPlace: Int?,
        var memberCountLimit: Int?,
        var orderTime: String?,
        var title: String?
    )

    val data = Write(null,"",null,"",
        "",null,null,null,"")


    fun sendWrite(v: View){
        if(data.category == null || data.chatLink.isNullOrEmpty() ||
                data.deliveryFeeByHost == null || data.meetPlace == null || data.memberCountLimit == null ||
                data.orderTime == null || data.title.isNullOrEmpty()) return

        Log.i("eunjin", "post 글쓰기 작성 시작 ${data}")

        val params:HashMap<String, Any?> = HashMap<String, Any?>()
        params["category"] = data.category
        params["chatLink"] = data.chatLink
        params["deliveryFeeByHost"] = data.deliveryFeeByHost
        params["description"] = data.description
        params["foodLink"] = data.foodLink
        params["meetPlace"] = data.meetPlace
        params["memberCountLimit"] = data.memberCountLimit
        params["orderTime"] = data.orderTime
        params["title"] = data.title


        val disposable = repository.sendPost(
            params
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.isSuccessful){
                    if(it.body()?.status == "OK"){
                        Log.i("eunjin", "글쓰기 성공" )
                        v.context.getActivity()?.finish()
                    }else{
                        Log.i("eunjin", "글쓰기 실패1 ${it.body()?.status} ${it.body()?.message} ${params["nickname"]}" )
                    }

                }else{
                    Log.i("eunjin", "글쓰기 실패2 ${it.body()?.message} ${it.body()?.status}" )
                }

            }, {

                Log.i("eunjin", "글쓰기 실패3" )
            })
        addDisposable(disposable)

    }

    fun showCategoryDialog(v: View){
        val activity = v.context.getActivity() as PostActivity
        val fm = activity.supportFragmentManager
        categoryDialog = CategoryDialog(this, data.category)
        categoryDialog.show(fm, "category")
    }

    fun showTimeDialog(v: View){
        SingleDateAndTimePickerDialog.Builder(v.context)
            .bottomSheet()
            .curved()
            .listener { date ->

                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").let {
                    data.orderTime = it.format(date)
                }
                SimpleDateFormat("yyyy/MM/dd a HH:mm").let {
                    _date.value = it.format(date)
                }



            }
            .title("Simple")
            .minutesStep(1)
            .display()
    }


    private val _date = MutableLiveData<String>()
    val date : LiveData<String>
        get() = _date


    private val _category_name = MutableLiveData<String>()
    val category_name : LiveData<String>
        get() = _category_name

    override fun inputData(quary: Int, name: String) {
        data.category = quary
        _category_name.value = name

    }


}