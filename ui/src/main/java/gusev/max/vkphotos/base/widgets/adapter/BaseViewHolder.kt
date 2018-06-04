package gusev.max.vkphotos.base.widgets.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import gusev.max.presentation.base.model.BaseModel

abstract class BaseViewHolder<M : BaseModel> constructor(
    itemView: View,
    private val clickListener: ModelClickListener<M>
) : RecyclerView.ViewHolder(itemView) {

    interface ModelClickListener<M : BaseModel> {
        fun onMainModelClick(model: M, position: Int, view: View)
    }

    open fun bind(model: M) {
        itemView.setOnClickListener({ v ->
            clickListener.onMainModelClick(
                    model,
                    this.adapterPosition,
                    getView()
            )
        })
    }

    abstract fun getView(): View
}