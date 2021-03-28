package desla.aos.eating.ui.view.host

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import desla.aos.eating.data.model.DetailResponse
import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.data.repositories.ViewRepository
import desla.aos.eating.ui.base.BaseViewModel
import desla.aos.eating.util.getActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ViewHostViewModel(val repository: ViewRepository) : BaseViewModel(){

    private val TAG = "PostViewModel"

    var beforeData : PostsResponse.Data? = null

    private val _data = MutableLiveData<DetailResponse.Data>()
    val data : LiveData<DetailResponse.Data>
        get() = _data

    fun clickBackBtn(v: View){
        v.context.getActivity()?.finish()
    }

    fun goChat(v: View){
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.value?.chatLink))
            v.context.startActivity(intent)

        } catch (activityNotFound: ActivityNotFoundException) {

            _message.value = "입력된 채팅 주소가 없습니다"
        }

    }

    fun getDetailPost(){

        val disposable = repository.getDetailPost(beforeData?.postId!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.isSuccessful){
                        if(it.body()?.status == "OK"){
                            Log.i("eunjin", "글 불러오기 성공 ${it.code()}" )

                            _data.value = it.body()?.data

                        }else{
                            Log.i("eunjin", "글불러오기 실패 ${it.body()?.status} ${it.body()?.message} }" )
                        }

                    }else{
                        Log.i("eunjin", "${it.code()} 글 불러오기 실패2${it.body()?.status}  ${it.body()?.message}" )
                    }

                }, {

                    Log.i("eunjin", "글 불러오기 실패3" )
                })
        addDisposable(disposable)

    }

}