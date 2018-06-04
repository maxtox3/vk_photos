package gusev.max.vkphotos.base.fragment

import android.content.Context
import gusev.max.vkphotos.base.activity.BaseActivityCallback

abstract class BaseFragmentWithCallback<ACTIVITY_CALLBACK : BaseActivityCallback> : BaseFragment() {

    protected lateinit var activityCallback: ACTIVITY_CALLBACK

    override fun onAttach(context: Context?) {
        try {
            initActivityCallback(context)
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement activityCallback")
        }
        super.onAttach(context)
    }

    abstract fun initActivityCallback(context: Context?)


}