package com.example.airhockey

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

class MainActivity : AppCompatActivity() {
    private lateinit var airGLSurfaceView : AirGLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        airGLSurfaceView = AirGLSurfaceView(this)
        setContentView(airGLSurfaceView)
    }

    override fun onResume() {
        super.onResume()
        airGLSurfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        airGLSurfaceView.onResume()
    }
}