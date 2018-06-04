package gusev.max.vkphotos.base.widgets.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import gusev.max.presentation.base.model.BaseModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

abstract class BaseAdapter<M : BaseModel, VIEW_HOLDER : BaseViewHolder<M>> :
        RecyclerView.Adapter<VIEW_HOLDER>(),
        BaseViewHolder.ModelClickListener<M> {

    protected val mainModelClickObservable: PublishSubject<Pair<M, View>> = PublishSubject.create()
    protected var modelList: MutableList<M> = mutableListOf()

    override fun getItemCount(): Int {
        return modelList.size
    }

    fun setData(models: MutableList<M>) {
        val oldSize = modelList.size
        modelList.clear()
        notifyItemRangeRemoved(0, oldSize)

        modelList.addAll(models)
        notifyItemRangeInserted(0, models.size)
    }

    override fun onMainModelClick(model: M, position: Int, view: View) {
        mainModelClickObservable.onNext(Pair(model, view))
    }

    fun getMainModelClickObservable(): Observable<Pair<M, View>> {
        return mainModelClickObservable
    }
}