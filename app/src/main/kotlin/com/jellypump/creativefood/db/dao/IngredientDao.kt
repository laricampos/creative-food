package com.jellypump.creativefood.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jellypump.creativefood.db.model.INGREDIENT_TABLE_NAME
import com.jellypump.creativefood.db.model.Ingredient
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface IngredientDao {

    @Insert
    fun insert(ingredient: Ingredient) : Completable

    @Query("SELECT * FROM $INGREDIENT_TABLE_NAME")
    fun getAll(): Single<List<Ingredient>>

    @Query("SELECT * FROM $INGREDIENT_TABLE_NAME WHERE name =(:name)")
    fun getByName(name: String): Single<Ingredient>
}
