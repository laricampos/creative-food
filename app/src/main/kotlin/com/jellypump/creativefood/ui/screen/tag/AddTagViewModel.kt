package com.jellypump.creativefood.ui.screen.tag

import androidx.annotation.ColorRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.jellypump.creativefood.db.entity.TagEntity
import com.jellypump.creativefood.extensions.runInBackground
import com.jellypump.creativefood.repo.TagRepo
import com.jellypump.creativefood.ui.core.BaseViewModel
import io.reactivex.Completable
import io.reactivex.processors.ReplayProcessor
import io.reactivex.rxkotlin.Flowables
import javax.inject.Inject

class AddTagViewModel @Inject constructor(
    private val tagRepo: TagRepo
) : BaseViewModel() {

    private val tagName: ReplayProcessor<String> = ReplayProcessor.create()
    private val tagColor: ReplayProcessor<Int> = ReplayProcessor.create()

    val isButtonEnabled: LiveData<Boolean>
        get() = LiveDataReactiveStreams.fromPublisher(
            Flowables.combineLatest(tagName, tagColor)
                .map { (name, color) ->
                    name.isNotEmpty() && color != 0
                })

    fun onColorSelected(@ColorRes color: Int) {
        tagColor.onNext(color)
    }

    fun onNameEntered(name: String) {
        tagName.onNext(name)
    }

    fun addTag(): Completable {
        val name = requireNotNull(tagName.value)
        val colour = requireNotNull(tagColor.value)
        return tagRepo.add(
            TagEntity(
                name = name, colour = colour
            )
        ).runInBackground()
    }
}