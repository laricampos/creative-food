package com.jellypump.creativefood.ui.screen.tag

import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.jellypump.creativefood.R
import com.jellypump.creativefood.db.model.Tag
import com.jellypump.creativefood.extensions.runInBackground
import com.jellypump.creativefood.repo.TagRepo
import com.jellypump.creativefood.ui.core.BaseDialogFragment
import com.jellypump.creativefood.ui.core.BaseViewModel
import io.reactivex.Completable
import kotlinx.android.synthetic.main.tag_add_fragment.*
import javax.inject.Inject

class AddTagViewModel @Inject constructor(
    private val tagRepo: TagRepo
) : BaseViewModel() {

    fun addTag(tag: Tag): Completable = tagRepo.add(tag).runInBackground()
}

class AddTagFragment : BaseDialogFragment() {

    override val layoutId = R.layout.tag_add_fragment

    private val viewModel by lazy { getViewModel(AddTagViewModel::class) }

    private val tagColours = listOf(
        R.color.green_sushi,
        R.color.pink_cranberry,
        R.color.yellow_tulip,
        R.color.blue_eastern,
        R.color.orange_fire,
        R.color.red_flame,
        R.color.purple_wisteria,
        R.color.blue_curious
    )

    override fun initUi() {
        tagColours.forEach {
            tag_color_container.addView(createChip(it))
        }

        tad_add_button.setOnClickListener {
            onAddClick()
        }
    }

    private fun onAddClick() {
        val checkedChipColor = view?.findViewById<Chip>(tag_color_container.checkedChipId)?.chipBackgroundColor
            ?: throw IllegalStateException("A color must be selected")
        val tag = Tag(tag_name_input.text.toString(), checkedChipColor.defaultColor)

        viewModel.addTag(tag).simpleSubscribe {
            findNavController().navigateUp()
        }
    }

    private fun createChip(@ColorRes chipColour: Int) = Chip(context).apply {
        val chipSide = resources.getDimension(R.dimen.chip_side).toInt()
        layoutParams = ViewGroup.LayoutParams(chipSide, chipSide)
        chipCornerRadius = resources.getDimension(R.dimen.chip_corner_radius)
        isCheckedIconVisible = true
        isCheckable = true
        chipBackgroundColor = getColorStateList(context, chipColour)
    }
}