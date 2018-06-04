package gusev.max.vkphotos.friends

import android.view.LayoutInflater
import android.view.ViewGroup
import gusev.max.presentation.model.UserModel
import gusev.max.vkontakte_photos.R
import gusev.max.vkphotos.base.widgets.adapter.BaseAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class FriendsAdapter @Inject constructor() : BaseAdapter<UserModel, FriendViewHolder>(),
        FriendViewHolder.NeedToLoadPhotoListener {

    private var onBind: Boolean = false
    private val publishSubject: PublishSubject<UserModel> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FriendViewHolder {
        val itemView = LayoutInflater
            .from(parent?.context)
            .inflate(R.layout.item_friend, parent, false)
        return FriendViewHolder(this, itemView, this)
    }

    override fun onBindViewHolder(holder: FriendViewHolder?, position: Int) {
        onBind = true
        holder?.bind(modelList[position])
        onBind = false
    }

    fun updateModel(userModel: UserModel?) {
        if (userModel != null) {
            val index = modelList.indexOf(getById(modelList, userModel))
            modelList[index] = userModel
            if (!onBind) {
                notifyItemChanged(index)
            }
        }
    }

    private fun getById(list: List<UserModel>, model: UserModel): UserModel? {
        return list.first { it.id == model.id }
    }

    override fun onNeedToLoadPhoto(userModel: UserModel) {
        publishSubject.onNext(userModel)
    }

    fun getPhotoLoadObservable(): Observable<UserModel> {
        return publishSubject
    }
}