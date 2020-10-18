package com.jellypump.creativefood.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredient(
    val id: Long,
    val name: String,
    val healthScore: Int,
    val tasteScore: Int,
    val tags: List<Tag>
): Parcelable