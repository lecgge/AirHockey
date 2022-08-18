package com.example.airhockey.base

import android.annotation.SuppressLint
import com.example.airhockey.AirGLSurfaceView
import com.example.airhockey.AirHockeyRender.AirHockeyRenderer
import com.example.airhockey.App

object GLESHelper {
    var airGLSurfaceView: AirGLSurfaceView
    var renderer : AirHockeyRenderer

    init {
        renderer = AirHockeyRenderer(App.context)
        airGLSurfaceView = AirGLSurfaceView(renderer,App.context)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setOnTouchListener(onTouchListener: OnTouchListener) {
        airGLSurfaceView.setOnTouchListener(onTouchListener)
    }
}