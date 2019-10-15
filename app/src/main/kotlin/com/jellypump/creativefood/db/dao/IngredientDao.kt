package com.jellypump.creativefood.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jellypump.creativefood.db.model.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface IngredientDao {

    @Insert
    fun insert(ingredient: Ingredient) : Single<Long>

    @Query("SELECT * FROM $INGREDIENT_TABLE_NAME")
    fun getAll(): Single<List<Ingredient>>

    @Query("SELECT * FROM $INGREDIENT_TAG_JOIN_TABLE_NAME INNER JOIN $INGREDIENT_TABLE_NAME ON $INGREDIENT_TAG_JOIN_TABLE_NAME.ingredientId=$INGREDIENT_TABLE_NAME.id INNER JOIN $TAG_TABLE_NAME ON $TAG_TABLE_NAME.id = $INGREDIENT_TAG_JOIN_TABLE_NAME.tagId WHERE ingredientId =(:id)")
    fun getTagsByIngredient(id: Int): Single<List<Tag>>

    @Query("SELECT * FROM $INGREDIENT_TABLE_NAME WHERE name =(:name)")
    fun getByName(name: String): Single<Ingredient>

    @Insert
    fun insertIngredientTag(ingredientTag: IngredientTagJoin): Completable
}
