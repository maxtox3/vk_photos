package gusev.max.vkphotos.main

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import dagger.android.AndroidInjection
import gusev.max.presentation.auth.AuthIntent
import gusev.max.presentation.auth.AuthViewModel
import gusev.max.presentation.auth.AuthViewState
import gusev.max.presentation.base.BaseView
import gusev.max.vkontakte_photos.R
import gusev.max.vkphotos.auth.AuthDialogFragment
import gusev.max.vkphotos.auth.AuthResultListener
import gusev.max.vkphotos.base.Constants
import gusev.max.vkphotos.base.activity.BaseActivityFragmentContainer
import gusev.max.vkphotos.friends.UsersFragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MainActivity : BaseActivityFragmentContainer(), MainActivityCallback,
        BaseView<AuthIntent, AuthViewState>, AuthResultListener {

    private val authPublisher: PublishSubject<AuthIntent.SaveAuthDataIntent> = PublishSubject.create()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: AuthViewModel

    private var dialog: AuthDialogFragment = AuthDialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            navigateToUsers()
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(AuthViewModel::class.java)

        addDisposable(viewModel.states().subscribe({ render(it) }))
        viewModel.processIntents(intents())
    }

    override fun intents(): Observable<AuthIntent> {
        return Observable.merge(initialIntent(), authPublisher)
    }

    private fun initialIntent(): Observable<AuthIntent.InitialIntent> {
        return Observable.just(AuthIntent.InitialIntent)
    }

    override fun render(state: AuthViewState) {
        when {
            state.inProgress -> {
                setupScreenForLoading()
            }
            state === AuthViewState.Failed -> {
                setupScreenForError()
            }
            state === AuthViewState.AuthSuccess -> {
                setupScreenForSuccess()
            }
        }
    }

    private fun setupScreenForSuccess() {
        if (dialog.isVisible) {
            dialog.dismiss()
        }
        updateUsersFragment()
    }

    private fun setupScreenForError() {
    }

    private fun setupScreenForLoading() {
    }

    private fun updateUsersFragment() {
        (supportFragmentManager.findFragmentByTag(Constants.USERS_FRAGMENT) as UsersFragment).updateData()
    }

    override fun onAuthSuccess(url: String) {
        authPublisher.onNext(AuthIntent.SaveAuthDataIntent(url))
    }

    override fun navigateToUsers() {
        navigateToFragment(Constants.USERS_FRAGMENT, null, false)
    }

    override fun navigateToAuth() {
        showAuthDialog()
    }

    private fun showAuthDialog() {
        dialog.setAuthResultListener(this)
        dialog.show(this.supportFragmentManager, Constants.AUTH_FRAGMENT)
    }

    override fun createFragment(tag: String, args: Bundle?): Fragment {
        when (tag) {
            Constants.USERS_FRAGMENT -> return UsersFragment()
            Constants.AUTH_FRAGMENT -> return AuthDialogFragment()
            else -> Log.i("createFragment: ", "you must add your fragment")
        }
        return Fragment()
    }

    override fun setContainerId() {
        setContainerId(R.id.fragment_container)
    }

}