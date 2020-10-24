package com.jellypump.creativefood.ui.screen.home

import com.jellypump.creativefood.R
import com.jellypump.creativefood.ui.core.BaseFragment
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : BaseFragment() {

    override val layoutId: Int = R.layout.home_fragment

    override fun initUi() {
        home_add_ingredient_button.setOnClickListener {
            navController.navigate(HomeFragmentDirections.actionManageIngredient())
        }

        home_generate_recipe_button.setOnClickListener {
            navController.navigate(HomeFragmentDirections.actionGenerateRecipe())
        }
    }
}