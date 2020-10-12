package com.jellypump.creativefood.ui.screen.ingredient.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.jellypump.creativefood.extensions.runInBackground
import com.jellypump.creativefood.model.Ingredient
import com.jellypump.creativefood.model.Tag
import com.jellypump.creativefood.repo.IngredientRepo
import com.jellypump.creativefood.repo.TagRepo
import com.jellypump.creativefood.ui.core.BaseViewModel
import io.reactivex.Completable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.rxkotlin.Flowables
import javax.inject.Inject

const val NOT_SELECTED = -200

class AddIngredientViewModel @Inject constructor(
    private val tagRepo: TagRepo,
    private val ingredientRepo: IngredientRepo) : BaseViewModel() {

    private val selectedTags: BehaviorProcessor<List<Tag>> = BehaviorProcessor.create()
    private val healthIndex: BehaviorProcessor<Int> = BehaviorProcessor.create()
    private val tasteIndex: BehaviorProcessor<Int> = BehaviorProcessor.create()
    private val ingredientName: BehaviorProcessor<String> = BehaviorProcessor.create()

    val allTags: LiveData<List<Tag>> by lazy {
        LiveDataReactiveStreams.fromPublisher(
            tagRepo.allTags.runInBackground()
        )
    }

    val isButtonEnabled: LiveData<Boolean>
        get() = LiveDataReactiveStreams.fromPublisher(
            Flowables.combineLatest(healthIndex, tasteIndex, ingredientName)
                .map { (healthIndex, tasteIndex, ingredientName) ->
                    ingredientName.isNotEmpty() && healthIndex != NOT_SELECTED && tasteIndex != NOT_SELECTED
                }
        )

    fun onTagsSelected(tagNames: List<String>) {
        val tags = tagNames.mapIndexed { _, tagName ->
            allTags.value?.find { tag ->
                tagName == tag.name
            } ?: throw IllegalStateException("Can't find tag")
        }
        selectedTags.onNext(tags)
    }

    fun onHeathIndexSelected(index: Int) {
        healthIndex.onNext(index)
    }

    fun onTasteIndexSelected(index: Int) {
        tasteIndex.onNext(index)
    }

    fun onNameEntered(name: String) {
        ingredientName.onNext(name)
    }

    fun addIngredient(): Completable {
        val name = requireNotNull(ingredientName.value)
        val healthIndex = requireNotNull(healthIndex.value)
        val tasteIndex = requireNotNull(tasteIndex.value)
        val selectedTags = selectedTags.value ?: emptyList()

        val ingredient = Ingredient(name, healthIndex, tasteIndex, selectedTags)
        return ingredientRepo.addIngredient(ingredient).runInBackground()
    }
}