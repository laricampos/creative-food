package com.jellypump.creativefood.db.dao

import androidx.room.*
import com.jellypump.creativefood.db.entity.*
import io.reactivex.Completable
import io.reactivex.Single
import timber.log.Timber

@Dao
interface IngredientDao {

    @Insert
    fun insertIngredient(ingredient: IngredientEntity): Long

    @Update
    fun updateIngredient(ingredient: IngredientEntity)

    @Query("SELECT * FROM $INGREDIENT_TABLE_NAME")
    fun getAll(): Single<List<IngredientEntity>>

    @Query("SELECT * FROM $INGREDIENT_TABLE_NAME WHERE name =(:name)")
    fun getByName(name: String): Single<IngredientEntity>

    @Insert
    fun insertIngredientTag(ingredientTag: IngredientTagEntity)

    @Transaction
    @Query("DELETE FROM $INGREDIENT_TAG_JOIN_TABLE_NAME WHERE ingredientId =(:ingredientTagId)")
    fun deleteAllIngredientTags(ingredientTagId: Long)

    @Transaction
    @Query("SELECT * FROM $INGREDIENT_TABLE_NAME")
    fun getIngredientsWithTags(): Single<List<IngredientWithTagsEntity>>

    @Transaction
    fun insertIngredientWithTagSync(ingredientEntity: IngredientEntity, tags: List<TagEntity>) {
        val id = insertIngredient(ingredientEntity)
        tags.forEach {
            val ingredientTag = IngredientTagEntity(id, it.tagId)
            insertIngredientTag(ingredientTag)
        }
    }

    @Transaction
    fun updateIngredientWithTagSync(ingredientEntity: IngredientEntity, tags: List<TagEntity>) {
        updateIngredient(ingredientEntity)
        deleteAllIngredientTags(ingredientEntity.ingredientId)
        tags.forEach {
            val ingredientTag = IngredientTagEntity(ingredientEntity.ingredientId, it.tagId)
            insertIngredientTag(ingredientTag)
        }
    }

    fun insertIngredientWithTag(ingredientEntity: IngredientEntity, tags: List<TagEntity>) =
        Completable.create {
            Timber.d("Ingredient inserted: $ingredientEntity/nwith tags: $tags")
            insertIngredientWithTagSync(ingredientEntity, tags)
            it.onComplete()
        }

    fun updateIngredientWithTag(ingredientEntity: IngredientEntity, tags: List<TagEntity>) =
        Completable.create {
            Timber.d("Ingredient updated: $ingredientEntity/nwith tags: $tags")
            updateIngredientWithTagSync(ingredientEntity, tags)
            it.onComplete()
        }
}
