package com.jellypump.creativefood.db.mapper

import com.jellypump.creativefood.db.entity.IngredientEntity
import com.jellypump.creativefood.db.entity.IngredientWithTagsEntity
import com.jellypump.creativefood.dto.IngredientDto
import com.jellypump.creativefood.model.Ingredient
import com.jellypump.creativefood.model.Tag

fun IngredientWithTagsEntity.toModel() = Ingredient(
    name = ingredient.name,
    healthScore = ingredient.healthScore,
    tasteScore = ingredient.tasteScore,
    tags = tags.map {
        Tag(
            name = it.name,
            colour = it.colour
        )
    }
)

fun Ingredient.toEntity() = IngredientEntity(
    name = name,
    healthScore = healthScore,
    tasteScore = tasteScore
