package com.jellypump.creativefood.ui.screen.recipe

import com.jellypump.creativefood.extensions.runInBackground
import com.jellypump.creativefood.model.Ingredient
import com.jellypump.creativefood.repo.IngredientRepo
import com.jellypump.creativefood.ui.core.BaseViewModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val DEFAULT_MIN_INGREDIENT_NUMBER = 3
private const val DEFAULT_MAX_INGREDIENT_NUMBER = 5
const val DEFAULT_MIN_HEALTH_SCORE = 5
const val DEFAULT_MIN_TASTE_SCORE = 5

class GenerateRecipeViewModel @Inject constructor(ingredientRepo: IngredientRepo) : BaseViewModel() {

    var minHealthScore: Int = DEFAULT_MIN_HEALTH_SCORE

    var minTasteScore: Int = DEFAULT_MIN_TASTE_SCORE

    var maxNumberIngredients: Int = DEFAULT_MAX_INGREDIENT_NUMBER

    var minNumberIngredients: Int = DEFAULT_MIN_INGREDIENT_NUMBER

    private val ingredients: Single<List<Ingredient>> = ingredientRepo.allIngredients
        .runInBackground()
        .cache()

    val selectedIngredients: Single<List<Ingredient>> = ingredients
        .map { ingredients ->
            selectIngredients(ingredients.shuffled())
        }
        .map {
            if (it.isEmpty()) throw Throwable()
            else it
        }
        .timeout(30, TimeUnit.SECONDS, Schedulers.io())
        .runInBackground()

    private fun selectIngredients(ingredients: List<Ingredient>): List<Ingredient> {
        val numIngredients = (minNumberIngredients..maxNumberIngredients).shuffled()
        numIngredients.forEach { numberIngredients ->
            val combinedIngredients = combineIngredients(numberIngredients, emptyList(), ingredients)
            if (combinedIngredients.isNotEmpty()) {
                return combinedIngredients
            }
        }
        return emptyList()
    }

    private fun combineIngredients(numberIngredients: Int, selectedIngredients: List<Ingredient>, allIngredients: List<Ingredient>): List<Ingredient> {
        allIngredients.forEach { ingredient ->
            if (!selectedIngredients.contains(ingredient)) {
                if (selectedIngredients.size == numberIngredients) {
                    return if (isIngredientCombinationValid(selectedIngredients)) selectedIngredients else emptyList()
                } else {
                    val newList = selectedIngredients.toMutableList()
                    newList.add(ingredient)
                    val result = combineIngredients(numberIngredients, newList, allIngredients)
                    if (result.isNotEmpty()) return result
                }
            }
        }
        return emptyList()
    }

    private fun isIngredientCombinationValid(selectedIngredients: List<Ingredient>): Boolean {
        var totalHealthScore = 0
        var totalTasteScore = 0
        selectedIngredients.forEach {
            totalHealthScore += it.healthScore
            totalTasteScore += it.tasteScore
        }
        return totalHealthScore >= minHealthScore && totalTasteScore >= minTasteScore
    }
}