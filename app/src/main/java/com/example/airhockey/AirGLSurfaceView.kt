package com.example.airhockey

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import com.example.airhockey.base.BaseRenderer

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