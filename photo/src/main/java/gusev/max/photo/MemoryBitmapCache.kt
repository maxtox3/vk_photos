package gusev.max.photo

import android.graphics.Bitmap
import android.util.LruCache
import javax.inject.Inject

class MemoryBitmapCache @Inject constructor() : BitmapCache {

    companion object {
        val CACHE_SIZE_BYTES = (Runtime.getRuntime().maxMemory() / 1024 / 2000).toInt()
    }

    private val mCache = LruCache<String, Bitmap>(CACHE_SIZE_BYTES)

    override fun getName(): String {
        return "Memory Cache"
    }

    override fun containsKey(key: String): Boolean {
        synchronized(mCache) {
            val existingBitmap = get(key)
            return existingBitmap != null
        }
    }

    override operator fun get(key: String): Bitmap? {
        return mCache.get(key)
    }

    override fun save(key: String, bitmapToSave: Bitmap) {
        mCache.put(key, bitmapToSave)
    }

    override fun clear() {
        mCache.evictAll()
    }

}