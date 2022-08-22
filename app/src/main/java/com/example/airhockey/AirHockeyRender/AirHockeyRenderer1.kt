package com.example.airhockey.AirHockeyRender


import Point
import android.content.Context
import androidx.compose.ui.graphics.Color
import com.example.airhockey.R
import com.example.airhockey.base.BaseRenderer
import com.example.airhockey.base.BaseTextureModel
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
        super.onDrawFrame(gl)
        positionObjectInScene(line)
        colorShaderProgram.useProgram()
        colorShaderProgram.setUniforms(modelViewProjectionMatrix, Color(1f,1f,1f))
        line.bindData(colorShaderProgram = colorShaderProgram)
        line.draw()

        positionObjectInScene(line1)
        colorShaderProgram.useProgram()
        colorShaderProgram.setUniforms(modelViewProjectionMatrix, Color(1f,1f,1f))
        line1.bindData(colorShaderProgram = colorShaderProgram)
        line1.draw()

        positionObjectInScene(line2)
        colorShaderProgram.useProgram()
        colorShaderProgram.setUniforms(modelViewProjectionMatrix, Color(1f,1f,1f))
        line2.bindData(colorShaderProgram = colorShaderProgram)
        line2.draw()
    }

    override fun change(y: Int) {
        line.position = Point(line.position.x,line.position.y-y*0.001f,line.position.z)
        line1.position = Point(line1.position.x,line1.position.y-y*0.001f,line1.position.z)
        line2.position = Point(line2.position.x,line2.position.y-y*0.001f,line2.position.z)

        if (line.position.y < -1f) {
            line.position.y = 1f
        }
        if (line1.position.y < -1f) {
            line1.position.y = 1f
        }
        if (line2.position.y < -1f) {
            line2.position.y = 1f
        }
    }
}