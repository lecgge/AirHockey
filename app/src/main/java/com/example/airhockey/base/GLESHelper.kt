package com.example.airhockey.base

import android.annotation.SuppressLint
import android.content.Context
import com.example.airhockey.AirGLSurfaceView


object GLESHelper {
    lateinit var airGLSurfaceView: AirGLSurfaceView
    @SuppressLint("StaticFieldLeak")
    lateinit var renderer: BaseRenderer

    fun init(context: Context,renderer: BaseRenderer) {
        this.renderer = renderer
        airGLSurfaceView = AirGLSurfaceView(renderer, context)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setOnTouchListener(onTouchListener: OnTouchListener) {
        airGLSurfaceView.setOnTouchListener(onTouchListener)
    }

}