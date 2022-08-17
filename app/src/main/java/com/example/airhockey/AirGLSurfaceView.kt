package com.example.airhockey

import Point
import android.content.Context
import android.graphics.PixelFormat
import android.opengl.GLES20.GL_BLEND
import android.opengl.GLES20.glEnable
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import com.example.airhockey.AirHockeyRender.AirHockeyRenderer5
import com.example.airhockey.AirHockeyRender.AirHockeyRenderer6
import com.example.airhockey.AirHockeyRender.AirHockeyRenderer7
import com.example.airhockey.objects.Car
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
//                        renderer.carPositionChange(-0.2f,y)
//                        Log.d("A", "for: $y")
                        y += 0.01f

                        for (i in 0..5) {
                            renderer.addCar(Car(i,context,R.drawable.img,Point((-0.2+0.2*i).toFloat(), -0.5F, 0F)))
                        }

                        renderer.addCar(Car(1,context,R.drawable.img,Point((0).toFloat(), y, 0F)))
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