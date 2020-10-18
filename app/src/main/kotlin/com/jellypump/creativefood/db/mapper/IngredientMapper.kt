package com.jellypump.creativefood.db.mapper

import com.jellypump.creativefood.db.entity.IngredientEntity
import com.jellypump.creativefood.db.entity.IngredientWithTagsEntity
import com.jellypump.creativefood.model.Ingredient
import com.jellypump.creativefood.model.Tag

fun IngredientWithTagsEntity.toModel() = Ingredient(
    id = ingredient.ingredientId,
    name = ingredient.name,
    healthScore = ingredient.healthScore,
    tasteScore = ingredient.tasteScore,
    tags = tags.map {
        Tag(
            id = it.tagId,
            name = it.name,
            colour = it.colour
        )
    }
)

fun Ingredient.toEntity() = IngredientEntity(
    ingredientId = id,
    name = name,
    healthScore = healthScore,
    tasteScore = tasteScore
)