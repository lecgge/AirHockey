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
    private var lifecycleState by mutableStateOf(Lifecycle.Event.ON_ANY)   //界面状态lifecycleState由mutableStateOf创建并托管
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        // 观察lifecycle状态
        lifecycle.addObserver(object : DefaultLifecycleObserver {

            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                lifecycleState = Lifecycle.Event.ON_RESUME

            }

            override fun onPause(owner: LifecycleOwner) {
                super.onPause(owner)
                lifecycleState = Lifecycle.Event.ON_PAUSE
            }

        })
        setContent {
            //// 在Compose下托管传统视图
            AndroidView(
                factory = {

                    AirGLSurfaceView(it)
                },
                //// lifecycleState更新时被回调
                update = {
                    when (lifecycleState) {
                        Lifecycle.Event.ON_PAUSE -> {
                            it.onPause()
                        }
                        Lifecycle.Event.ON_RESUME -> {
                            it.onResume()

                        }
                        else -> {}
                    }
                }
            )
        }
    }
}