package com.jellypump.creativefood.repo

import com.jellypump.creativefood.db.dao.IngredientDao
import com.jellypump.creativefood.db.model.Ingredient
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IngredientRepo @Inject constructor(
    private val ingredientDao: IngredientDao
) {

    val allIngredients: Single<List<Ingredient>>
        get() = ingredientDao.getAll()

}