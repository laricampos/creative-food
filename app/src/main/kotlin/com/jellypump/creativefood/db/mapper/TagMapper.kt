package com.jellypump.creativefood.db.mapper

import android.content.Context
import androidx.core.content.ContextCompat
import com.jellypump.creativefood.R
import com.jellypump.creativefood.db.entity.TagEntity
import com.jellypump.creativefood.model.Tag

fun Tag.toEntity() = TagEntity(
    name = name,
    colour = colour
)

fun TagEntity.toModel() = Tag(
    name = name,
    colour = colour
)

private val randomTagColour
    get() = listOf(
        R.color.green_sushi,
        R.color.pink_cranberry,
        R.color.yellow_tulip,
        R.color.blue_eastern,
        R.color.orange_fire,
        R.color.red_flame,
        R.color.purple_wisteria,
        R.color.blue_curious
    ).random()

fun String.toTag(context: Context) = Tag(
    name = this,
    colour = ContextCompat.getColor(context, randomTagColour)
)