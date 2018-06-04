package gusev.max.vkphotos.auth

interface AuthResultListener {
    fun onAuthSuccess(url: String)
    fun onAuthError(){
    }
}