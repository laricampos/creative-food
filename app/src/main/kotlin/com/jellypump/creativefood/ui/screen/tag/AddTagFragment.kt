package com.jellypump.creativefood.ui.screen.tag

import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.jellypump.creativefood.R
import com.jellypump.creativefood.db.model.Tag
import com.jellypump.creativefood.extensions.onTextChanged
import com.jellypump.creativefood.extensions.runInBackground
import com.jellypump.creativefood.repo.TagRepo
import com.jellypump.creativefood.ui.core.BaseDialogFragment
import com.jellypump.creativefood.ui.core.BaseViewModel
import io.reactivex.Completable
import io.reactivex.processors.ReplayProcessor
import io.reactivex.rxkotlin.Flowables
import kotlinx.android.synthetic.main.tag_add_fragment.*
import javax.inject.Inject

class AddTagViewModel @Inject constructor(
    private val tagRepo: TagRepo
) : BaseViewModel() {

    private val tagName: ReplayProcessor<String> = ReplayProcessor.create()
    private val tagColor: ReplayProcessor<Int> = ReplayProcessor.create()

    val isButtonEnabled: LiveData<Boolean>
        get() = LiveDataReactiveStreams.fromPublisher(Flowables.combineLatest(tagName, tagColor)
            .map { (name, color) ->
                name.isNotEmpty() && color != 0
            })

    fun onColorSelected(@DrawableRes color: Int) {
        tagColor.onNext(color)
    }

    fun onNameEntered(name: String) {
        tagName.onNext(name)
    }

    fun addTag(): Completable = tagRepo.add(
        Tag(tagName.value, tagColor.value)
    ).runInBackground()
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

        tag_name_input.onTextChanged(viewModel::onNameEntered)
        tag_color_container.setOnCheckedChangeListener { chipGroup, id ->
            val chipColor = chipGroup.findViewById<Chip>(id)?.chipBackgroundColor?.defaultColor ?: 0
            viewModel.onColorSelected(chipColor)
        }

        tad_add_button.setOnClickListener {
            onAddClick()
        }

        viewModel.isButtonEnabled.observe(this, Observer {
            tad_add_button.isEnabled = it
        })
    }

    private fun onAddClick() {
        viewModel.addTag().simpleSubscribe {
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