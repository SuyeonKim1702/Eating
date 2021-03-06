package desla.aos.eating.data.model


import com.google.gson.annotations.SerializedName

data class KeywordAPI(
    @SerializedName("documents")
    val documents: List<Document>,
    @SerializedName("meta")
    val meta: Meta
) {
    data class Document(
        @SerializedName("address_name")
        val addressName: String,
        @SerializedName("category_group_code")
        val categoryGroupCode: String,
        @SerializedName("category_group_name")
        val categoryGroupName: String,
        @SerializedName("category_name")
        val categoryName: String,
        @SerializedName("distance")
        val distance: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("place_name")
        val placeName: String,
        @SerializedName("place_url")
        val placeUrl: String,
        @SerializedName("road_address_name")
        val roadAddressName: String,
        @SerializedName("x")
        val x: String,
        @SerializedName("y")
        val y: String
    )

    data class Meta(
        @SerializedName("is_end")
        val isEnd: Boolean,
        @SerializedName("pageable_count")
        val pageableCount: Int,
        @SerializedName("same_name")
        val sameName: SameName,
        @SerializedName("total_count")
        val totalCount: Int
    ) {
        data class SameName(
            @SerializedName("keyword")
            val keyword: String,
            @SerializedName("region")
            val region: List<Any>,
            @SerializedName("selected_region")
            val selectedRegion: String
        )
    }
}