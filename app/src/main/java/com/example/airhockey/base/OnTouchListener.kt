package com.example.airhockey.base

import android.view.MotionEvent
import android.view.View

interface OnTouchListener : View.OnTouchListener {
    override fun onTouch(v: View?, event: MotionEvent?): Boolean
}