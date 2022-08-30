package com.example.airhockey.base

import Point
import Ray
import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.GL_ONE
import android.opengl.GLES20.GL_ONE_MINUS_DST_ALPHA
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.opengl.Matrix.rotateM
import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.airhockey.App.Companion.context
import com.example.airhockey.programs.ColorShaderProgram
import com.example.airhockey.programs.TextureShaderProgram
import com.example.airhockey.util.loadTexture
import to
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

abstract class BaseRenderer(private val context: Context,var isPerspective:Boolean) : GLSurfaceView.Renderer, ModelMap,IRenderer {
    /**
     * 透视投影矩阵
     */
    val projectionMatrix: FloatArray = FloatArray(16)

    /**
     * 正交投影矩阵
     */
    val translationMatrix: FloatArray = FloatArray(16)

    /**
     * 纹理模型矩阵
     */
    val modelMatrix = FloatArray(16)

    /**
     * 纹理模型视图矩阵
     */
    val viewMatrix = FloatArray(16)

    /**
     * 视图矩阵与透视投影矩阵或者正交投影矩阵相乘后的结果矩阵，简称投影视图矩阵
     */
    val viewProjectionMatrix = FloatArray(16)

    /**
     * 模型矩阵与投影视图矩阵相乘后的结果矩阵
     */
    var modelViewProjectionMatrix = FloatArray(16)

    /**
     * [viewProjectionMatrix]的逆矩阵
     */
    val invertedViewProjectionMatrix = FloatArray(16)

    //纹理着色器程序
    lateinit var textureShaderProgram: TextureShaderProgram

    //非纹理着色器程序
    lateinit var colorShaderProgram: ColorShaderProgram

    //颜色着色器程序-颜色属性
    var color:Color = Color(1f,1f,1f)

    //页面刷新颜色
    var clearColor = Color(0f,0f,0f,0f)

    /**
     * 边界
     */
    private val leftBound = -0.5F
    private val rightBound = 0.5F
    private val farBound = -0.8F
    private val nearBound = 0.8F

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        //设置Clear颜色
        GLES20.glClearColor(clearColor.red,clearColor.green,clearColor.blue,clearColor.alpha)
        textureShaderProgram = TextureShaderProgram(context)
        colorShaderProgram = ColorShaderProgram(context)

        //为每个纹理模型加载纹理数据
        textureModelMap.forEach { key,value ->
            value.texture = context.loadTexture(value.texture)
        }
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)//这个方法设置了viewport的大小，这告诉了OpenGL可用于渲染的surface的大小

        //计算透视投影矩阵
        Matrix.perspectiveM(
            projectionMatrix,
            0,
            45f,
            width.toFloat() / height.toFloat(),
            1f,
            10f
        )

        //计算正交投影矩阵
        val isLandscape = width > height
        val aspectRatio = if (isLandscape) (width.toFloat()) / (height.toFloat()) else
            (height.toFloat()) / (width.toFloat())
        if (isLandscape) {
            Matrix.orthoM(translationMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f)
        } else {
            // 竖屏或正方形屏幕
            Matrix.orthoM(translationMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f)
        }

        //设置相机位置
        Matrix.setLookAtM(
            viewMatrix,
            0,
            0f,
            1.2f,
            2.2f,
            0f,
            0f,
            0f,
            0f,
            1f,
            0f
        )
    }

    override fun onDrawFrame(gl: GL10?) {
        //清除之前绘制内容
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        if (isPerspective) {
            //投影矩阵和视图矩阵相乘的结果缓存到viewProjectionMatrix
            Matrix.multiplyMM(
                viewProjectionMatrix,
                0,
                projectionMatrix,
                0,
                viewMatrix,
                0
            )
        } else {
            Matrix.multiplyMM(
                viewProjectionMatrix,
                0,
                translationMatrix,
                0,
                viewMatrix,
                0
            )
        }
        //绘制颜色模型
        drawColorModel()
        //绘制纹理模型
        drawTextureModel()
    }

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

    //绘制纹理模型
    fun drawTextureModel(model: BaseTextureModel) {
        //设置模型在屏幕中的显示效果
        positionObjectInScene(model)
        //使用纹理哦着色器程序
        textureShaderProgram.useProgram()
        //将模型视图矩阵与纹理传给着色器程序
        textureShaderProgram.setUniforms(modelViewProjectionMatrix, model.texture)
        //绑定纹理着色器程序
        model.bindData(textureShaderProgram)
        //绘制纹理模型
        model.draw(model.drawInfo.shaper, model.drawInfo.first, model.drawInfo.count)
    }

    fun drawColorModel(model: BaseColorModel) {
        //设置模型在屏幕中的显示效果
        positionObjectInScene(model)
        //使用颜色着色器程序
        colorShaderProgram.useProgram()
        //将模型视图矩阵与颜色数据传给着色器程序
        colorShaderProgram.setUniforms(modelViewProjectionMatrix, color)
        //绑定颜色着色器程序
        model.bindData(colorShaderProgram = colorShaderProgram)
        //绘制纹理模型
        model.draw()
    }

    //对纹理的模型矩阵和视图矩阵进行计算
    fun positionObjectInScene(model: BaseTextureModel) {
        Matrix.setIdentityM(modelMatrix, 0)
        //纹理模型旋转判断，绕Z轴旋转
        if (model.isTurn) {
            rotateM(modelMatrix, 0, model.turn, 0f, 0f, 1f)
        }
        //当使用透视投影矩阵时，我们应该使用旋转矩阵将在x0y平面的图像绕x轴旋转
        if (isPerspective) {
            rotateM(modelMatrix,0,-45f,1f,0f,0f)
        }
        //计算模型的位置矩阵
        Matrix.translateM(modelMatrix, 0, model.position.x, model.position.y, model.position.z)
        //计算模型最终的视图矩阵
        Matrix.multiplyMM(
            modelViewProjectionMatrix, 0, viewProjectionMatrix,
            0, modelMatrix, 0
        )
    }

    //对非纹理的模型矩阵和视图矩阵进行计算
    fun positionObjectInScene(model: BaseColorModel) {
        Matrix.setIdentityM(modelMatrix, 0)
        //当使用透视投影矩阵时，我们应该使用旋转矩阵将在x0y平面的图像绕x轴旋转
        if (isPerspective) {
            rotateM(modelMatrix,0,-45f,1f,0f,0f)
        }
        Matrix.translateM(modelMatrix, 0, model.position.x, model.position.y, model.position.z)

        Matrix.multiplyMM(
            modelViewProjectionMatrix, 0, viewProjectionMatrix,
            0, modelMatrix, 0
        )
    }

    private fun convertNormalized2DPointToRay(normalizedX: Float, normalizedY: Float): Ray {
        // 先在NDC坐标空间下进行考虑，给出触摸点对应的空间上的两点,NDC就是个正方体,肯定是其中某个点的z坐标为-1，而另一个点的z坐标为1
        val nearPointNdc = floatArrayOf(normalizedX, normalizedY, -1F, 1F)
        val farPointNdc = floatArrayOf(normalizedX, normalizedY, 1F, 1F)
        val nearPointWorld = FloatArray(4)  //近点世界坐标
        val farPointWorld = FloatArray(4)   //远点世界坐标
        // 接下来用逆矩阵对两个点分别撤销变换，得到真实的世界坐标系下的坐标
        Matrix.multiplyMV(
            nearPointWorld,
            0,
            invertedViewProjectionMatrix,
            0,
            nearPointNdc,
            0 //将每个点与invertedViewProjectionMatrix相乘，得到世界空间中的坐标。
        )
        Matrix.multiplyMV(
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

    abstract fun change(y: Float)
}