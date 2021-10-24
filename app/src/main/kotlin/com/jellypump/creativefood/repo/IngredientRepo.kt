package com.jellypump.creativefood.repo

import com.jellypump.creativefood.db.dao.IngredientDao
import com.jellypump.creativefood.db.mapper.toEntity
import com.jellypump.creativefood.db.mapper.toModel
import com.jellypump.creativefood.model.Ingredient
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IngredientRepo @Inject constructor(
    private val ingredientDao: IngredientDao
) {

    val allIngredients: Single<List<Ingredient>>
        get() = ingredientDao.getIngredientsWithTags()
            .map {
                it.map { ingredientEntity ->
                    ingredientEntity.toModel()
                }
            }

    fun addOrUpdateIngredient(ingredient: Ingredient) = Completable.create { emitter ->
        val ingredientEntity = ingredient.toEntity()
        val tagEntities = ingredient.tags.map {
            it.toEntity()
        }
        ingredientDao.upsertIngredientWithTagSync(ingredientEntity, tagEntities)
        emitter.onComplete()
    }
}