package gusev.max.vkphotos.base.widgets.dialog

interface DialogResultListener<TYPE> {
    fun onDialogSuccess(result: TYPE)
    fun onDialogError()
}