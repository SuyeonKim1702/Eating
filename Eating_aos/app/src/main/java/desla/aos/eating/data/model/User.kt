package desla.aos.eating.data.model


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("fastAnswer")
        val fastAnswer: Int,
        @SerializedName("foodDivide")
        val foodDivide: Int,
        @SerializedName("niceGuy")
        val niceGuy: Int,
        @SerializedName("nickname")
        val nickname: String,
        @SerializedName("reviews")
        val reviews: List<Review>,
        @SerializedName("sugarScore")
        val sugarScore: Int,
        @SerializedName("timeGood")
        val timeGood: Int,
        @SerializedName("totalCount")
        val totalCount: Int
    ) {
        data class Review(
            @SerializedName("createdDate")
            val createdDate: String,
            @SerializedName("deletedDate")
            val deletedDate: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("member")
            val member: Member,
            @SerializedName("review")
            val review: String,
            @SerializedName("sender")
            val sender: Sender,
            @SerializedName("updatedDate")
            val updatedDate: String
        ) {
            data class Member(
                @SerializedName("createdDate")
                val createdDate: String,
                @SerializedName("deletedDate")
                val deletedDate: String,
                @SerializedName("distance")
                val distance: Int,
                @SerializedName("fastAnswer")
                val fastAnswer: Int,
                @SerializedName("favoritePosts")
                val favoritePosts: List<FavoritePost>,
                @SerializedName("foodDivide")
                val foodDivide: Int,
                @SerializedName("id")
                val id: Int,
                @SerializedName("kakaoId")
                val kakaoId: String,
                @SerializedName("location")
                val location: Location,
                @SerializedName("memberCategory")
                val memberCategory: List<String>,
                @SerializedName("memberPosts")
                val memberPosts: List<MemberPost>,
                @SerializedName("niceGuy")
                val niceGuy: Int,
                @SerializedName("nickname")
                val nickname: String,
                @SerializedName("password")
                val password: String,
                @SerializedName("profileUrl")
                val profileUrl: String,
                @SerializedName("reviews")
                val reviews: List<Any>,
                @SerializedName("sugarScore")
                val sugarScore: Int,
                @SerializedName("timeGood")
                val timeGood: Int,
                @SerializedName("totalCount")
                val totalCount: Int,
                @SerializedName("updatedDate")
                val updatedDate: String
            ) {
                data class FavoritePost(
                    @SerializedName("id")
                    val id: Int,
                    @SerializedName("post")
                    val post: Post
                ) {
                    data class Post(
                        @SerializedName("category")
                        val category: String,
                        @SerializedName("chatLink")
                        val chatLink: String,
                        @SerializedName("createdDate")
                        val createdDate: String,
                        @SerializedName("deletedDate")
                        val deletedDate: String,
                        @SerializedName("deliveryFeeByHost")
                        val deliveryFeeByHost: Int,
                        @SerializedName("description")
                        val description: String,
                        @SerializedName("finished")
                        val finished: Boolean,
                        @SerializedName("foodLink")
                        val foodLink: String,
                        @SerializedName("id")
                        val id: Int,
                        @SerializedName("latitude")
                        val latitude: Int,
                        @SerializedName("longitude")
                        val longitude: Int,
                        @SerializedName("meetPlace")
                        val meetPlace: String,
                        @SerializedName("memberCountLimit")
                        val memberCountLimit: Int,
                        @SerializedName("memberPosts")
                        val memberPosts: List<Any>,
                        @SerializedName("orderTime")
                        val orderTime: String,
                        @SerializedName("title")
                        val title: String,
                        @SerializedName("updatedDate")
                        val updatedDate: String
                    )
                }

                data class Location(
                    @SerializedName("address")
                    val address: String,
                    @SerializedName("createdDate")
                    val createdDate: String,
                    @SerializedName("deletedDate")
                    val deletedDate: String,
                    @SerializedName("id")
                    val id: Int,
                    @SerializedName("latitude")
                    val latitude: Int,
                    @SerializedName("longitude")
                    val longitude: Int,
                    @SerializedName("updatedDate")
                    val updatedDate: String
                )

                data class MemberPost(
                    @SerializedName("id")
                    val id: Int,
                    @SerializedName("post")
                    val post: Post
                ) {
                    data class Post(
                        @SerializedName("category")
                        val category: String,
                        @SerializedName("chatLink")
                        val chatLink: String,
                        @SerializedName("createdDate")
                        val createdDate: String,
                        @SerializedName("deletedDate")
                        val deletedDate: String,
                        @SerializedName("deliveryFeeByHost")
                        val deliveryFeeByHost: Int,
                        @SerializedName("description")
                        val description: String,
                        @SerializedName("finished")
                        val finished: Boolean,
                        @SerializedName("foodLink")
                        val foodLink: String,
                        @SerializedName("id")
                        val id: Int,
                        @SerializedName("latitude")
                        val latitude: Int,
                        @SerializedName("longitude")
                        val longitude: Int,
                        @SerializedName("meetPlace")
                        val meetPlace: String,
                        @SerializedName("memberCountLimit")
                        val memberCountLimit: Int,
                        @SerializedName("memberPosts")
                        val memberPosts: List<Any>,
                        @SerializedName("orderTime")
                        val orderTime: String,
                        @SerializedName("title")
                        val title: String,
                        @SerializedName("updatedDate")
                        val updatedDate: String
                    )
                }
            }

            data class Sender(
                @SerializedName("createdDate")
                val createdDate: String,
                @SerializedName("deletedDate")
                val deletedDate: String,
                @SerializedName("distance")
                val distance: Int,
                @SerializedName("fastAnswer")
                val fastAnswer: Int,
                @SerializedName("favoritePosts")
                val favoritePosts: List<FavoritePost>,
                @SerializedName("foodDivide")
                val foodDivide: Int,
                @SerializedName("id")
                val id: Int,
                @SerializedName("kakaoId")
                val kakaoId: String,
                @SerializedName("location")
                val location: Location,
                @SerializedName("memberCategory")
                val memberCategory: List<String>,
                @SerializedName("memberPosts")
                val memberPosts: List<MemberPost>,
                @SerializedName("niceGuy")
                val niceGuy: Int,
                @SerializedName("nickname")
                val nickname: String,
                @SerializedName("password")
                val password: String,
                @SerializedName("profileUrl")
                val profileUrl: String,
                @SerializedName("reviews")
                val reviews: List<Any>,
                @SerializedName("sugarScore")
                val sugarScore: Int,
                @SerializedName("timeGood")
                val timeGood: Int,
                @SerializedName("totalCount")
                val totalCount: Int,
                @SerializedName("updatedDate")
                val updatedDate: String
            ) {
                data class FavoritePost(
                    @SerializedName("id")
                    val id: Int,
                    @SerializedName("post")
                    val post: Post
                ) {
                    data class Post(
                        @SerializedName("category")
                        val category: String,
                        @SerializedName("chatLink")
                        val chatLink: String,
                        @SerializedName("createdDate")
                        val createdDate: String,
                        @SerializedName("deletedDate")
                        val deletedDate: String,
                        @SerializedName("deliveryFeeByHost")
                        val deliveryFeeByHost: Int,
                        @SerializedName("description")
                        val description: String,
                        @SerializedName("finished")
                        val finished: Boolean,
                        @SerializedName("foodLink")
                        val foodLink: String,
                        @SerializedName("id")
                        val id: Int,
                        @SerializedName("latitude")
                        val latitude: Int,
                        @SerializedName("longitude")
                        val longitude: Int,
                        @SerializedName("meetPlace")
                        val meetPlace: String,
                        @SerializedName("memberCountLimit")
                        val memberCountLimit: Int,
                        @SerializedName("memberPosts")
                        val memberPosts: List<Any>,
                        @SerializedName("orderTime")
                        val orderTime: String,
                        @SerializedName("title")
                        val title: String,
                        @SerializedName("updatedDate")
                        val updatedDate: String
                    )
                }

                data class Location(
                    @SerializedName("address")
                    val address: String,
                    @SerializedName("createdDate")
                    val createdDate: String,
                    @SerializedName("deletedDate")
                    val deletedDate: String,
                    @SerializedName("id")
                    val id: Int,
                    @SerializedName("latitude")
                    val latitude: Int,
                    @SerializedName("longitude")
                    val longitude: Int,
                    @SerializedName("updatedDate")
                    val updatedDate: String
                )

                data class MemberPost(
                    @SerializedName("id")
                    val id: Int,
                    @SerializedName("post")
                    val post: Post
                ) {
                    data class Post(
                        @SerializedName("category")
                        val category: String,
                        @SerializedName("chatLink")
                        val chatLink: String,
                        @SerializedName("createdDate")
                        val createdDate: String,
                        @SerializedName("deletedDate")
                        val deletedDate: String,
                        @SerializedName("deliveryFeeByHost")
                        val deliveryFeeByHost: Int,
                        @SerializedName("description")
                        val description: String,
                        @SerializedName("finished")
                        val finished: Boolean,
                        @SerializedName("foodLink")
                        val foodLink: String,
                        @SerializedName("id")
                        val id: Int,
                        @SerializedName("latitude")
                        val latitude: Int,
                        @SerializedName("longitude")
                        val longitude: Int,
                        @SerializedName("meetPlace")
                        val meetPlace: String,
                        @SerializedName("memberCountLimit")
                        val memberCountLimit: Int,
                        @SerializedName("memberPosts")
                        val memberPosts: List<Any>,
                        @SerializedName("orderTime")
                        val orderTime: String,
                        @SerializedName("title")
                        val title: String,
                        @SerializedName("updatedDate")
                        val updatedDate: String
                    )
                }
            }
        }
    }
}