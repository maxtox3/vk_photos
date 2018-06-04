package gusev.max.presentation.main.browsefriends

import gusev.max.presentation.base.BaseAction


sealed class BrowseFriendsAction : BaseAction {

    object LoadFriends : BrowseFriendsAction()
}