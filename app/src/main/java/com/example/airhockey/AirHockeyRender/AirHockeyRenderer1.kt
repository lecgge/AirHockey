package com.example.airhockey.AirHockeyRender


import Point
import android.content.Context
import androidx.compose.ui.graphics.Color
import com.example.airhockey.R
import com.example.airhockey.base.BaseRenderer
import com.example.airhockey.objects.Line
import com.example.airhockey.objects.Table
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class AirHockeyRenderer1(private val context: Context) : BaseRenderer(context) {
    lateinit var line:Line
    lateinit var line1:Line
    lateinit var line2:Line
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        super.onSurfaceCreated(gl, config)
        line = Line(Point(0f,0f,0f),0.1f,0.2f)
        line1 = Line(Point(0f,0.25f,0f),0.1f,0.2f)
        line2 = Line(Point(0f,0.5f,0f),0.1f,0.2f)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        super.onSurfaceChanged(gl, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
//        addTextureModel(Table(1001, context, R.drawable.table, Point(0f, 0f, 0f)))
//        super.onDrawFrame(gl)
        positionObjectInScene(line)
        colorShaderProgram.useProgram()
        colorShaderProgram.setUniforms(modelViewProjectionMatrix, Color(1f,1f,1f))
        line.bindData(colorShaderProgram = colorShaderProgram)
        line.draw()

        colorShaderProgram.useProgram()
        colorShaderProgram.setUniforms(modelViewProjectionMatrix, Color(1f,1f,1f))
        line1.bindData(colorShaderProgram = colorShaderProgram)
        line1.draw()

        colorShaderProgram.useProgram()
        colorShaderProgram.setUniforms(modelViewProjectionMatrix, Color(1f,1f,1f))
        line2.bindData(colorShaderProgram = colorShaderProgram)
        line2.draw()
    }

    override fun change(y: Int) {
        line.position.y += y*0.01f
        line1.position.y += y*0.01f
        line2.position.y += y*0.01f

    }
}