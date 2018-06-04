package gusev.max.vkphotos.friends

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import dagger.android.support.AndroidSupportInjection
import gusev.max.domain.error.AuthError
import gusev.max.presentation.fullphoto.GetPhotoIntent
import gusev.max.presentation.fullphoto.GetPhotoView
import gusev.max.presentation.fullphoto.GetPhotoViewModel
import gusev.max.presentation.fullphoto.GetPhotoViewState
import gusev.max.presentation.main.browsefriends.BrowseFriendsIntent
import gusev.max.presentation.main.browsefriends.BrowseFriendsViewModel
import gusev.max.presentation.main.browsefriends.BrowseFriendsViewState
import gusev.max.presentation.model.UserModel
import gusev.max.vkontakte_photos.R
import gusev.max.vkphotos.base.fragment.BaseLCEFragment
import gusev.max.vkphotos.main.MainActivityCallback
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_friends_list.*
import javax.inject.Inject

class UsersFragment :
        BaseLCEFragment<BrowseFriendsIntent, BrowseFriendsViewState, BrowseFriendsViewModel, MainActivityCallback>(),
        GetPhotoView {

    private val tryAgainPublisher: PublishSubject<BrowseFriendsIntent.TryAgain> = PublishSubject.create()

    @Inject
    lateinit var friendsAdapter: FriendsAdapter

    @Inject
    lateinit var photoViewModel: GetPhotoViewModel

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun setupWidgets() {
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = friendsAdapter
    }

    /**
     * BrowseFriends methods
     */

    override fun intents(): Observable<BrowseFriendsIntent> {
        return Observable.merge(
                Observable.just(BrowseFriendsIntent.InitialIntent),
                Observable.just(BrowseFriendsIntent.BrowseFriends),
                tryAgainPublisher
        )
    }

    override fun render(state: BrowseFriendsViewState) {
        when {
            state.inProgress -> setupScreenForLoading()
            state is BrowseFriendsViewState.Failed -> setupScreenForError(state.throwable!!)
            state is BrowseFriendsViewState.LoadFriendsSuccess -> setupForSuccess(state.users)
        }
    }

    private fun setupForSuccess(friends: List<UserModel>?) {
        getLoadingView().visibility = View.GONE
        if (friendsAdapter.itemCount == 0) {
            friendsAdapter.setData(friends?.toMutableList() ?: arrayListOf())
        } else if (friends?.size == 0) {
            Snackbar.make(constraint, R.string.empty_error, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun showError(message: String) {
        val tryAgain = Snackbar.make(
                constraint,
                R.string.error_general,
                Snackbar.LENGTH_INDEFINITE
        )
        tryAgain.setAction(
                R.string.try_again,
                { v: View -> tryAgainPublisher.onNext(BrowseFriendsIntent.TryAgain) }
        )
        tryAgain.show()
    }

    internal fun updateData() {
        tryAgainPublisher.onNext(BrowseFriendsIntent.TryAgain)
    }

    override fun getViewModel(): BrowseFriendsViewModel {
        photoViewModel = ViewModelProviders.of(
                this,
                viewModelFactory
        ).get(GetPhotoViewModel::class.java)
        return ViewModelProviders.of(this, viewModelFactory).get(BrowseFriendsViewModel::class.java)
    }

    /**
     * GetPhotoView methods implementation
     */

    override fun subscribeForUpdatesFromViewModel() {
        compositeDisposable.add(photoViewModel.states().subscribe({ render(it) }))
        super.subscribeForUpdatesFromViewModel()
    }

    override fun processIntents() {
        photoViewModel.processIntents(photoIntents())
        super.processIntents()
    }

    override fun photoIntents(): Observable<GetPhotoIntent> {
        return Observable.merge(
                Observable.just(GetPhotoIntent.InitialIntent),
                friendsAdapter.getPhotoLoadObservable().map { GetPhotoIntent.LoadFullPhoto(it) })
    }

    override fun render(state: GetPhotoViewState) {
        when (state) {
            is GetPhotoViewState.Failed -> friendsAdapter.updateModel(state.userModel)
            is GetPhotoViewState.LoadPhotoSuccess -> friendsAdapter.updateModel(state.userModel)
        }
    }

    /**
     * BaseFragment methods implementation methods
     */

    override fun handleThrowable(throwable: Throwable) {
        if (throwable is AuthError) {
            activityCallback.navigateToAuth()
        } else {
            showError(throwable.localizedMessage)
        }
    }

    override fun initActivityCallback(context: Context?) {
        activityCallback = context as MainActivityCallback
    }

    override fun getContainerId(): Int {
        return R.layout.fragment_friends_list
    }

    override fun getLoadingView(): View {
        return progress
    }
}