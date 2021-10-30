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

const val INGREDIENT_TAG_JOIN_TABLE_NAME = "IngredientTagCrossRef"

@Entity(
    tableName = INGREDIENT_TAG_JOIN_TABLE_NAME,
    primaryKeys = ["ingredient_name", "tag_name"]
)
data class IngredientTagCrossRef(
    @ColumnInfo(name = "ingredient_name", index = true) val ingredientName: String,
    @ColumnInfo(name = "tag_name", index = true) val tagName: String)

@Entity(tableName = INGREDIENT_WITH_TAGS_TABLE_NAME)
data class IngredientWithTagsEntity(
    @Embedded val ingredient: IngredientEntity,
    @Relation(
        parentColumn = "ingredient_name",
        entityColumn = "tag_name",
        associateBy = Junction(IngredientTagCrossRef::class)
    )
    val tags: List<TagEntity>
)