package com.jellypump.creativefood.db.mapper

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