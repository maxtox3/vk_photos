package gusev.max.presentation.fullphoto

import gusev.max.photo.PhotoLoader
import io.reactivex.ObservableTransformer
import javax.inject.Inject


class GetPhotoProcessor @Inject constructor(
    private val photoLoader: PhotoLoader
) {
    private val processor: ObservableTransformer<GetPhotoAction.LoadPhoto, GetPhotoResult> = ObservableTransformer {
        it.switchMap { action ->
            photoLoader.load(action.user!!.photo100Url)
                .map {
                    action.user.bitmap = it
                    GetPhotoResult.LoadFullPhoto.success(action.user)
                }
                .onErrorReturn {
                    action.user.error = true
                    GetPhotoResult.LoadFullPhoto.failure(action.user)
                }
                .startWith(GetPhotoResult.LoadFullPhoto.inFlight(action.user))
        }
    }

    var actionProcessor: ObservableTransformer<GetPhotoAction, GetPhotoResult>

    init {
        this.actionProcessor = ObservableTransformer {
            it.publish {
                it.ofType(GetPhotoAction.LoadPhoto::class.java)
                    .compose(processor)
            }
        }
    }
}