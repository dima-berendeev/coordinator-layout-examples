package org.berendeev.coordinatorlayoutexamples.expandablebottomblock

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewBehavior(context: Context?, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<RecyclerView>(context, attrs) {
    override fun layoutDependsOn(parent: CoordinatorLayout, child: RecyclerView, dependency: View): Boolean {
        return isExpandableBlock(dependency)
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: RecyclerView, layoutDirection: Int): Boolean {
        val expandableButton = parent.children
            .firstOrNull { children ->
                isExpandableBlock(children)
            }
        return if (expandableButton != null) {
            val left = 0
            val expandableButtonVisibleHeight = parent.measuredHeight - expandableButton.top
            val top = 0 - expandableButtonVisibleHeight
            val bottom = top + child.measuredHeight
            val right = left + child.measuredWidth
            child.layout(left, top, right, bottom)
            true
        } else {
            super.onLayoutChild(parent, child, layoutDirection)
        }
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: RecyclerView, dependency: View): Boolean {
        val delta = dependency.top - child.bottom
        return if (delta != 0) {
            child.offsetTopAndBottom(delta)
            true
        } else {
            false
        }
    }

    private fun isExpandableBlock(children: View): Boolean {
        return (children.layoutParams as CoordinatorLayout.LayoutParams).behavior is ExpandableBottomBlockBehavior
    }
}
