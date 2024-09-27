package dev.balinapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream

object ImageProcessor {
    fun uriToBase64Image(uri: Uri, context: Context): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)

            val resizedBitmap = resizeImage(originalBitmap, 1280)

            val compressedImage =
                compressImage(resizedBitmap, maxFileSizeInBytes = 2 * 1024 * 1024)

            Base64.encodeToString(compressedImage, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun resizeImage(bitmap: Bitmap, maxSize: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        val scale = if (width > height) {
            maxSize.toFloat() / width
        } else {
            maxSize.toFloat() / height
        }

        val newWidth = (width * scale).toInt()
        val newHeight = (height * scale).toInt()

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

    private fun compressImage(bitmap: Bitmap, maxFileSizeInBytes: Int): ByteArray {
        val outputStream = ByteArrayOutputStream()
        var quality = 100

        do {
            outputStream.reset()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            quality -= 5
        } while (outputStream.size() > maxFileSizeInBytes && quality > 0)

        val finalWidth = 1024
        val finalHeight = 1024
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, finalWidth, finalHeight, true)
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

        return outputStream.toByteArray()
    }
}