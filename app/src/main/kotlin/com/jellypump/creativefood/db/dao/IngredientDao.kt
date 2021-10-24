package com.jellypump.creativefood.db.dao

import android.database.sqlite.SQLiteConstraintException
import androidx.room.*
import com.jellypump.creativefood.db.entity.*
import io.reactivex.Completable
import io.reactivex.Single
import timber.log.Timber

@Dao
interface IngredientDao {

    @Insert
    fun insertIngredient(ingredient: IngredientEntity)

    @Update
    fun updateIngredient(ingredient: IngredientEntity)

    @Query("SELECT * FROM $INGREDIENT_TABLE_NAME")
    fun getAll(): Single<List<IngredientEntity>>

    @Query("SELECT * FROM $INGREDIENT_TABLE_NAME WHERE ingredient_name =(:name)")
    fun getByName(name: String): Single<IngredientEntity>

    @Query("SELECT EXISTS(SELECT * FROM $INGREDIENT_TABLE_NAME WHERE ingredient_name =(:name))")
    fun isIngredientInserted(name: String): Boolean

    @Insert
    fun insertIngredientTag(ingredientTag: IngredientTagEntity)

    @Transaction
    @Query("DELETE FROM $INGREDIENT_TAG_JOIN_TABLE_NAME WHERE ingredient_name =(:ingredientName)")
    fun deleteAllIngredientTags(ingredientName: String)

    @Transaction
    @Query("SELECT * FROM $INGREDIENT_TABLE_NAME")
    fun getIngredientsWithTags(): Single<List<IngredientWithTagsEntity>>

    @Transaction
    fun insertIngredientWithTagSync(ingredientEntity: IngredientEntity, tags: List<TagEntity>) {
        insertIngredient(ingredientEntity)
        tags.forEach {
            val ingredientTag = IngredientTagEntity(ingredientEntity.name, it.name)
            insertIngredientTag(ingredientTag)
        }
    }

    @Transaction
    fun updateIngredientWithTagSync(ingredientEntity: IngredientEntity, tags: List<TagEntity>) {
        updateIngredient(ingredientEntity)
        deleteAllIngredientTags(ingredientEntity.name)
        tags.forEach {
            val ingredientTag = IngredientTagEntity(ingredientEntity.name, it.name)
            insertIngredientTag(ingredientTag)
        }
    }

    @Transaction
    fun upsertIngredientWithTagSync(ingredientEntity: IngredientEntity, tags: List<TagEntity>) {
        val isAlreadyInserted = isIngredientInserted(ingredientEntity.name)
        if (isAlreadyInserted) {
            updateIngredient(ingredientEntity)
            deleteAllIngredientTags(ingredientEntity.name)
        } else {
            insertIngredient(ingredientEntity)
            tags.forEach {
                val ingredientTag = IngredientTagEntity(ingredientEntity.name, it.name)
                insertIngredientTag(ingredientTag)
            }
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
