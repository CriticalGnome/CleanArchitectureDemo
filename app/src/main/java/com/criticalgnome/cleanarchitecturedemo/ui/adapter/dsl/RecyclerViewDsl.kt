package com.criticalgnome.cleanarchitecturedemo.ui.adapter.dsl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.criticalgnome.cleanarchitecturedemo.ui.adapter.dsl.internal.RecyclerViewDslAdapter
import com.criticalgnome.cleanarchitecturedemo.ui.adapter.dsl.internal.RecyclerViewDslViewHolder
import kotlin.reflect.KClass

var RecyclerView.Adapter<out RecyclerView.ViewHolder>.model: List<Any>
    get() = if (this is RecyclerViewDslAdapter) this.model else throw IllegalStateException()
    set(value) { if (this is RecyclerViewDslAdapter) this.model = value else throw IllegalStateException() }

fun recyclerViewAdapter(
    block: RecyclerViewAdapterClosure.() -> Unit
): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
    return RecyclerViewAdapterClosure().also(block).recyclerViewAdapter
}

@DslMarker
annotation class RecyclerViewDslMarker

@RecyclerViewDslMarker
class RecyclerViewAdapterClosure internal constructor() {

    private val viewHolderFactories = mutableMapOf<KClass<*>, RecyclerViewDslViewHolder.Factory<*, *>>()
    val define: DefineClosure
        get() = DefineClosure(this)

    fun define(): DefineClosure = define

    internal val recyclerViewAdapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>
        get() = RecyclerViewDslAdapter(viewHolderFactories.values.toList())

    internal fun <VIEW, MODEL: Any> defineViewHolderFactoryFor(
        viewFactory: (ViewGroup) -> Pair<VIEW, View>,
        modelClass: KClass<MODEL>,
        bind: VIEW.(MODEL) -> Unit
    ) {
        if (viewHolderFactories.containsKey(modelClass)) {
            throw IllegalStateException("A view holder factory for model (${modelClass}) has been already defined!")
        } else {
            viewHolderFactories[modelClass] = RecyclerViewDslViewHolder.Factory(
                viewFactory = viewFactory,
                modelClass = modelClass,
                bind = bind
            )
        }
    }
}

@RecyclerViewDslMarker
class DefineClosure internal constructor(
    private val recyclerViewAdapterClosure: RecyclerViewAdapterClosure
) {

    inline infix fun <reified VIEW: View> viewHolderOf(
        noinline block: Function1<ViewGroup, VIEW>
    ): ViewHolderOfClosure<VIEW> {
        return viewHolderOf(VIEW::class) { parent ->
            val view = block.invoke(parent)
            view to view
        }
    }

    inline infix fun <reified VIEW: ViewBinding> viewHolderOf(
        crossinline block: Function3<LayoutInflater, ViewGroup?, Boolean, VIEW>
    ): ViewHolderOfClosure<VIEW> {
        return viewHolderOf(VIEW::class) { parent ->
            val viewBinding = block.invoke(LayoutInflater.from(parent.context), parent, false)
            viewBinding to viewBinding.root
        }
    }

    fun <VIEW: Any> viewHolderOf(
        viewClass: KClass<VIEW>,
        viewFactory: (ViewGroup) -> Pair<VIEW, View>
    ): ViewHolderOfClosure<VIEW> {
        return ViewHolderOfClosure(this, viewFactory)
    }

    internal fun <VIEW, MODEL: Any> defineViewHolderFactoryFor(
        viewFactory: (ViewGroup) -> Pair<VIEW, View>,
        modelClass: KClass<MODEL>,
        bind: VIEW.(MODEL) -> Unit
    ) {
        recyclerViewAdapterClosure.defineViewHolderFactoryFor(
            viewFactory = viewFactory,
            modelClass = modelClass,
            bind = bind
        )
    }
}

@RecyclerViewDslMarker
class ViewHolderOfClosure<VIEW: Any> internal constructor(
    private val defineClosure: DefineClosure,
    private val viewFactory: (ViewGroup) -> Pair<VIEW, View>
) {

    infix fun couldBe(
        block: CouldBeClosure<VIEW>.() -> Unit
    ) {
        CouldBeClosure(this).also(block)
    }

    inline fun <reified MODEL: Any> couldBeBoundTo() {
        couldBeBoundTo(MODEL::class) {}
    }

    inline infix fun <reified MODEL: Any> couldBeBoundTo(
        noinline block: VIEW.(MODEL) -> Unit
    ) {
        couldBeBoundTo(MODEL::class, block)
    }

    fun <MODEL: Any> couldBeBoundTo(
        modelClass: KClass<MODEL>,
        block: VIEW.(MODEL) -> Unit
    ) {
        defineViewHolderFactoryFor(
            modelClass = modelClass,
            bind = block
        )
    }

    internal fun <MODEL: Any> defineViewHolderFactoryFor(
        modelClass: KClass<MODEL>,
        bind: VIEW.(MODEL) -> Unit
    ) {
        defineClosure.defineViewHolderFactoryFor(
            viewFactory = viewFactory,
            modelClass = modelClass,
            bind = bind
        )
    }
}

@RecyclerViewDslMarker
class CouldBeClosure<VIEW: Any> internal constructor(
    private val viewHolderOfClosure: ViewHolderOfClosure<VIEW>
) {

    inline fun <reified MODEL: Any> boundTo() = boundTo(MODEL::class) {}

    inline infix fun <reified MODEL: Any> boundTo(
        noinline block: VIEW.(MODEL) -> Unit
    ) = boundTo(modelClass = MODEL::class, block = block)

    fun<MODEL: Any> boundTo(
        modelClass: KClass<MODEL>,
        block: VIEW.(MODEL) -> Unit
    ) = viewHolderOfClosure.defineViewHolderFactoryFor(modelClass = modelClass, bind = block)
}
