package com.jellypump.creativefood.repo

import com.jellypump.creativefood.db.dao.IngredientDao
import com.jellypump.creativefood.db.model.Ingredient
import com.jellypump.creativefood.db.model.IngredientTagJoin
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
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
            ingredientDao.getTagsByIngredient(ingredient.id).map { tags ->
                ingredient.apply {
                    this.tags = tags
                }
            }.toFlowable()
        }.toList()

    fun addIngredient(ingredient: Ingredient): Completable = ingredientDao.insert(ingredient)
        .flatMapCompletable { id ->
            (Flowable.fromIterable(ingredient.tags))
                .flatMapCompletable { tag ->
                    ingredientDao.insertIngredientTag(IngredientTagJoin(id.toInt(), tag.id))
                }
        }
}