package com.jellypump.creativefood.dto

import com.google.gson.annotations.SerializedName

data class IngredientDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("health_score")
    val healthScore: Int,
    @SerializedName("taste_score")
    val tasteScore: Int,
    @SerializedName("tags")
    val tags: List<String>
)