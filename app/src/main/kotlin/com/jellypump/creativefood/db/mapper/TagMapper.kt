package com.jellypump.creativefood.db.mapper

import com.jellypump.creativefood.db.entity.TagEntity
import com.jellypump.creativefood.model.Tag

fun Tag.toEntity() = TagEntity(
    tagId = id,
    name = name,
    colour = colour
)

fun TagEntity.toModel() = Tag(
    id = tagId,
    name = name,
    colour = colour
)