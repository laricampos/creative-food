package com.jellypump.creativefood.model

data class Ingredient(
    val name: String,
    val healthScore: Int,
    val tasteScore: Int,
    val tags: List<Tag>
)