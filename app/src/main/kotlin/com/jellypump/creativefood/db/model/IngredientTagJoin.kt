package com.jellypump.creativefood.db.model

import androidx.room.Entity
import androidx.room.ForeignKey

const val INGREDIENT_TAG_JOIN_TABLE_NAME = "ingredient_tag_joins"

@Entity(
    tableName = INGREDIENT_TAG_JOIN_TABLE_NAME,
    primaryKeys = ["ingredientId", "tagId"],
    foreignKeys = [
        ForeignKey(
            entity = Ingredient::class,
            parentColumns = arrayOf("name"),
            childColumns = arrayOf("ingredientId")
        ),
        ForeignKey(
            entity = Tag::class,
            parentColumns = arrayOf("name"),
            childColumns = arrayOf("tagId")
        )]
)
class IngredientTagJoin(val ingredientId: Int, val tagId: Int)