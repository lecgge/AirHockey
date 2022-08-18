package com.example.airhockey

import Point
import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.MotionEvent
import com.example.airhockey.AirHockeyRender.AirHockeyRenderer
import com.example.airhockey.base.BaseRenderer
import com.example.airhockey.objects.Car
import com.example.airhockey.objects.Table

/**
 *@Author QiYuZhen
 *@Date 0808 12:24
 */
class AirGLSurfaceView @JvmOverloads constructor(
    var renderer: BaseRenderer,
    context: Context,attrs:AttributeSet?=null
): GLSurfaceView(context,attrs){


    init {
        setEGLContextClientVersion(2)
        setRenderer(renderer)
    }
    override fun onResume() {
        super.onResume()
    }
}