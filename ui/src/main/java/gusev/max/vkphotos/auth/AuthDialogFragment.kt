package gusev.max.vkphotos.auth

import gusev.max.vkphotos.base.Constants
import gusev.max.vkphotos.base.widgets.dialog.BaseDialogFragment
import gusev.max.vkphotos.base.widgets.dialog.DialogResultListener
import kotlinx.android.synthetic.main.dialog_fragment.*

class AuthDialogFragment : BaseDialogFragment(),
        DialogResultListener<String> {

    private lateinit var authResultListener: AuthResultListener

    fun setAuthResultListener(authResultListener: AuthResultListener) {
        this.authResultListener = authResultListener
    }

    override fun setupWidgets() {
        auth_web_view.isVerticalScrollBarEnabled = false
        auth_web_view.isHorizontalScrollBarEnabled = false
        auth_web_view.webViewClient = AuthWebView(this)
        auth_web_view.settings.javaScriptEnabled = true
        auth_web_view.loadUrl(Constants.OAUTH_AUTHORIZE_URL)
    }

    override fun onDialogSuccess(result: String) {
        authResultListener.onAuthSuccess(result)
    }

    override fun onDialogError() {
        authResultListener.onAuthError()
    }

    override fun initResultListener() {

    }
}