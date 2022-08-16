package com.example.airhockey

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import com.example.airhockey.AirHockeyRender.AirHockeyRenderer5
import com.example.airhockey.AirHockeyRender.AirHockeyRenderer6
import com.example.airhockey.AirHockeyRender.AirHockeyRenderer7
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

/**
 *@Author QiYuZhen
 *@Date 0808 12:24
 */
class AirGLSurfaceView @JvmOverloads constructor(
    context: Context,attrs:AttributeSet?=null
): GLSurfaceView(context,attrs){
     private val renderer= AirHockeyRenderer7(context)

    init {
        setEGLContextClientVersion(2)
        setRenderer(renderer)

        var y = -0.5f

        setOnTouchListener { v, event ->
            when(event?.actionMasked){
                MotionEvent.ACTION_DOWN ->{
                    queueEvent {
                        renderer.carPositionChange(-0.2f,y)
                        Log.d("A", "for: $y")
                        y += 0.01f
                    }
                    true
                }
                MotionEvent.ACTION_MOVE ->{
                    false
                }
                else ->{
                    performClick()
                    false
                }
            }
        }



    }

    override fun onResume() {
        super.onResume()

    }




}