package com.jellypump.creativefood.ui.screen.ingredient.manage

import androidx.recyclerview.widget.LinearLayoutManager
import com.jellypump.creativefood.R
import com.jellypump.creativefood.model.Ingredient
import com.jellypump.creativefood.ui.core.BaseFragment
import com.jellypump.creativefood.ui.screen.ingredient.IngredientViewModel
import kotlinx.android.synthetic.main.ingredient_manage_fragment.*

class ManageIngredientsFragment : BaseFragment() {

    override val layoutId: Int = R.layout.ingredient_manage_fragment

    private val viewModel by lazy { getViewModel(IngredientViewModel::class) }

    override fun initUi() {
        ingredient_manage_add_button.setOnClickListener {
            navController.navigate(ManageIngredientsFragmentDirections.actionAddIngredient(null))
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        ingredient_manage_list.layoutManager = LinearLayoutManager(requireContext())
        ingredient_manage_list.adapter = ManageIngredientsListAdapter(::onIngredientSelected)
        val verticalSpacing = resources.getDimensionPixelSize(R.dimen.small_margin)
        val topBottomSpacing = resources.getDimensionPixelSize(R.dimen.medium_margin)
        ingredient_manage_list.addItemDecoration(VerticalSpaceItemDecoration(verticalSpacing, topBottomSpacing))
    }

    private fun onIngredientSelected(ingredient: Ingredient) {
        navController.navigate(ManageIngredientsFragmentDirections.actionAddIngredient(ingredient))
    }

    override fun observeData() {
        viewModel.ingredients.observe {
            (ingredient_manage_list.adapter as ManageIngredientsListAdapter).ingredients = it
        }
    }
}