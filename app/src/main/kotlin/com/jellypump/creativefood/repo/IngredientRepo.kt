package com.jellypump.creativefood.repo

import android.content.Context
import com.google.gson.Gson
import com.jellypump.creativefood.R
import com.jellypump.creativefood.db.dao.IngredientDao
import com.jellypump.creativefood.db.dao.TagDao
import com.jellypump.creativefood.db.entity.IngredientTagCrossRef
import com.jellypump.creativefood.db.mapper.toEntity
import com.jellypump.creativefood.db.mapper.toModel
import com.jellypump.creativefood.dto.IngredientDto
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

    private fun addIngredientList(ingredients: List<Ingredient>) {
        val ingredientEntities = ingredients.map { it.toEntity() }
        val tagEntities = ingredients.map { ingredient ->
            ingredient.tags.map { it.toEntity() }
        }.flatten().toSet()
        val ingredientTagCrossRefEntities = ingredients.map { ingredient ->
            ingredient.tags.map { IngredientTagCrossRef(ingredient.name, it.name) }
        }.flatten().toSet()
        ingredientDao.insertIngredientListWithTagSync(ingredientEntities, tagEntities, ingredientTagCrossRefEntities)
    }

    fun loadDefaultIngredients(context: Context) = Completable.create { emitter ->
        val reader = context.resources.openRawResource(R.raw.ingredient_list).reader()
        val ingredientDtoList = Gson().fromJson(reader, Array<IngredientDto>::class.java)
        val ingredientList = ingredientDtoList.map { it.toModel(context) }
        addIngredientList(ingredientList)
        emitter.onComplete()
    }
}