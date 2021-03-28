package desla.aos.eating.ui.home

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.data.repositories.HomeRepository
import desla.aos.eating.ui.MyApplication
import desla.aos.eating.ui.base.BaseViewModel
import desla.aos.eating.util.getActivity
import desla.aos.eating.util.startMainActivity
import desla.aos.eating.util.startPostActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
        private val repository: HomeRepository
) : BaseViewModel() {


        private val _postList = MutableLiveData<List<PostsResponse.Data>>()
        val postList : LiveData<List<PostsResponse.Data>>
                get() = _postList


        private val _address = MutableLiveData<String>()
        val address : LiveData<String>
                get() = _address

        private val _distance = MutableLiveData<Int>()
        val distance : LiveData<Int>
                get() = _distance

        fun setAddress(str: String){
                _address.value = str
        }

        fun setDistance(dis: Int){
                _distance.value = dis
        }






        fun getPost(){


                val disposable = repository.getPosts(
                   "0-1-2-3-4-5-6-7-8-9", 1000, 0, 20
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                                if(it.isSuccessful){
                                        if(it.body()?.status == "OK"){
                                                Log.i("eunjin", "글 불러오기 성공 ${it.code()} ${it.body()?.data?.size}" )

                                                _postList.value = it.body()?.data

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