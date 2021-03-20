package desla.aos.eating.data.model

import java.io.Serializable

data class MapSearch(
        val Title: String?,
        val Address: String,
        val road: String?,
        val x: Double,
        val y: Double
): Serializable