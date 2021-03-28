package desla.aos.eating.data.model


import com.google.gson.annotations.SerializedName

data class DetailResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("category")
        val category: Int,
        @SerializedName("categoryURL")
        val categoryURL: String,
        @SerializedName("chatLink")
        val chatLink: String,
        @SerializedName("currentMemberCount")
        val currentMemberCount: Int,
        @SerializedName("deliveryFeeByHost")
        val deliveryFeeByHost: Int,
        @SerializedName("description")
        val description: String,
        @SerializedName("finished")
        val finished: Boolean,
        @SerializedName("foodLink")
        val foodLink: String,
        @SerializedName("meetPlace")
        val meetPlace: Int,
        @SerializedName("memberCountLimit")
        val memberCountLimit: Int,
        @SerializedName("orderTime")
        val orderTime: String,
        @SerializedName("title")
        val title: String
    )
}