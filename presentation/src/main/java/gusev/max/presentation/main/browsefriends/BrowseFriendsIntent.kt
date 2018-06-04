package gusev.max.presentation.main.browsefriends

import gusev.max.presentation.base.BaseIntent


sealed class BrowseFriendsIntent : BaseIntent {

    object InitialIntent : BrowseFriendsIntent()

    object BrowseFriends : BrowseFriendsIntent()

    object TryAgain : BrowseFriendsIntent()
}