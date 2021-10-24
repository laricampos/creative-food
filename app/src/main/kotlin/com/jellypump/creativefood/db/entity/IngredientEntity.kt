package com.jellypump.creativefood.db.entity

import androidx.room.*

const val INGREDIENT_TABLE_NAME = "ingredients"
const val INGREDIENT_WITH_TAGS_TABLE_NAME = "ingredientWithTags"

@Entity(tableName = INGREDIENT_TABLE_NAME)
data class IngredientEntity(
    @PrimaryKey
    @ColumnInfo(name = "ingredient_name")
    val name: String,
    val healthScore: Int,
    val tasteScore: Int
)

@Entity(tableName = INGREDIENT_WITH_TAGS_TABLE_NAME)
data class IngredientWithTagsEntity(
    @Embedded val ingredient: IngredientEntity,
    @Relation(
        parentColumn = "ingredient_name",
        entityColumn = "tag_name",
        associateBy = Junction(IngredientTagEntity::class)
    )
    val tags: List<TagEntity>
)