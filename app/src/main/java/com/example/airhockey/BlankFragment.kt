package com.example.airhockey

import Point
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.airhockey.AirHockeyRender.AirHockeyRenderer1
import com.example.airhockey.base.GLESHelper
import com.example.airhockey.base.OnTouchListener
import com.example.airhockey.objects.Car


class BlankFragment : Fragment() {

    var width = 0
    var height = 0

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
        context?.let { GLESHelper.init(context = it, renderer = AirHockeyRenderer1(it)) }

        var angle = 0f
        var x = -0.35f
        var y = 0f

        var tag = false
        GLESHelper.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {

                        GLESHelper.airGLSurfaceView.queueEvent {

                            for (i in 0..10) {
                                GLESHelper.renderer.change(i)
                            }


                            GLESHelper.renderer.moveTextureModel(
                                Car(
                                    id = 0,
                                    context = App.context,
                                    position = Point(x, y, 0F),
                                    resId = R.drawable.car,
                                    isTurn = true,
                                    turn = angle
                                )
                            )
                        }
                        if (tag) {
                            if (angle <= 0 && angle >= -60) {
                                angle += 10f
                            }
                            if (angle == 0f) {
                                tag = false
                            }
                        } else {
                            if (angle<=0 && angle>= -60) {
                                angle -= 10f
                            }
                            if (angle == -60f) {
                                tag = true
                            }
                        }
                        Log.d("TAG1", "onTouch: tag:$tag")

                        x += 0.06f
                        y += 0.01f
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