package desla.aos.eating.data.repositories

import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.data.network.ServerApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class LikeRepository(private val server: ServerApi) {

    fun getPostParticipated() : Single<Response<PostsResponse>> =

            server.getPostParticipated()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    fun getPostFavorite() : Single<Response<PostsResponse>> =

            server.getPostFavorite()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

}