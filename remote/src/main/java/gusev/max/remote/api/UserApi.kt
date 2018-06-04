package gusev.max.remote.api

import gusev.max.remote.pojo.UsersPojo
import io.reactivex.Flowable
import retrofit2.http.GET



interface UserApi {

    companion object {

        /**
         * hardcode -> should make builder for params
         */
        private const val GET_FRIENDS_URL = "friends.get?fields=photo_max_orig%2Cphoto_100&v=5.78"
    }

    @GET(GET_FRIENDS_URL)
    fun getFriends(): Flowable<UsersPojo>

}