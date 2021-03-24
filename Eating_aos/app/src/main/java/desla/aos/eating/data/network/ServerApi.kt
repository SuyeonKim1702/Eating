package desla.aos.eating.data.network

import desla.aos.eating.data.model.PostResponse
import desla.aos.eating.data.model.RequestRegister
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ServerApi {

    /*
[request]
- kakao_id : 회원식별번호
- nickname : 닉네임
- profile : 프로필 사진 파일
- address : 주소
- latitude : 위도
- longitude : 경도
[response]
- status : 201
- message : "회원가입 성공"
- data : null
     */

    @POST("member")
    fun postRegister(
           @Body param: RequestRegister
    ): Single<PostResponse>

    companion object {
        operator fun invoke(): ServerApi {
            return Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("http://3.34.214.72/")
                    .build()
                    .create(ServerApi::class.java)
        }
    }

}