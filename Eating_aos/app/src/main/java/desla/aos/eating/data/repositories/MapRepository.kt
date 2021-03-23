package desla.aos.eating.data.repositories

import desla.aos.eating.data.model.*
import desla.aos.eating.data.network.KakaoMapApi
import desla.aos.eating.data.network.ServerApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MapRepository (
    private val api: KakaoMapApi,
    private val server: ServerApi
)  {

    fun postRegister(rR: RequestRegister) : Single<PostResponse> =

        //한 페이지에 보여질 문서의 개수, 1-30 사이, 기본 값 10
        server.postRegister(rR)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())



    fun getLocationWithKeyword(query: String, authorization: String) : Single<KeywordAPI> =

            //한 페이지에 보여질 문서의 개수, 1-30 사이, 기본 값 10
            api.getLocationWithKeyword(query,  authorization)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    fun getAddressWithQuery(query: String, authorization: String) : Single<AddressAPI> =

        //한 페이지에 보여질 문서의 개수, 1-30 사이, 기본 값 10
        api.getLocationWithAddress(query, 30, authorization)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getAddressWithGeo(x: Double, y: Double, authorization: String) : Single<GeoAPI> =

            api.getAddressWithPoint(x.toString(), y.toString(), authorization)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())


}