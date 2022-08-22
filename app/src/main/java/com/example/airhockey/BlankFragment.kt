package com.example.airhockey

import Point
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.airhockey.AirHockeyRender.AirHockeyRenderer
import com.example.airhockey.AirHockeyRender.AirHockeyRenderer1
import com.example.airhockey.App.Companion.context
import com.example.airhockey.base.GLESHelper
import com.example.airhockey.base.OnTouchListener
import com.example.airhockey.objects.Car


class BlankFragment : Fragment() {

    var width = 0
    var height = 0

    var widthView =0
    var heightView =0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        activity?.apply {
            width = windowManager.currentWindowMetrics.bounds.width()
        }

        activity?.apply {
            height = windowManager.currentWindowMetrics.bounds.height()
        }
        context?.let { GLESHelper.init(context = it, renderer = AirHockeyRenderer(it)) }


        var y = -0.5f
        var z = -0.5f
        GLESHelper.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        GLESHelper.airGLSurfaceView.queueEvent {
                            y += 0.01f
                            z += 0.05f
                            for (i in 0..5) {

                            }
                            GLESHelper.renderer.moveTextureModel(
                                Car(
                                    1,
                                    App.context,
                                    Point((0).toFloat(), y, 0F)
                                )
                            )
                            GLESHelper.renderer.moveTextureModel(
                                Car(
                                    0,
                                    App.context,
                                    Point(-0.2f, z, 0F)
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