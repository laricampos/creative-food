package com.jellypump.creativefood.ui.screen.ingredient

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.material.chip.Chip
import com.jellypump.creativefood.R
import com.jellypump.creativefood.db.model.Tag
import com.jellypump.creativefood.extensions.runInBackground
import com.jellypump.creativefood.repo.TagRepo
import com.jellypump.creativefood.ui.core.BaseFragment
import com.jellypump.creativefood.ui.core.BaseViewModel
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.ingredient_add_fragment.*
import javax.inject.Inject

private const val HEALTH_SCALE_SIZE = 5
private const val TASTE_SCALE_SIZE = 5

class AddIngredientViewModel @Inject constructor(private val tagRepo: TagRepo) : BaseViewModel() {

    private val _tags: MutableLiveData<List<Tag>> = MutableLiveData()
    val tags: LiveData<List<Tag>> by lazy {
        tagRepo.allTags
            .runInBackground()
            .subscribe { tags ->
                _tags.value = tags
            }.addTo(compositeDisposable)
        _tags
    }
}

class AddIngredientFragment : BaseFragment() {

    override val layoutId: Int = R.layout.ingredient_add_fragment

    private val viewModel by lazy { getViewModel(AddIngredientViewModel::class) }

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

    override fun observeData() {
        viewModel.tags.observe(this, Observer {
            addTags(it)
        })
    }

    @SuppressLint("ResourceAsColor")
    private fun addTags(tags: List<Tag>) {
        tags.forEach {
            val tagChip = Chip(requireContext()).apply {
                text = it.name
                chipBackgroundColor = ColorStateList.valueOf(it.colour)
            }
            ingredient_add_tag_container.addView(tagChip)
        }
    }

    private fun createChip(chipText: String) = Chip(context).apply {
        text = chipText
        chipCornerRadius = resources.getDimension(R.dimen.chip_corner_radius)
        isCheckedIconVisible = false
        isCheckable = true
    }
}