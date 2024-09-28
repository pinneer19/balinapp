package dev.balinapp.util

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import dev.balinapp.R

fun Fragment.showConfirmationDialog(
    title: String,
    approvalMessage: String,
    confirmButtonText: String,
    action: () -> Unit
) {
    AlertDialog.Builder(requireContext())
        .setTitle(title)
        .setMessage(approvalMessage)
        .setPositiveButton(confirmButtonText) { dialog, _ ->
            action()
            dialog.dismiss()
        }
        .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }
        .create()
        .show()
}