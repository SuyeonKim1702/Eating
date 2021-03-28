package desla.aos.eating.data.model


import com.google.gson.annotations.SerializedName

data class PostsResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("categoryImage")
        val categoryImage: String,
        @SerializedName("deliveryFeeByHost")
        val deliveryFeeByHost: Int,
        @SerializedName("distance")
        val distance: Int,
        @SerializedName("favorite")
        val favorite: Boolean,
        @SerializedName("foodLink")
        val foodLink: String,
        @SerializedName("meetPlace")
        val meetPlace: Int,
        @SerializedName("memberCount")
        val memberCount: Int,
        @SerializedName("memberCountLimit")
        val memberCountLimit: Int,
        @SerializedName("orderTime")
        val orderTime: String,
        @SerializedName("postId")
        val postId: Int,
        @SerializedName("title")
        val title: String
    )
}