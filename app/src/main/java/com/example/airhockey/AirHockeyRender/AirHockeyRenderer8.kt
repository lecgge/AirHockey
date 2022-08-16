package com.example.airhockey.AirHockeyRender

/**
 *@Author QiYuZhen
 *
 */


import Point
import Ray
import android.content.Context
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix.*
import android.util.Log
import com.example.airhockey.R
import com.example.airhockey.objects.Car
import com.example.airhockey.objects.Line
import com.example.airhockey.objects.Mallet
import com.example.airhockey.objects.Table
import com.example.airhockey.programs.ColorShaderProgram
import com.example.airhockey.programs.TextureShaderProgram
import com.example.airhockey.util.MatrixHelper
import com.example.airhockey.util.loadTexture
import to
import javax.microedition.khronos.opengles.GL10

/**
 *@Author QiYuZhen
 *
 */

class AirHockeyRenderer8
    (private val context: Context) : GLSurfaceView.Renderer {
    /**
     * 透视投影矩阵
     */
    private val projectionMatrix: FloatArray = FloatArray(16)

    /**
     * 模型矩阵
     */
    private val modelMatrix = FloatArray(16)

    /**
     *    初始化变量
     */
    //添加冰球的类变量
    private lateinit var table: Table
    private lateinit var mallet: Mallet
    private lateinit var line: Line
    private lateinit var car: Car

    private lateinit var carPosition: Point

    private lateinit var textureShaderProgram: TextureShaderProgram
    private lateinit var textureShaderProgram1: TextureShaderProgram
    private lateinit var colorShaderProgram: ColorShaderProgram
    private var texture: Int = 0
    private var carpicture: Int = 0
    //视图矩阵存储在viewMatrix中
    private val viewMatrix = FloatArray(16)
    //用于保存矩阵乘法的结果
    private val viewProjectionMatrix = FloatArray(16)
    //用于保存矩阵乘法的结果
    private val modelViewProjectionMatrix = FloatArray(16)


    /**
     * [viewProjectionMatrix]的逆矩阵
     */
    private val invertedViewProjectionMatrix = FloatArray(16)

    /**
     * 边界
     */
    private val leftBound = -0.5F
    private val rightBound = 0.5F
    private val farBound = -0.8F
    private val nearBound = 0.8F

    //保证value的值不小于min而且不大于max
    private fun clamp(value: Float, min: Float, max: Float): Float {
        /*return when {
        value < min -> {
            min
        }
        value > max -> {
            max
        }
        else -> {
            value
        }
    }*/
        // 这行代码和上面的注释代码的含义一致
        return max.coerceAtMost(value.coerceAtLeast(min))
    }

    //增加速度和方向属性
    private lateinit var previousBlueMalletPosition: Point

    override fun onSurfaceCreated(p0: GL10?, p1: javax.microedition.khronos.egl.EGLConfig?) {
        //设置Clear颜色
        glClearColor(0f, 0f, 0f, 0f)
        table = Table()

        car = Car()

        carPosition = Point(-0.2F, -0.5F, 0F)

        line = Line()

        textureShaderProgram = TextureShaderProgram(context)
        textureShaderProgram1 = TextureShaderProgram(context)
        colorShaderProgram = ColorShaderProgram(context)
        texture = context.loadTexture(R.drawable.table)
        carpicture = context.loadTexture(R.drawable.car)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)//这个方法设置了viewport的大小，这告诉了OpenGL可用于渲染的surface的大小

        // 创建透视投影
        MatrixHelper.perspectivew(
            projectionMatrix,
            45f,
            width.toFloat() / height.toFloat(),
            1f,
            10f
        )

        setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f)

    }

    /**
     * 往屏幕绘制内容
     * 使用纹理来进行绘制
     */
    override fun onDrawFrame(gl: GL10?) {
        //清除之前绘制内容

        glClear(GL_COLOR_BUFFER_BIT)

        //投影矩阵和视图矩阵相乘的结果缓存到viewProjectionMatrix
        multiplyMM(
            viewProjectionMatrix,
            0,
            projectionMatrix,
            0,
            viewMatrix,
            0
        )

        //求4*4矩阵的逆矩阵
        invertM(invertedViewProjectionMatrix, 0, viewProjectionMatrix, 0)//我们可以使用它将二维接触点转换为一对三维坐标

        positionTableInScene()
        textureShaderProgram.useProgram()
        textureShaderProgram.setUniforms(modelViewProjectionMatrix, texture)
        table.bindData(textureShaderProgram)
        table.draw()

        Log.d("TAG", "onDrawFrame: ${carPosition.y}")
        positionObjectInScene(carPosition.x, carPosition.y, carPosition.z)
        textureShaderProgram1.useProgram()
        textureShaderProgram1.setUniforms(modelViewProjectionMatrix, carpicture)
        car.bindData(textureShaderProgram1)
        car.draw()
    }

    private fun positionTableInScene() {
        setIdentityM(modelMatrix, 0)
        // 这张桌子是用X、Y坐标定义的，所以我们把它旋转90度，使它平放在xoz平面上
//        rotateM(modelMatrix, 0, -10f, 1f, 0f, 0f)
        //viewProjectionMatrix和modelMatrix相乘，并将结果存储在modelViewProjectionMatrix中


        multiplyMM(
            modelViewProjectionMatrix, 0, viewProjectionMatrix,
            0, modelMatrix, 0
        )
    }

    private fun positionObjectInScene(x: Float, y: Float, z: Float) {
        setIdentityM(modelMatrix, 0)
        rotateM(modelMatrix, 0, -3f, 0f, 0f, 1f)
        translateM(modelMatrix, 0, x, y, z)
        multiplyMM(
            modelViewProjectionMatrix, 0, viewProjectionMatrix,
            0, modelMatrix, 0
        )
    }


    fun carPositionChange(normalizedX: Float, normlizedY: Float) {

        carPosition = Point(
            normalizedX,
            normlizedY,
            0f
        )


    }

    private fun convertNormalized2DPointToRay(normalizedX: Float, normalizedY: Float): Ray {
        // 先在NDC坐标空间下进行考虑，给出触摸点对应的空间上的两点,NDC就是个正方体,肯定是其中某个点的z坐标为-1，而另一个点的z坐标为1
        val nearPointNdc = floatArrayOf(normalizedX, normalizedY, -1F, 1F)
        val farPointNdc = floatArrayOf(normalizedX, normalizedY, 1F, 1F)
        val nearPointWorld = FloatArray(4)  //近点世界坐标
        val farPointWorld = FloatArray(4)   //远点世界坐标
        // 接下来用逆矩阵对两个点分别撤销变换，得到真实的世界坐标系下的坐标
        multiplyMV(
            nearPointWorld,
            0,
            invertedViewProjectionMatrix,
            0,
            nearPointNdc,
            0 //将每个点与invertedViewProjectionMatrix相乘，得到世界空间中的坐标。
        )
        multiplyMV(
            farPointWorld, 0, invertedViewProjectionMatrix, 0, farPointNdc, 0
        )

        //
        divideByW(nearPointWorld)
        divideByW(farPointWorld)

        val nearPointRay = Point(nearPointWorld[0], nearPointWorld[1], nearPointWorld[2])
        val farPointRay = Point(farPointWorld[0], farPointWorld[1], farPointWorld[2])
        return Ray(
            nearPointRay,
            nearPointRay to farPointRay
        )

    }

    private fun divideByW(vector: FloatArray) {
        vector[0] /= vector[3]
        vector[1] /= vector[3]
        vector[2] /= vector[3]
    }


}