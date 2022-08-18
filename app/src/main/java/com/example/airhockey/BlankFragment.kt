package com.example.airhockey

import Point
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.airhockey.base.GLESHelper
import com.example.airhockey.base.OnTouchListener
import com.example.airhockey.objects.Car

class BlankFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var y = -0.5f
        GLESHelper.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        GLESHelper.airGLSurfaceView.queueEvent {
                            y += 0.01f
                            for (i in 0..5) {
                                GLESHelper.renderer.addTextureModel(
                                    Car(
                                        i,
                                        App.context,
                                        Point((-0.2 + 0.2 * i).toFloat(), -0.5F, 0F)
                                    )
                                )
                            }
                            GLESHelper.renderer.moveTextureModel(
                                Car(
                                    1,
                                    App.context,
                                    Point((0).toFloat(), y, 0F)
                                )
                            )
                        }
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        return false
                    }
                    else -> {
                        GLESHelper.airGLSurfaceView.performClick()
                        return false
                    }
                }
            }
        })
        return GLESHelper.airGLSurfaceView
    }

    override fun onResume() {
        super.onResume()
        GLESHelper.airGLSurfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        GLESHelper.airGLSurfaceView.onPause()
    }
}