package gusev.max.vkphotos.auth

import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import gusev.max.vkphotos.base.Constants
import gusev.max.vkphotos.base.widgets.dialog.DialogResultListener

class AuthWebView(
    private val resultListener: DialogResultListener<String>
) : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return when {
            url!!.startsWith(Constants.CALLBACK_URL) and !url.contains("error") -> {
                true
            }
            url.contains("error") -> {
                false
            }
            else -> {
                Log.d("DIALOG", "url not contains callback url")
                view!!.loadUrl(url)
                true
            }
        }
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        resultListener.onDialogError()
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)

        when {
            url!!.contains("error") -> {
                resultListener.onDialogError()
                return
            }
            url.contains("access_token") -> {
                resultListener.onDialogSuccess(url)
                return
            }
            else -> {
            }
        }
    }
}