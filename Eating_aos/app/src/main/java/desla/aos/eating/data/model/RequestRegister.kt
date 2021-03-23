package desla.aos.eating.data.model

import com.google.gson.annotations.SerializedName

/*
[request]
{
  "kakaoId": "eunjin",
  "nickname": "eunjin1",
  "profile": null,
  "address": "용인시",
  "latitude": 37.4,
  "longitude": 12.12
}
[response]
- status : 201
- message : "회원가입 성공"
- data : null
 */
data class RequestRegister(
        @SerializedName("kakaoId")
        val kakao_id: String,
        @SerializedName("nickname")
        val nickname: String?,
        @SerializedName("address")
        val address: String?,
        @SerializedName("latitude")
        val latitude: Double?,
        @SerializedName("longitude")
        val longitude: Double?
)