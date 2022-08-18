package com.example.airhockey.AirHockeyRender

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.example.airhockey.R
import com.example.airhockey.objects.Mallet
import com.example.airhockey.objects.Table
import com.example.airhockey.programs.ColorShaderProgram
import com.example.airhockey.programs.TextureShaderProgram
import com.example.airhockey.util.MatrixHelper
import com.example.airhockey.util.loadTexture
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class AirHockeyRenderer5(private val context: Context) : GLSurfaceView.Renderer {
    /**
     * 透视投影矩阵
     */
    private val projectionMatrix: FloatArray = FloatArray(16)

    /**
     * 模型矩阵
     */
    private val modelMatrix = FloatArray(16)
    private lateinit var table: Table
    private lateinit var mallet: Mallet
    private lateinit var textureShaderProgram: TextureShaderProgram
    private lateinit var colorShaderProgram: ColorShaderProgram
    private var texture: Int = 0





    companion object {  //伴生类
        private const val POSITION_COMPONENT_COUNT = 4//每个顶点有4个分量，x,y,z,w
        private const val BYTES_PER_FLOAT = 4 //Java中的浮点有32位，而一个字节有8位。也就是说每个浮点占用4个字节

        private const val COLOR_COMPONENT_COUNT = 3
        private const val STRIDE =
            (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT  //使用STRIDE来告诉OpenGL每个位置数据之间有多少字节，以便它知道需要跳过多远
    }
    /**
     *    初始化变量
     */

    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
    //设置Clear颜色
        glClearColor(0f,0f,0f,0f)
//        table= Table()
        mallet= Mallet(0.08F, 0.15F, 32)
        textureShaderProgram= TextureShaderProgram(context)
        colorShaderProgram= ColorShaderProgram(context)
        texture=context.loadTexture(R.drawable.table)

    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)//这个方法设置了viewport的大小，这告诉了OpenGL可用于渲染的surface的大小

        // 创建透视投影
        MatrixHelper.perspectivew(
            projectionMatrix,
            55f,
            width.toFloat() / height.toFloat(),
            1f,
            10f
        )
        //调整模型矩阵
        // 定义模型矩阵
        Matrix.setIdentityM(modelMatrix, 0)
        //在上面基础上,在Z轴平移-2.5个单位
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -2.5f)
        Matrix.rotateM(modelMatrix, 0, -50f, 1f, 0f, 0f)

        val temp = FloatArray(16)
        //矩阵相乘
        Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0)
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.size)

    }

    /**
     * 往屏幕绘制内容
     * 使用纹理来进行绘制
     */
    override fun onDrawFrame(gl: GL10?) {
      //清除之前绘制内容
        glClear(GL_COLOR_BUFFER_BIT)
        //使用该程序
        textureShaderProgram.useProgram()
        //赋值Uniform
        textureShaderProgram.setUniforms(matrix = projectionMatrix, textureId = texture)
        //赋值Attribute
        table.bindData(textureShaderProgram)
        //绘制
        table.draw()
        colorShaderProgram.useProgram()
       // colorShaderProgram.setUniforms(matrix = projectionMatrix)
        mallet.bindData(colorShaderProgram)
        mallet.draw()
    }


}