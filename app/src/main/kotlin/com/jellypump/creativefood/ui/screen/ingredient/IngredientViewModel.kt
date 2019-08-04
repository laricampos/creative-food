package com.jellypump.creativefood.ui.screen.ingredient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jellypump.creativefood.db.model.Ingredient
import com.jellypump.creativefood.extensions.runInBackground
import com.jellypump.creativefood.repo.IngredientRepo
import com.jellypump.creativefood.ui.core.BaseViewModel
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class IngredientViewModel @Inject constructor(
    private val ingredientRepo: IngredientRepo
) : BaseViewModel() {

    private val _ingredients: MutableLiveData<List<Ingredient>> = MutableLiveData()
    val ingredients: LiveData<List<Ingredient>>
        get() {
            ingredientRepo.allIngredients
                .runInBackground()
                .subscribe { ingredients ->
                    _ingredients.value = ingredients
                }.addTo(compositeDisposable)
            return _ingredients
        }
}