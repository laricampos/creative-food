package com.jellypump.creativefood.ui.screen.tag

import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import com.google.android.material.chip.Chip
import com.jellypump.creativefood.R
import com.jellypump.creativefood.ui.core.BaseDialogFragment
import kotlinx.android.synthetic.main.tag_add_fragment.*

class AddTagFragment : BaseDialogFragment() {

    override val layoutId = R.layout.tag_add_fragment

    private val tagViewModel by lazy { getViewModel(TagViewModel::class) }

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