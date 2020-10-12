package com.jellypump.creativefood.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

const val INGREDIENT_TAG_JOIN_TABLE_NAME = "IngredientTagEntity"

@Entity(
    tableName = INGREDIENT_TAG_JOIN_TABLE_NAME,
    primaryKeys = ["ingredientId", "tagId"]
)
class IngredientTagEntity(
    @ColumnInfo(name = "ingredientId", index = true) val ingredientId: Long,
    @ColumnInfo(name = "tagId", index = true) val tagId: Long)