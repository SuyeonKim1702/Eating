package desla.aos.eating.data.model


import com.google.gson.annotations.SerializedName

data class GeoAPI(
    @SerializedName("documents")
    val documents: List<Document>,
    @SerializedName("meta")
    val meta: Meta
) {
    data class Document(
        @SerializedName("address")
        val address: Address,
        @SerializedName("road_address")
        val roadAddress: RoadAddress
    ) {
        data class Address(
            @SerializedName("address_name")
            val addressName: String,
            @SerializedName("main_address_no")
            val mainAddressNo: String,
            @SerializedName("mountain_yn")
            val mountainYn: String,
            @SerializedName("region_1depth_name")
            val region1depthName: String,
            @SerializedName("region_2depth_name")
            val region2depthName: String,
            @SerializedName("region_3depth_name")
            val region3depthName: String,
            @SerializedName("sub_address_no")
            val subAddressNo: String,
            @SerializedName("zip_code")
            val zipCode: String
        )

        data class RoadAddress(
            @SerializedName("address_name")
            val addressName: String,
            @SerializedName("building_name")
            val buildingName: String,
            @SerializedName("main_building_no")
            val mainBuildingNo: String,
            @SerializedName("region_1depth_name")
            val region1depthName: String,
            @SerializedName("region_2depth_name")
            val region2depthName: String,
            @SerializedName("region_3depth_name")
            val region3depthName: String,
            @SerializedName("road_name")
            val roadName: String,
            @SerializedName("sub_building_no")
            val subBuildingNo: String,
            @SerializedName("underground_yn")
            val undergroundYn: String,
            @SerializedName("zone_no")
            val zoneNo: String
        )
    }

    data class Meta(
        @SerializedName("total_count")
        val totalCount: Int
    )
}