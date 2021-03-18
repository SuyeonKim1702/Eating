package desla.aos.eating.data.network

import desla.aos.eating.data.model.AddressAPI
import desla.aos.eating.data.model.GeoAPI
import desla.aos.eating.data.model.KeywordAPI
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoMapApi {

    @GET("search/keyword.json")
    fun getLocationWithKeyword(
            @Query("query") query: String,
            @Header("Authorization") Authorization: String
    ): Single<KeywordAPI>

    @GET("search/address.json")
    fun getLocationWithAddress(
            @Query("query") query: String,
            @Query("size") size: Int,
            @Header("Authorization") Authorization: String
    ): Single<AddressAPI>

    @GET("geo/coord2address.json")
    fun getAddressWithPoint(
            @Query("x") x : String, //경도
            @Query("y") y: String, //위도
            @Header("Authorization") Authorization: String
    ): Single<GeoAPI>


    companion object{
        operator fun invoke() : KakaoMapApi {
            return Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("https://dapi.kakao.com/v2/local/")
                    .build()
                    .create(KakaoMapApi::class.java)
        }
    }


}