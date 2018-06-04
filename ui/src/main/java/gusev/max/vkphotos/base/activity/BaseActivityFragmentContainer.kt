package gusev.max.vkphotos.base.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable



abstract class BaseActivityFragmentContainer : AppCompatActivity(),
        FragmentContainer {
    private lateinit var disposable: CompositeDisposable
    private var currentTag: String? = null
    private var containerId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("ACTIVITY", "ON CREATE")
        super.onCreate(savedInstanceState)
        setContainerId()
        if (savedInstanceState != null) {
            currentTag = savedInstanceState.getString(SAVED_FRAGMENT_TAG)
            restoreFragment(currentTag)
        }
        disposable = CompositeDisposable()
    }

    override fun onDestroy() {
        disposable.dispose()
        Log.d("ACTIVITY", "ON DESTROY AND DISPOSE")
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }

    public override fun onSaveInstanceState(outState: Bundle?) {
        if (currentTag != null) {
            outState?.putString(SAVED_FRAGMENT_TAG, currentTag)
        }
        super.onSaveInstanceState(outState)
    }

    protected fun navigateToFragmentWithRemoveExisting(
        tag: String,
        args: Bundle,
        addToBackStack: Boolean
    ) {
        val transaction = supportFragmentManager.beginTransaction()

        if (addToBackStack) {
            transaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction.addToBackStack(null)
        }

        if (supportFragmentManager.findFragmentByTag(tag) != null) {
            remove(transaction, supportFragmentManager.findFragmentByTag(tag))
        } else {
            val fragment = createFragment(tag, args)
            add(transaction, fragment, tag)
        }
    }

    protected fun removeFragment(tag: String) {
        val transaction = supportFragmentManager.beginTransaction()

        if (supportFragmentManager.findFragmentByTag(tag) != null) {
            remove(transaction, supportFragmentManager.findFragmentByTag(tag))
        }
    }

    protected fun navigateToFragment(tag: String, args: Bundle?, addToBackStack: Boolean) {

        val transaction = supportFragmentManager.beginTransaction()

        if (addToBackStack) {
            transaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction.addToBackStack(null)
        }

        if (supportFragmentManager.findFragmentByTag(tag) != null) {
            replace(transaction, supportFragmentManager.findFragmentByTag(tag), tag)
        } else {
            val fragment = createFragment(tag, args)
            add(transaction, fragment, tag)
        }
    }

    protected fun addDisposable(disposable: Disposable) {
        this.disposable.add(disposable)
    }

    protected fun setContainerId(containerId: Int) {
        this.containerId = containerId
    }

    protected abstract fun createFragment(tag: String, args: Bundle?): Fragment

    private fun restoreFragment(tag: String?) {
        val transaction = supportFragmentManager.beginTransaction()
        if (supportFragmentManager.findFragmentByTag(tag) != null) {
            replace(transaction, supportFragmentManager.findFragmentByTag(tag), tag)
        }
    }

    protected fun setCurrentTag(tag: String) {
        currentTag = tag
    }

    private fun replace(transaction: FragmentTransaction, fragment: Fragment, tag: String?) {
        transaction.replace(containerId, fragment, tag).commit()
    }

    private fun remove(transaction: FragmentTransaction, fragment: Fragment) {
        transaction.remove(fragment).commit()
    }

    private fun add(transaction: FragmentTransaction, fragment: Fragment, tag: String) {
        transaction.add(containerId, fragment, tag).commit()
    }

    companion object {
        const val SAVED_FRAGMENT_TAG = "SAVED_FRAGMENT_TAG"
    }
}