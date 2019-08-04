package com.jellypump.creativefood.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

const val INGREDIENT_TABLE_NAME = "ingredients"

@Entity(tableName = INGREDIENT_TABLE_NAME)
data class Ingredient(
    @PrimaryKey val name: String,
    @ColumnInfo(name = "health_score") val healthScore: Int,
    @ColumnInfo(name = "taste_score") val tasteScore: Int
) {
    @Ignore
    var tags: List<Tag>? = null
        set(value) {
            if(field == null) {
                field = value
            } else {
                throw UnsupportedOperationException("Field cannot be reassigned")
            }
        }
}