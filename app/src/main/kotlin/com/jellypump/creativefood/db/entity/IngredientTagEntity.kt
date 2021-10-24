package com.jellypump.creativefood.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

const val INGREDIENT_TAG_JOIN_TABLE_NAME = "IngredientTagEntity"

@Entity(
    tableName = INGREDIENT_TAG_JOIN_TABLE_NAME,
    primaryKeys = ["ingredient_name", "tag_name"]
)
class IngredientTagEntity(
    @ColumnInfo(name = "ingredient_name", index = true) val ingredientName: String,
    @ColumnInfo(name = "tag_name", index = true) val tagName: String)