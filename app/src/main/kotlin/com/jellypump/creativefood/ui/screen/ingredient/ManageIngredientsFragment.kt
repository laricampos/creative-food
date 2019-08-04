package com.jellypump.creativefood.ui.screen.ingredient

import androidx.lifecycle.Observer
import com.jellypump.creativefood.R
import com.jellypump.creativefood.ui.core.BaseFragment
import kotlinx.android.synthetic.main.ingredient_manage_fragment.*

class ManageIngredientsFragment : BaseFragment() {

    override val layoutId: Int = R.layout.ingredient_manage_fragment

    private val ingredientViewModel by lazy { getViewModel(IngredientViewModel::class) }

    override fun initUi() {
        ingredient_manage_add_button.setOnClickListener {
            navController.navigate(ManageIngredientsFragmentDirections.actionAddIngredient())
        }
    }

    override fun observeData() {
        ingredientViewModel.ingredients.observe(this, Observer {

        })
    }
}