package desla.aos.eating.data.repositories

import desla.aos.eating.data.model.AddressAPI
import desla.aos.eating.data.model.GeoAPI
import desla.aos.eating.data.model.KeywordAPI
import desla.aos.eating.data.network.KakaoMapApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MapRepository (
    private val api: KakaoMapApi
)  {

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