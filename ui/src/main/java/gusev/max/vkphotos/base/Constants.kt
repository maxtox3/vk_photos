package gusev.max.vkphotos.base

import gusev.max.vkontakte_photos.BuildConfig

object Constants {
    const val USERS_FRAGMENT = "UsersFragment"
    const val AUTH_FRAGMENT = "AuthDialogFragment"

    const val CALLBACK_URL = "http://api.vkontakte.ru/blank.html"
    const val OAUTH_AUTHORIZE_URL = "https://oauth.vk.com/authorize?client_id=" + BuildConfig.VK_APP_ID + "&redirect_uri=https://oauth.vk.com/blank.html&scope=friends&response_type=token&v=5.52"
}