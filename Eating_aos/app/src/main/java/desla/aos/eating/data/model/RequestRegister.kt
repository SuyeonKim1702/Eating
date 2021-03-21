package desla.aos.eating.data.model

import com.google.gson.annotations.SerializedName

/*
[request]
- kakao_id : 회원식별번호
- nickname : 닉네임
- profile : 프로필 사진 파일
- address : 주소
- latitude : 위도
- longitude : 경도
[response]
- status : 201
- message : "회원가입 성공"
- data : null
 */
data class RequestRegister(
        @SerializedName("kakao_id")
        val kakao_id: String,
        @SerializedName("nickname")
        val nickname: String,
        @SerializedName("profile")
        val profile: Int,
        @SerializedName("address")
        val address: String,
        @SerializedName("latitude")
        val latitude: Long,
        @SerializedName("longitude")
        val longitude: Long

)