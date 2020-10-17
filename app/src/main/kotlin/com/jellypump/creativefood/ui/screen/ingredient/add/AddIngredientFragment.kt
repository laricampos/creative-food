package com.jellypump.creativefood.ui.screen.ingredient.add

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.jellypump.creativefood.R
import com.jellypump.creativefood.extensions.onTextChanged
import com.jellypump.creativefood.model.Tag
import com.jellypump.creativefood.ui.core.BaseFragment
import kotlinx.android.synthetic.main.ingredient_add_fragment.*

private const val HEALTH_SCALE_SIZE = 5
private const val TASTE_SCALE_SIZE = 5

class AddIngredientFragment : BaseFragment() {

    override val layoutId: Int = R.layout.ingredient_add_fragment

    private val viewModel by lazy { getViewModel(AddIngredientViewModel::class) }

    private val args: AddIngredientFragmentArgs by navArgs()

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

        ingredient_add_name_input.onTextChanged(viewModel::onNameEntered)

        ingredient_add_health_scale_container.setOnCheckedChangeListener { chipGroup, id ->
            viewModel.onHeathIndexSelected(
                chipGroup.findViewById<Chip>(id)?.text?.toString()?.toInt()
                    ?: NOT_SELECTED
            )
        }
        ingredient_add_taste_scale_container.setOnCheckedChangeListener { chipGroup, id ->
            viewModel.onTasteIndexSelected(
                chipGroup.findViewById<Chip>(id)?.text?.toString()?.toInt()
                    ?: NOT_SELECTED
            )
        }

        ingredient_add_button.setOnClickListener {
            viewModel.addIngredient().simpleSubscribe {
                findNavController().navigateUp()
            }
        }

        viewModel.isButtonEnabled.observe {
            ingredient_add_button.isEnabled = it
        }
        populateIngredient()
    }

    private fun populateIngredient() {
        val ingredient = args.ingredient ?: return

        ingredient_add_name_input.setText(ingredient.name)
        val healthChipPosition = ingredient.healthScore + HEALTH_SCALE_SIZE
        ingredient_add_health_scale_container.apply {
            val position = getChildAt(healthChipPosition).id
            check(position)
        }

        val tasteChipPosition = ingredient.tasteScore + TASTE_SCALE_SIZE
        ingredient_add_taste_scale_container.apply {
            val position = getChildAt(tasteChipPosition).id
            check(position)
        }
    }

    override fun observeData() {
        viewModel.allTags.observe {
            addTags(it)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun addTags(tags: List<Tag>) {
        val selectedTags = args.ingredient?.tags ?: emptyList()

        ingredient_add_tag_container.removeAllViews()
        tags.forEach { tag ->
            val tagChip = Chip(requireContext()).apply {
                text = tag.name
                chipBackgroundColor = ColorStateList.valueOf(tag.colour)
                isCheckable = true
                setOnCheckedChangeListener { _, _ -> onTagSelected() }
            }

            if (selectedTags.any { it.id == tag.id }) {
                tagChip.isChecked = true
            }
            ingredient_add_tag_container.addView(tagChip)
        }
    }

    private fun onTagSelected() {
        val selectedTags = (0..ingredient_add_tag_container.childCount)
            .map { index ->
                ingredient_add_tag_container.getChildAt(index)
            }
            .filter { view ->
                (view as? Chip)?.isChecked ?: false
            }
            .map {
                (it as? Chip)?.text.toString()
            }
        viewModel.onTagsSelected(selectedTags)
    }

    private fun createChip(chipText: String) = Chip(context).apply {
        text = chipText
        chipCornerRadius = resources.getDimension(R.dimen.chip_corner_radius)
        isCheckedIconVisible = false
        isCheckable = true
    }
}