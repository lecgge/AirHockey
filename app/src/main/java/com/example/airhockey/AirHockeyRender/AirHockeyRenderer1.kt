package com.example.airhockey.AirHockeyRender


import Point
import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.GL_ONE_MINUS_SRC_ALPHA
import android.opengl.GLES20.GL_SRC_ALPHA
import androidx.compose.ui.graphics.Color
import com.example.airhockey.base.BaseRenderer
import com.example.airhockey.objects.Line
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class AirHockeyRenderer1(private val context: Context) : BaseRenderer(context) {
    lateinit var lineLeft:Line
    lateinit var lineRight:Line
    lateinit var lineMid:Line

    lateinit var line0:Line
    lateinit var line1:Line
    lateinit var line2:Line
    lateinit var line3:Line
    lateinit var line4:Line
    lateinit var line5:Line

    lateinit var line6:Line
    lateinit var line7:Line
    lateinit var line8:Line
    lateinit var line9:Line
    lateinit var line10:Line
    lateinit var line11:Line

    lateinit var line12:Line
    lateinit var line13:Line
    lateinit var line14:Line
    lateinit var line15:Line
    lateinit var line16:Line
    lateinit var line17:Line
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        super.onSurfaceCreated(gl, config)
        line0 = Line(Point(-0.18f,0.05f,0f),0.1f,0.2f)
        line1 = Line(Point(-0.18f,0.2f,0f),0.1f,0.2f)
        line2 = Line(Point(-0.18f,0.35f,0f),0.1f,0.2f)
        line3 = Line(Point(-0.18f,0.5f,0f),0.1f,0.2f)
        line4 = Line(Point(-0.18f,0.65f,0f),0.1f,0.2f)
        line5 = Line(Point(-0.18f,0.8f,0f),0.1f,0.2f)

        line6 = Line(Point(0.18f,0.05f,0f),0.1f,0.2f)
        line7 = Line(Point(0.18f,0.2f,0f),0.1f,0.2f)
        line8 = Line(Point(0.18f,0.35f,0f),0.1f,0.2f)
        line9 = Line(Point(0.18f,0.5f,0f),0.1f,0.2f)
        line10 = Line(Point(0.18f,0.65f,0f),0.1f,0.2f)
        line11 = Line(Point(0.18f,0.8f,0f),0.1f,0.2f)

        line12 = Line(Point(0.18f,0.95f,0f),0.1f,0.2f)
        line13 = Line(Point(0.18f,1.1f,0f),0.1f,0.2f)
        line14 = Line(Point(0.18f,1.25f,0f),0.1f,0.2f)
        line15 = Line(Point(0.18f,1.4f,0f),0.1f,0.2f)
        line16 = Line(Point(0.18f,1.55f,0f),0.1f,0.2f)
        line17 = Line(Point(0.18f,1.7f,0f),0.1f,0.2f)

        lineLeft = Line(Point(0.4f,0f,0f),0.15f,2f)
        lineRight = Line(Point(-0.4f,0f,0f),0.15f,2f)
        lineMid = Line(Point(0f,0f,0f),0.15f,2f)

        gl?.glEnable(GLES20.GL_BLEND)
        gl?.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        super.onSurfaceChanged(gl, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
//        addTextureModel(Table(1001, context, R.drawable.table, Point(0f, 0f, 0f)))

        super.onDrawFrame(gl)

        modelDraw(line0)
        modelDraw(line1)
        modelDraw(line2)
        modelDraw(line3)
        modelDraw(line4)
        modelDraw(line5)

        modelDraw(line6)
        modelDraw(line7)
        modelDraw(line8)
        modelDraw(line9)
        modelDraw(line10)
        modelDraw(line11)

        modelDraw(line12)
        modelDraw(line13)
        modelDraw(line14)
        modelDraw(line15)
        modelDraw(line16)
        modelDraw(line17)

        modelDraw(lineLeft)
        modelDraw(lineRight)
        modelDraw(lineMid)

        modelMap.forEach { id, model ->
            drawTextureModel(model)
        }

    }

    fun modelDraw(line: Line) {
        positionObjectInScene(line)
        colorShaderProgram.useProgram()
        colorShaderProgram.setUniforms(modelViewProjectionMatrix, Color(1f,1f,1f))
        line.bindData(colorShaderProgram = colorShaderProgram)
        line.draw()
    }

    override fun change(y: Int) {

        moveModel(line0,y)
        moveModel(line1,y)
        moveModel(line2,y)
        moveModel(line3,y)
        moveModel(line4,y)
        moveModel(line5,y)

        moveModel(line6,y)
        moveModel(line7,y)
        moveModel(line8,y)
        moveModel(line9,y)
        moveModel(line10,y)
        moveModel(line11,y)

        moveModel(line12,y)
        moveModel(line13,y)
        moveModel(line14,y)
        moveModel(line15,y)
        moveModel(line16,y)
        moveModel(line17,y)
    }

    fun carTurn(){

    }

    fun moveModel(line: Line,y:Int){
        line.position = Point(line.position.x,line.position.y-y*0.001f,line.position.z)
        if (line.position.y < -2f) {
            line.position.y = 1f
        }
    }
}