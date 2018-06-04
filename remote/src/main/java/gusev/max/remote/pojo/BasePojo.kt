package gusev.max.remote.pojo

import gusev.max.remote.model.BaseRemoteModel


open class BasePojo<M : BaseRemoteModel> {
    var response: BaseResponse<M>? = null
    var error: BaseError? = null

    class BaseResponse<M : BaseRemoteModel> {
        var count: Int = 0
        var items: List<M>? = null
    }
}