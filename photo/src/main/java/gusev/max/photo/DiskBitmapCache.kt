package gusev.max.photo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.*
import java.net.URLEncoder
import javax.inject.Inject

class DiskBitmapCache @Inject constructor(context: Context) : BitmapCache {

    companion object {

        private val LOCK = Any()
        @Volatile
        private var sDiskCache: DiskBitmapCache? = null

        fun getInstance(context: Context): DiskBitmapCache {
            if (sDiskCache == null) {
                synchronized(LOCK) {
                    if (sDiskCache == null) {
                        sDiskCache = DiskBitmapCache(context)
                    }
                }
            }
            return sDiskCache!!
        }
    }

    private val mCacheDirectory: File = context.cacheDir

    override fun getName(): String {
        return "Disk Cache"
    }

    override fun containsKey(key: String): Boolean {
        synchronized(mCacheDirectory) {
            val existingBitmap = get(key)
            return existingBitmap != null
        }
    }

    override operator fun get(key: String): Bitmap? {
        synchronized(mCacheDirectory) {
            val cacheFileName = encodeKey(key)
            val foundCacheFiles = mCacheDirectory.listFiles({ dir, filename ->
                filename == cacheFileName
            })

            return if (foundCacheFiles == null || foundCacheFiles.isEmpty()) {
                null
            } else readBitmapFromFile(foundCacheFiles[0])
        }
    }

    override fun save(key: String, bitmapToSave: Bitmap) {
        val cacheFileName = encodeKey(key)
        val cacheFile = File(mCacheDirectory, cacheFileName)

        try {
            val fileOutputStream = FileOutputStream(cacheFile)
            saveBitmapToFile(bitmapToSave, fileOutputStream)

        } catch (e: java.io.IOException) {
            e.printStackTrace()
        }

    }

    override fun clear() {
        synchronized(mCacheDirectory) {
            val cachedFiles = mCacheDirectory.listFiles()
            if (cachedFiles != null) {
                for (cacheFile in cachedFiles) {
                    cacheFile.delete()
                }
            }
            mCacheDirectory.delete()
        }
    }

    private fun encodeKey(toEncodeString: String): String? {
        try {
            return URLEncoder.encode(toEncodeString, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return null
    }

    private fun readBitmapFromFile(foundCacheFile: File): Bitmap? {
        try {
            val fileInputStream = FileInputStream(foundCacheFile)
            return BitmapFactory.decodeStream(fileInputStream)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        return null
    }

    @Throws(IOException::class)
    private fun saveBitmapToFile(bitmapToSave: Bitmap, fileOutputStream: FileOutputStream) {
        fileOutputStream.use { stream ->
            bitmapToSave.compress(Bitmap.CompressFormat.PNG, 100, stream)
        }
    }

}