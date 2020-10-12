package com.jellypump.creativefood.ui.screen.ingredient

import androidx.lifecycle.LiveData
import com.jellypump.creativefood.extensions.runInBackground
import com.jellypump.creativefood.extensions.toLiveData
import com.jellypump.creativefood.model.Ingredient
import com.jellypump.creativefood.model.Tag
import com.jellypump.creativefood.repo.IngredientRepo
import com.jellypump.creativefood.repo.TagRepo
import com.jellypump.creativefood.ui.core.BaseViewModel
import javax.inject.Inject

class IngredientViewModel @Inject constructor(
    private val ingredientRepo: IngredientRepo,
    private val tagRepo: TagRepo
) : BaseViewModel() {

    val ingredients: LiveData<List<Ingredient>> by lazy {
        ingredientRepo.allIngredients
            .runInBackground()
            .toLiveData()
    }

    val tags: LiveData<List<Tag>> by lazy {
        tagRepo.allTags
            .runInBackground()
            .toLiveData()
    }
}