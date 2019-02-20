package org.berendeev.coordinatorlayoutexamples.expandablebottomblock

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import kotlinx.android.parcel.Parcelize

class ExpandableBottomBlockBehavior(
    context: Context,
    attrs: AttributeSet?
) : CoordinatorLayout.Behavior<View>(context, attrs) {

    private var blockOffset = 0

    override fun onSaveInstanceState(parent: CoordinatorLayout, child: View): Parcelable {
        return SavedState(blockOffset)
    }

    override fun onRestoreInstanceState(parent: CoordinatorLayout, child: View, state: Parcelable) {
        blockOffset = (state as SavedState).blockOffset
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int): Boolean {
        val left = 0
        val top = parent.measuredHeight - child.measuredHeight + blockOffset
        val bottom = top + child.measuredHeight
        val right = left + child.measuredWidth
        child.layout(left, top, right, bottom)
        return true
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return true
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        val minOffsetDelta = -blockOffset
        val maxOffsetDelta = child.height - blockOffset
        val clampedOffsetDelta = if (dy < 0) {
            Math.min(-dy, maxOffsetDelta)
        } else {
            Math.max(-dy, minOffsetDelta)
        }
        blockOffset += clampedOffsetDelta
        child.offsetTopAndBottom(clampedOffsetDelta)
        consumed[1] = -clampedOffsetDelta
    }

    @Parcelize
    data class SavedState(val blockOffset: Int) : Parcelable
}
