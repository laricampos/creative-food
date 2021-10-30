package com.jellypump.creativefood.ui.screen.home

import android.widget.Toast
import com.jellypump.creativefood.R
import com.jellypump.creativefood.ui.core.BaseFragment
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : BaseFragment() {

    override val layoutId: Int = R.layout.home_fragment

    private val viewModel by lazy { getViewModel(HomeViewModel::class) }

    override fun initUi() {
        home_add_ingredient_button.setOnClickListener {
            navController.navigate(HomeFragmentDirections.actionManageIngredient())
        }

        home_generate_recipe_button.setOnClickListener {
            navController.navigate(HomeFragmentDirections.actionGenerateRecipe())
        }

        home_import_ingredients_button.setOnClickListener {
            Toast.makeText(requireContext(), "Loading ingredients", Toast.LENGTH_LONG).show()
            viewModel.importIngredients().simpleSubscribe {
                Toast.makeText(requireContext(), "Loading completed", Toast.LENGTH_LONG).show()
            }
        }
    }
}