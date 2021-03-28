package desla.aos.eating.ui.like

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.data.repositories.HomeRepository
import desla.aos.eating.data.repositories.LikeRepository
import desla.aos.eating.data.repositories.UserRepository
import desla.aos.eating.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LikeViewModel(
        private val repository: LikeRepository
) : BaseViewModel() {



    private val _partiList = MutableLiveData<List<PostsResponse.Data>>()
    val partiList : LiveData<List<PostsResponse.Data>>
        get() = _partiList

    private val _likeList = MutableLiveData<List<PostsResponse.Data>>()
    val likeList : LiveData<List<PostsResponse.Data>>
        get() = _likeList

    fun getPartiPost(){

        val disposable = repository.getPostParticipated()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.isSuccessful){
                        if(it.body()?.status == "OK"){
                            Log.i("eunjin", "글 불러오기 성공 ${it.code()} ${it.body()?.data?.size}" )

                            _partiList.value = it.body()?.data

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

    fun getFavoritePost(){

        val disposable = repository.getPostFavorite()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.isSuccessful){
                        if(it.body()?.status == "OK"){
                            Log.i("eunjin", "글 불러오기 성공 ${it.code()} ${it.body()?.data?.size}" )

                            _likeList.value = it.body()?.data

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