package com.jellypump.creativefood.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag(
    val id: Long,
    val name: String,
    val colour: Int
) : Parcelable