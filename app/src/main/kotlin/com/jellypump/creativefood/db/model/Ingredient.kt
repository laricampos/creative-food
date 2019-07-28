package com.jellypump.creativefood.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val INGREDIENT_TABLE_NAME = "ingredients"

@Entity(tableName = INGREDIENT_TABLE_NAME)
data class Ingredient(
    @PrimaryKey val name : String,
    @ColumnInfo(name = "health_score") val healthScore: Int,
    @ColumnInfo(name = "taste_score") val tasteScore: Int
)