package gusev.max.vkphotos.friends

import android.view.View
import gusev.max.presentation.model.UserModel
import gusev.max.vkphotos.base.widgets.adapter.BaseViewHolder
import kotlinx.android.synthetic.main.item_friend.view.*

class FriendViewHolder(
    private val needToLoadPhotoListener: NeedToLoadPhotoListener,
    itemView: View,
    clickListener: ModelClickListener<UserModel>
) : BaseViewHolder<UserModel>(itemView, clickListener) {

    interface NeedToLoadPhotoListener {
        fun onNeedToLoadPhoto(userModel: UserModel)
    }

    override fun bind(model: UserModel) {
        super.bind(model)
        itemView.name.text = model.getName()
        itemView.photo_img.transitionName = model.id.toString()
        setupAvatar(model)
        setupError(model)
    }

    private fun setupError(model: UserModel) {
        if (model.error) {
            //todo show try again
        }
    }

    private fun setupAvatar(model: UserModel) {
        if (model.bitmap != null) {
            itemView.photo_img.setImageBitmap(model.bitmap)
            itemView.progress_bar.visibility = View.GONE
        } else {
            needToLoadPhotoListener.onNeedToLoadPhoto(model)
            itemView.progress_bar.visibility = View.VISIBLE
        }
    }

    override fun getView(): View {
        return itemView.photo_img
    }
}