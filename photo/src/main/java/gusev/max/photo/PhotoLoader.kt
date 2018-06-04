package gusev.max.photo

import android.graphics.Bitmap
import io.reactivex.Observable
import javax.inject.Inject

class PhotoLoader @Inject constructor(
    private val mMemoryCache: MemoryBitmapCache,
    private val mDiskCache: DiskBitmapCache,
    private val mNetworkClient: NetworkClient
) {
    fun load(imageUrl: String): Observable<Bitmap> {
        return Observable.just(imageUrl).flatMap { findImageSource(it) }
    }

    private fun findImageSource(imageUrl: String): Observable<Bitmap> {
        if (mMemoryCache.containsKey(imageUrl)) {
            return loadFromCache(imageUrl, mMemoryCache)
        } else if (mDiskCache.containsKey(imageUrl)) {
            return loadFromCache(imageUrl, mDiskCache)
        } else {
            return Observable.defer {
                mNetworkClient.loadImage(imageUrl)
                    .doOnNext { saveToCache(it, imageUrl, mMemoryCache) }
                    .doOnNext { saveToCache(it, imageUrl, mDiskCache) }
            }
        }
    }

    private fun saveToCache(bitmap: Bitmap, url: String, cache: BitmapCache) {
        cache.save(url, bitmap)
    }

    private fun loadFromCache(imageUrl: String, whichBitmapCache: BitmapCache): Observable<Bitmap> {
        return Observable
            .just(whichBitmapCache.get(imageUrl))
    }
}