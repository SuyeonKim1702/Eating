package desla.aos.eating.data.model

import java.io.Serializable

data class PostResponse(
        val data: String,
        val status : String,
        val message : String
): Serializable