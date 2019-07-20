package com.jellypump.creativefood.ui.screen.ingredient

import com.google.android.material.chip.Chip
import com.jellypump.creativefood.R
import com.jellypump.creativefood.ui.core.BaseFragment
import kotlinx.android.synthetic.main.ingredient_add_fragment.*

private const val HEALTH_SCALE_SIZE = 5
private const val TASTE_SCALE_SIZE = 5

class AddIngredientFragment : BaseFragment() {

    override val layoutId: Int = R.layout.ingredient_add_fragment

    override fun initUi() {
        for (i in -HEALTH_SCALE_SIZE..HEALTH_SCALE_SIZE + 1) {
            ingredient_add_health_scale_container.addView(createChip(i.toString()))
        }
        for (i in -TASTE_SCALE_SIZE..TASTE_SCALE_SIZE + 1) {
            ingredient_add_taste_scale_container.addView(createChip(i.toString()))
        }
        ingredient_add_tag_add_button.setOnClickListener {
            navController.navigate(AddIngredientFragmentDirections.actionAddTag())
        }
    }

    private fun createChip(chipText: String) = Chip(context).apply {
        text = chipText
        chipCornerRadius = resources.getDimension(R.dimen.chip_corner_radius)
        isCheckedIconVisible = false
        isCheckable = true
    }
}