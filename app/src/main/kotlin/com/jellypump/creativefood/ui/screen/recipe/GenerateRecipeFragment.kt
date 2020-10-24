package com.jellypump.creativefood.ui.screen.recipe

import android.app.AlertDialog
import android.widget.NumberPicker
import android.widget.Toast
import com.google.android.material.chip.Chip
import com.jellypump.creativefood.R
import com.jellypump.creativefood.ui.core.BaseFragment
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.recipe_generate_fragment.*
import timber.log.Timber

private const val HEALTH_SCALE_SIZE = 5
private const val TASTE_SCALE_SIZE = 5

class GenerateRecipeFragment : BaseFragment() {

    override val layoutId: Int = R.layout.recipe_generate_fragment

    private val viewModel by lazy { getViewModel(GenerateRecipeViewModel::class) }

    override fun initUi() {
        recipe_generate_button.setOnClickListener {
            onGenerateButtonClick()
        }

        recipe_generate_max_number_ingredients_text.setOnClickListener {
            showNumberPickerDialog { selectedValue ->
                viewModel.maxNumberIngredients = selectedValue
                recipe_generate_max_number_ingredients_text.text = selectedValue.toString()
            }
        }
        recipe_generate_min_number_ingredients_text.setOnClickListener {
            showNumberPickerDialog { selectedValue ->
                viewModel.minNumberIngredients = selectedValue
                recipe_generate_min_number_ingredients_text.text = selectedValue.toString()
            }
        }
        setupScoreScale()
    }

    override fun observeData() {

    }

    private fun setupScoreScale() {
        for (i in 0..HEALTH_SCALE_SIZE * 2) {
            val chip = createChip(i.toString()) {
                viewModel.minHealthScore = it
            }
            recipe_generate_min_health_score_chip_group.addView(chip)
        }
        (recipe_generate_min_health_score_chip_group.getChildAt(DEFAULT_MIN_HEALTH_SCORE) as Chip).isChecked = true

        for (i in -0..TASTE_SCALE_SIZE * 2) {
            val chip = createChip(i.toString()) {
                viewModel.minTasteScore = it
            }
            recipe_generate_min_taste_score_chip_group.addView(chip)
        }
        (recipe_generate_min_taste_score_chip_group.getChildAt(DEFAULT_MIN_TASTE_SCORE) as Chip).isChecked = true
    }

    private fun createChip(chipText: String, onScoreSelected: (Int) -> Unit) = Chip(context).apply {
        text = chipText
        chipCornerRadius = resources.getDimension(R.dimen.chip_corner_radius)
        isCheckedIconVisible = false
        isCheckable = true
        setOnClickListener {
            val chip = (it as Chip)
            chip.isChecked = true
            onScoreSelected(chip.text.toString().toInt())
        }
    }

    private fun onGenerateButtonClick() {
        viewModel.selectedIngredients.subscribeBy(
            onError = {
                Toast.makeText(requireContext(), R.string.recipe_generate_error, Toast.LENGTH_LONG).show()
                Timber.e(it)
            },
            onSuccess = { ingredients ->
                var result = ""
                ingredients.forEach {
                    result += "${it.name}\n"
                }
                recipe_generate_result_text.text = result
            }).addTo(compositeDisposable)
    }

    private fun showNumberPickerDialog(onValueSelected: (Int) -> Unit) {
        val numberPicker = NumberPicker(requireContext()).apply {
            maxValue = 360
            minValue = 0
        }

        return AlertDialog.Builder(activity).apply {
            setTitle(R.string.recipe_select_max_num_ingredients_title)
            setMessage(R.string.recipe_select_max_num_ingredients_text)
            setPositiveButton(android.R.string.ok) { _, _ ->
                onValueSelected(numberPicker.value)
            }
            setNegativeButton(android.R.string.cancel) { _, _ -> }
            setView(numberPicker)
        }.create().show()
    }
}