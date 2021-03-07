package dev.timatifey.stockaggregator.fragments.stocks

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class StocksItemDecoration(private val offset: Int): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.right = offset;
        outRect.left = offset;
        outRect.top = offset;
        outRect.bottom = offset;
    }

}
