package com.jellypump.creativefood.repo

import com.jellypump.creativefood.db.dao.IngredientDao
import com.jellypump.creativefood.db.model.Ingredient
import com.jellypump.creativefood.db.model.Tag
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IngredientRepo @Inject constructor(
    private val ingredientDao: IngredientDao
) {

    val allIngredients: Single<List<Ingredient>>
        get() = ingredientDao.getAll().flatMapPublisher {
            Flowable.fromIterable(it)
        }.flatMap { ingredient ->
            ingredientDao.getTagsByIngredient(ingredient.name).map { tags ->
                ingredient.apply {
                    this.tags = tags
                }
            }.toFlowable()
        }.toList()

    fun getIngredient(name: String): Single<Ingredient> =
        Single.zip(ingredientDao.getByName(name), ingredientDao.getTagsByIngredient(name), BiFunction { ingredient: Ingredient, tags: List<Tag> ->
            ingredient.apply {
                this.tags = tags
            }
        })
}