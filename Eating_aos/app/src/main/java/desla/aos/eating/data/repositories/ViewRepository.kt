package desla.aos.eating.data.repositories

import desla.aos.eating.data.model.DetailResponse
import desla.aos.eating.data.model.PostResponse
import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.data.network.ServerApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class ViewRepository(private val server: ServerApi) {

    fun getDetailPost(postId: Int) : Single<Response<DetailResponse>> =

            server.getPostDetail(postId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    fun postJoin(postId: Int) : Single<Response<PostResponse>> =

        server.postJoin(postId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun postFavorite(postId: Int) : Single<Response<PostResponse>> =

        server.postFavorite(postId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun postUnFavorite(postId: Int) : Single<Response<PostResponse>> =

        server.postUnFavorite(postId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}