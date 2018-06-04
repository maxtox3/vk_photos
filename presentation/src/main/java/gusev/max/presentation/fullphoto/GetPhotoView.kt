package gusev.max.presentation.fullphoto

import io.reactivex.Observable


interface GetPhotoView {
    fun photoIntents(): Observable<GetPhotoIntent>
    fun render(state: GetPhotoViewState)
}