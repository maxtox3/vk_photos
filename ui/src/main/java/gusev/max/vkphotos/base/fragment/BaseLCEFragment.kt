package gusev.max.vkphotos.base.fragment

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gusev.max.presentation.base.BaseIntent
import gusev.max.presentation.base.BaseView
import gusev.max.presentation.base.BaseViewModel
import gusev.max.presentation.base.BaseViewState
import gusev.max.vkphotos.base.activity.BaseActivityCallback
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseLCEFragment<
        INTENT : BaseIntent,
        VIEW_STATE : BaseViewState,
        VIEW_MODEL : BaseViewModel<INTENT, VIEW_STATE>,
        ACTIVITY_CALLBACK : BaseActivityCallback> : BaseFragmentWithCallback<ACTIVITY_CALLBACK>(),
        BaseView<INTENT, VIEW_STATE> {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: VIEW_MODEL
    protected val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = getViewModel()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    abstract fun getViewModel(): VIEW_MODEL

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeForUpdatesFromViewModel()
        processIntents()
    }

    protected open fun processIntents() {
        viewModel.processIntents(intents())
    }

    protected open fun subscribeForUpdatesFromViewModel() {
        compositeDisposable.add(viewModel.states().subscribe({ render(it) }))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    abstract fun getLoadingView(): View

    protected fun setupScreenForError(throwable: Throwable) {
        getLoadingView().visibility = View.GONE
        handleThrowable(throwable)
    }

    abstract fun handleThrowable(throwable: Throwable)

    protected fun setupScreenForLoading() {
        getLoadingView().visibility = View.VISIBLE
    }

    abstract fun showError(message: String)

}