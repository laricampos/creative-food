package com.jellypump.creativefood.ui.screen.ingredient.manage

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int, private val initialAndLastSpacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val isFirstCell = parent.getChildAdapterPosition(view) == 0
        val isLastCell = parent.getChildAdapterPosition(view) == (parent.adapter?.itemCount ?: 0) - 1

        outRect.top = if (isFirstCell) initialAndLastSpacing else 0
        outRect.bottom = if (isLastCell) initialAndLastSpacing else verticalSpaceHeight
    }
}