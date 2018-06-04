package gusev.max.photo

import android.graphics.Bitmap

interface BitmapCache {

    fun getName(): String

    fun containsKey(key: String): Boolean

    fun get(key: String): Bitmap?

    fun save(key: String, bitmapToSave: Bitmap)

    fun clear()
}