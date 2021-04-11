package desla.aos.eating.ui.view.client

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog
import desla.aos.eating.data.model.DetailResponse
import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.data.repositories.PostRepository
import desla.aos.eating.data.repositories.ViewRepository
import desla.aos.eating.ui.base.BaseViewModel
import desla.aos.eating.util.getActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat


class ViewViewModel(val repository: ViewRepository) : BaseViewModel(){

    private val TAG = "PostViewModel"

    var beforeData : PostsResponse.Data? = null

    private val _data = MutableLiveData<DetailResponse.Data>()
    val data : LiveData<DetailResponse.Data>
        get() = _data

    fun clickBackBtn(v: View){
        v.context.getActivity()?.finish()
    }

    fun goStore(v: View){

        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.value?.foodLink))
            v.context.startActivity(intent)

        } catch (activityNotFound: ActivityNotFoundException) {

            _message.value = "입력된 가게 주소가 없습니다"
        }

    }


    fun goChat(v: View){
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.value?.chatLink))
            v.context.startActivity(intent)
            sendJoin()

        } catch (activityNotFound: ActivityNotFoundException) {

            _message.value = "입력된 채팅 주소가 없습니다"
        }

    }


    private val _favorite = MutableLiveData<Boolean>()
    val favorite : LiveData<Boolean>
        get() = _favorite

    fun goFavorite(v: View){

        if((v as ImageView).isSelected){
           //좋아요 취소
            val disposable = repository.postUnFavorite(beforeData?.postId!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if(it.isSuccessful){
                            if(it.code() == 200){

                                _favorite.value = false

                                Log.i("eunjin", "찜하기 취소 성공 ${it.code()}" )
                            }else{
                                Log.i("eunjin", "찜하기 취소 실패 ${it.body()?.status} ${it.body()?.message} }" )
                            }

                        }else{
                            Log.i("eunjin", "${it.code()} 찜하기 취소 실패2${it.body()?.status}  ${it.body()?.message}" )
                        }

                    }, {

                        Log.i("eunjin", "찜하기 취소 실패3" )
                    })
            addDisposable(disposable)

        }else{
            val disposable = repository.postFavorite(beforeData?.postId!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if(it.isSuccessful){
                            if(it.code() == 200){

                                _favorite.value = true

                                Log.i("eunjin", "찜하기 성공1 ${it.code()}" )
                            }else{
                                Log.i("eunjin", "찜하기 실패2 ${it.body()?.status} ${it.body()?.message} }" )
                            }

                        }else{
                            Log.i("eunjin", "${it.code()} 찜하기 실패3${it.body()?.status}  ${it.body()?.message}" )
                        }

                    }, {

                        Log.i("eunjin", "찜하기 실패4" )
                    })
            addDisposable(disposable)
        }


    }

    private fun sendJoin(){

        val disposable = repository.postJoin(beforeData?.postId!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.isSuccessful){
                        if(it.code() == 200){
                            Log.i("eunjin", "참여 성공 ${it.code()}" )
                        }else{
                            Log.i("eunjin", "참여 실패 ${it.body()?.status} ${it.body()?.message} }" )
                        }

                    }else{
                        Log.i("eunjin", "${it.code()} 참여 실패2${it.body()?.status}  ${it.body()?.message}" )
                    }

                }, {

                    Log.i("eunjin", "참여 실패3" )
                })
        addDisposable(disposable)

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