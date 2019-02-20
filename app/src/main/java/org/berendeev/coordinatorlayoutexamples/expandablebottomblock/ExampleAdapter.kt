package org.berendeev.coordinatorlayoutexamples.expandablebottomblock

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_example_row.*
import org.berendeev.coordinatorlayoutexamples.R

private const val ITEMS_COUNT = 100

class ExampleAdapter : RecyclerView.Adapter<ExampleAdapter.ExampleHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleHolder {
        return ExampleHolder(parent)
    }

    override fun getItemCount(): Int = ITEMS_COUNT

    override fun onBindViewHolder(holder: ExampleHolder, position: Int) {
        holder.setNumber(position + 1)
        holder.setLineMarker(position % 2 == 0)
    }


    class ExampleHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_example_row)),
        LayoutContainer {
        override val containerView: View?
            get() = itemView

        fun setNumber(number: Int) {
            numberTextView.text = number.toString()
        }

        fun setLineMarker(isMarked: Boolean) {
            if (isMarked) {
                numberTextView.setBackgroundResource(R.color.lightOrange)
            } else {
                numberTextView.setBackgroundResource(0)
            }
        }

        companion object {
            private fun ViewGroup.inflate(@LayoutRes layoutId: Int): View {
                return LayoutInflater.from(this.context).inflate(layoutId, this, false)
            }
        }
    }
}
