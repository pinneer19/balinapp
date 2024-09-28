package dev.balinapp.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import dev.balinapp.R
import java.io.File

class CameraManager(
    fragment: Fragment,
    private val context: Context,
    private val onPhotoCaptured: (Uri) -> Unit
) {
    private val cameraPermissionContract =
        fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) launchCamera() else showPermissionDeniedToast()
        }

    private val cameraTakePictureContract =
        fragment.registerForActivityResult(ActivityResultContracts.TakePicture()) { saved ->
            if (saved) onPhotoCaptured(getTemporaryFileUri()) else showPhotoCaptureCancelledToast()
        }

    fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestCameraPermission() {
        cameraPermissionContract.launch(Manifest.permission.CAMERA)
    }

    fun launchCamera() {
        val uri = getTemporaryFileUri()
        cameraTakePictureContract.launch(uri)
    }

    private fun getTemporaryFileUri(): Uri {
        val file = File(context.filesDir, "tmp_photo.jpg")
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }

    private fun showPermissionDeniedToast() {
        Toast.makeText(
            context,
            context.getString(R.string.camera_permission_not_granted),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showPhotoCaptureCancelledToast() {
        Toast.makeText(context, context.getString(R.string.take_photo_cancel), Toast.LENGTH_SHORT)
            .show()
    }
}