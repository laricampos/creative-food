package com.jellypump.creativefood.ui.screen.home

import android.content.Context
import com.jellypump.creativefood.extensions.runInBackground
import com.jellypump.creativefood.repo.IngredientRepo
import com.jellypump.creativefood.ui.core.BaseViewModel
import io.reactivex.Completable
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val applicationContext: Context,
    private val ingredientRepo: IngredientRepo
) : BaseViewModel() {

    fun importIngredients(): Completable = ingredientRepo.loadDefaultIngredients(applicationContext).runInBackground()
}