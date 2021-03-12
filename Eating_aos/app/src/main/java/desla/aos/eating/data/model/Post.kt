package desla.aos.eating.data.model

import java.io.Serializable

data class Post(
    val id: Int,
    val distance: Int,
    val title: String
): Serializable