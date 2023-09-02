package com.criticalgnome.cleanarchitecturedemo.ui.adapter.dsl.internal

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

internal class RecyclerViewDslAdapter(
    private val viewHolderFactories: List<RecyclerViewDslViewHolder.Factory<*, *>>
): RecyclerView.Adapter<RecyclerViewDslViewHolder<*, *>>() {
    var model: List<Any> = listOf()
        set(value) {
            DiffUtil
                .calculateDiff(object : DiffUtil.Callback() {
                    override fun getOldListSize() = field.size
                    override fun getNewListSize() = value.size
                    override fun areItemsTheSame(old: Int, new: Int) = field[old] === value[old]
                    override fun areContentsTheSame(old: Int, new: Int) = field[old] == value[old]
                })
                .also { diffResult ->
                    field = value
                    diffResult.dispatchUpdatesTo(this)
                }
        }

    override fun getItemViewType(position: Int) =
        model[position].let {
            viewHolderFactories
                .withIndex()
                .first { (_, viewHolderFactory) -> viewHolderFactory.couldBeBoundTo(it) }
                .index
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewDslViewHolder<*, *> =
        viewHolderFactories[viewType].create(parent)

    override fun onBindViewHolder(holder: RecyclerViewDslViewHolder<*, *>, position: Int) =
        model[position].let(holder::bind)

    override fun getItemCount() = model.size
}
