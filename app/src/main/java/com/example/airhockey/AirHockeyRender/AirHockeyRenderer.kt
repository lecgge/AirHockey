package com.example.airhockey.AirHockeyRender


import Point
import android.content.Context
import com.example.airhockey.R
import com.example.airhockey.base.BaseRenderer
import com.example.airhockey.objects.Table
import javax.microedition.khronos.opengles.GL10


class AirHockeyRenderer(private val context: Context) : BaseRenderer(context) {
    override fun onDrawFrame(gl: GL10?) {
        addTextureModel(Table(1001, context, R.drawable.table, Point(0f, 0f, 0f)))
        super.onDrawFrame(gl)
    }
}