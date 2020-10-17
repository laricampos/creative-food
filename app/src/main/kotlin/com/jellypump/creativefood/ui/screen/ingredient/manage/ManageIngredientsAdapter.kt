package com.jellypump.creativefood.ui.screen.ingredient.manage

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.jellypump.creativefood.R
import com.jellypump.creativefood.model.Ingredient
import com.jellypump.creativefood.model.Tag
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.ingredient_list_item.view.*

class ManageIngredientsListAdapter(private val onItemClick: (Ingredient) -> Unit) : RecyclerView.Adapter<ManageIngredientsListAdapter.IngredientViewHolder>() {

    var ingredients: List<Ingredient> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder =
        IngredientViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.ingredient_list_item, parent, false),
            onItemClick
        )

    override fun getItemCount(): Int = ingredients.size

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(ingredients[position])
    }

    class IngredientViewHolder(override val containerView: View, private val onItemClick: (Ingredient) -> Unit) : RecyclerView.ViewHolder(containerView),
                                                                                                                  LayoutContainer {

        fun bind(ingredient: Ingredient) {
            containerView.apply {
                ingredient_name.text = ingredient.name
                ingredient_taste_score.text = ingredient.tasteScore.toString()
                ingredient_health_score.text = ingredient.healthScore.toString()
                addTags(ingredient.tags)
                setOnClickListener {
                    onItemClick(ingredient)
                }
            }
        }

        private fun addTags(tags: List<Tag>?) {
            tags?.forEach {
                val tagChip = Chip(containerView.context).apply {
                    text = it.name
                    chipBackgroundColor = ColorStateList.valueOf(it.colour)
                    isCheckable = true
                }
                containerView.ingredient_tag_container.addView(tagChip)
            }
        }
    }
}