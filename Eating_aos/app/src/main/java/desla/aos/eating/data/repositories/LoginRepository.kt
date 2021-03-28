package desla.aos.eating.data.repositories

import desla.aos.eating.data.model.*
import desla.aos.eating.data.network.KakaoMapApi
import desla.aos.eating.data.network.ServerApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class LoginRepository (
    private val server: ServerApi
)  {


    fun postLogin(rR: HashMap<String, Any>) : Single<Response<PostResponse>> =

        //한 페이지에 보여질 문서의 개수, 1-30 사이, 기본 값 10
        server.postLogin(rR)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())



}