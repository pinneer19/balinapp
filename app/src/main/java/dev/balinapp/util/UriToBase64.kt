package dev.balinapp.util

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Base64OutputStream
import java.io.ByteArrayOutputStream
import java.io.FileInputStream

fun Uri.toBase64(context: Context): String? {
//    context.contentResolver.openInputStream(this)?.use { inputStream ->
//        val bytes = inputStream.readBytes()
//        Base64.encodeToString(bytes, Base64.DEFAULT)
//    }

    return context.contentResolver.openInputStream(this)?.use { inputStream ->
        ByteArrayOutputStream().use { outputStream ->
            Base64OutputStream(outputStream, Base64.DEFAULT).use { base64FilterStream ->
                inputStream.copyTo(base64FilterStream)
                base64FilterStream.flush()
                outputStream.toString()
            }
        }
    }
}