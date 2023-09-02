package com.criticalgnome.cleanarchitecturedemo.ui.adapter.dsl.internal

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KClass
import kotlin.reflect.cast

internal class RecyclerViewDslViewHolder<VIEW, MODEL: Any>(
    private val view: VIEW,
    private val modelClass: KClass<MODEL>,
    private val bind: VIEW.(MODEL) -> Unit,
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    fun bind(model: Any) {
        if (couldBeBoundTo(model)) view.bind(modelClass.cast(model))
        else throw IllegalArgumentException("Couldn't bind a model ($model) to a view ($view).")
    }

    private fun couldBeBoundTo(model: Any): Boolean = modelClass.isInstance(model)

    class Factory<VIEW, MODEL: Any>(
        private val viewFactory: (ViewGroup) -> Pair<VIEW, View>,
        private val modelClass: KClass<MODEL>,
        private val bind: VIEW.(MODEL) -> Unit
    ) {
        fun create(parent: ViewGroup): RecyclerViewDslViewHolder<VIEW, MODEL> {
            val(view, itemView) = viewFactory.invoke(parent)
            return RecyclerViewDslViewHolder(
                view = view,
                modelClass = modelClass,
                bind = bind,
                itemView = itemView
            )
        }

        fun couldBeBoundTo(model: Any): Boolean = modelClass.isInstance(model)
    }
}
