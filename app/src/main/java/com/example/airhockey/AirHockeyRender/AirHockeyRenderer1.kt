package com.example.airhockey.AirHockeyRender


import Point
import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.*
import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.airhockey.base.BaseColorModel
import com.example.airhockey.base.BaseRenderer
import com.example.airhockey.base.BaseTextureModel
import com.example.airhockey.objects.Line
import com.example.airhockey.util.loadTexture
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class AirHockeyRenderer1(private val context: Context,isPerspective:Boolean) : BaseRenderer(context,isPerspective) {

    override val textureModelMap: MutableMap<Int, BaseTextureModel> = mutableMapOf()
    override val colorModelMap: MutableMap<Int, BaseColorModel> = mutableMapOf()


    lateinit var lineLeft: Line
    lateinit var lineRight: Line
    lateinit var lineMid: Line

    lateinit var line0: Line
    lateinit var line1: Line
    lateinit var line2: Line
    lateinit var line3: Line
    lateinit var line4: Line
    lateinit var line5: Line

    lateinit var line6: Line
    lateinit var line7: Line
    lateinit var line8: Line
    lateinit var line9: Line
    lateinit var line10: Line
    lateinit var line11: Line

    lateinit var line12: Line
    lateinit var line13: Line
    lateinit var line14: Line
    lateinit var line15: Line
    lateinit var line16: Line
    lateinit var line17: Line

    lateinit var line18: Line
    lateinit var line19: Line
    lateinit var line20: Line
    lateinit var line21: Line
    lateinit var line22: Line
    lateinit var line23: Line
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        super.onSurfaceCreated(gl, config)
        line0 = Line(0, Point(-0.18f, 0.05f, 0f), 0.1f, 0.2f)
        line1 = Line(1, Point(-0.18f, 0.2f, 0f), 0.1f, 0.2f)
        line2 = Line(2, Point(-0.18f, 0.35f, 0f), 0.1f, 0.2f)
        line3 = Line(3, Point(-0.18f, 0.5f, 0f), 0.1f, 0.2f)
        line4 = Line(4, Point(-0.18f, 0.65f, 0f), 0.1f, 0.2f)
        line5 = Line(5, Point(-0.18f, 0.8f, 0f), 0.1f, 0.2f)

        line6 = Line(6, Point(0.18f, 0.05f, 0f), 0.1f, 0.2f)
        line7 = Line(7, Point(0.18f, 0.2f, 0f), 0.1f, 0.2f)
        line8 = Line(8, Point(0.18f, 0.35f, 0f), 0.1f, 0.2f)
        line9 = Line(9, Point(0.18f, 0.5f, 0f), 0.1f, 0.2f)
        line10 = Line(10, Point(0.18f, 0.65f, 0f), 0.1f, 0.2f)
        line11 = Line(11, Point(0.18f, 0.8f, 0f), 0.1f, 0.2f)

        line12 = Line(12, Point(0.18f, 0.95f, 0f), 0.1f, 0.2f)
        line13 = Line(13, Point(0.18f, 1.1f, 0f), 0.1f, 0.2f)
        line14 = Line(14, Point(0.18f, 1.25f, 0f), 0.1f, 0.2f)
        line15 = Line(15, Point(0.18f, 1.4f, 0f), 0.1f, 0.2f)
        line16 = Line(16, Point(0.18f, 1.55f, 0f), 0.1f, 0.2f)
        line17 = Line(17, Point(0.18f, 1.7f, 0f), 0.1f, 0.2f)

        line18 = Line(18, Point(-0.18f, 0.05f, 0f), 0.1f, 0.2f)
        line19 = Line(19, Point(-0.18f, 0.2f, 0f), 0.1f, 0.2f)
        line20 = Line(20, Point(-0.18f, 0.35f, 0f), 0.1f, 0.2f)
        line21 = Line(21, Point(-0.18f, 0.5f, 0f), 0.1f, 0.2f)
        line22 = Line(22, Point(-0.18f, 0.65f, 0f), 0.1f, 0.2f)
        line23 = Line(23, Point(-0.18f, 0.8f, 0f), 0.1f, 0.2f)

        lineLeft = Line(24,Point(0.4f, 0f, 0f), 0.15f, 2f)
        lineRight = Line(25,Point(-0.4f, 0f, 0f), 0.15f, 2f)
        lineMid = Line(26,Point(0f, 0f, 0f), 0.15f, 2f)

        addColorModel(line0)
        addColorModel(line1)
        addColorModel(line2)
        addColorModel(line3)
        addColorModel(line4)
        addColorModel(line5)
        addColorModel(line6)
        addColorModel(line7)
        addColorModel(line8)
        addColorModel(line9)
        addColorModel(line10)
        addColorModel(line11)
        addColorModel(line12)
        addColorModel(line13)
        addColorModel(line14)
        addColorModel(line15)
        addColorModel(line16)
        addColorModel(line17)
        addColorModel(lineLeft)
        addColorModel(lineRight)
        addColorModel(lineMid)

        textureModelMap.forEach { key,value->
            value.texture= context.loadTexture(value.resId)
        }

        gl?.glEnable(GLES20.GL_BLEND)
        gl?.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        super.onSurfaceChanged(gl, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
//        addTextureModel(Table(1001, context, R.drawable.table, Point(0f, 0f, 0f)))

        super.onDrawFrame(gl)

        drawColorModel()
        drawTextureModel()
    }

    override fun drawTextureModel() {
        textureModelMap.forEach { key, value ->
            drawTextureModel(value)
        }
    }

    override fun drawColorModel() {
        colorModelMap.forEach { key, value ->
            drawColorModel(value)
        }
    }

    override fun addTextureModel(model: BaseTextureModel) {
        if (textureModelMap.containsKey(model.id)) {
            moveTextureModel(model)
        } else {
            textureModelMap[model.id] = model
            Log.d("size", "addTextureModel: size:${textureModelMap.size}")
        }

    }

    override fun moveTextureModel(model: BaseTextureModel) {
        if (textureModelMap.containsKey(model.id)) {
            textureModelMap.get(model.id)?.position = model.position
            textureModelMap.get(model.id)?.isTurn = model.isTurn
            textureModelMap.get(model.id)?.turn = model.turn
        } else {
            addTextureModel(model)
        }
    }

    override fun addColorModel(model: BaseColorModel) {
        if (colorModelMap.containsKey(model.id)) {
            moveColorModel(model)
        } else {
            colorModelMap[model.id] = model
            Log.d("size", "addTextureModel: size:${textureModelMap.size}")
        }

    }

    override fun moveColorModel(model: BaseColorModel) {
        if (colorModelMap.containsKey(model.id)) {
            colorModelMap.get(model.id)?.position = model.position
        } else {
            addColorModel(model)
        }
    }

    //边界检测
    override fun clash() {
        TODO("Not yet implemented")
    }

    override fun change(y: Float) {

        moveModel(line0, y)
        moveModel(line1, y)
        moveModel(line2, y)
        moveModel(line3, y)
        moveModel(line4, y)
        moveModel(line5, y)

        moveModel(line6, y)
        moveModel(line7, y)
        moveModel(line8, y)
        moveModel(line9, y)
        moveModel(line10, y)
        moveModel(line11, y)

        moveModel(line12, y)
        moveModel(line13, y)
        moveModel(line14, y)
        moveModel(line15, y)
        moveModel(line16, y)
        moveModel(line17, y)
    }

    fun moveModel(line: Line, y: Float) {
        line.position = Point(line.position.x, line.position.y - y * 0.001f, line.position.z)
        if (line.position.y < -2f) {
            line.position.y = 1f
        }
    }
}