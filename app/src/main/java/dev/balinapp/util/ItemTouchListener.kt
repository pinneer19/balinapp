package dev.balinapp.util

import android.view.View

interface ItemTouchListener {

    fun onItemClick(view: View?)

    fun onItemLongClick(view: View?, position: Int)
}