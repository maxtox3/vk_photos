package gusev.max.remote

import gusev.max.data.auth.AuthCache
import gusev.max.remote.api.UserApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


object VkService {

    private const val ACCESS_TOKEN_FIELD = "access_token"

    fun makeUserApi(isDebug: Boolean, baseUrl: String, cache: AuthCache): UserApi {
        return makeDefaultRetrofit(isDebug, baseUrl, cache).create(UserApi::class.java)
    }

    private fun makeDefaultRetrofit(
        isDebug: Boolean,
        baseUrl: String,
        cache: AuthCache
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(makeHttpClient(makeLoggingInterceptor(isDebug), cache))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun makeHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        cache: AuthCache
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor(cache))
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return logging
    }

    private class TokenInterceptor(
        private val authCache: AuthCache
    ) : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val newRequest = request.url().let {
                val newUrl = chain.request().url().newBuilder()
                    .scheme(it.scheme())
//                    .query(it.query() + "&$ACCESS_TOKEN_FIELD=${authCache.getAccessToken()}")
                    .addQueryParameter(ACCESS_TOKEN_FIELD, authCache.getAccessToken())
                    .host(it.host())
                    .port(it.port())
                    .build()

                return@let chain.request().newBuilder()
                    .url(newUrl)
                    .build()
            }

            return chain.proceed(newRequest)
        }
    }

}

