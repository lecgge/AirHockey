package com.example.airhockey.AirHockeyRender

import Plane
import Point
import Ray
import Sphere
import Vector
import android.content.Context
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix.*
import androidx.compose.ui.graphics.Color
import com.example.airhockey.R
import com.example.airhockey.objects.Mallet
import com.example.airhockey.objects.Puck
import com.example.airhockey.objects.Table
import com.example.airhockey.programs.ColorShaderProgram
import com.example.airhockey.programs.TextureShaderProgram
import com.example.airhockey.util.MatrixHelper
import com.example.airhockey.util.loadTexture
import intersectionPoint
import intersects
import to
import javax.microedition.khronos.opengles.GL10

/**
 *@Author QiYuZhen
 *
 */

class AirHockeyRenderer6

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
    private lateinit var puck: Puck

    private lateinit var textureShaderProgram: TextureShaderProgram
    private lateinit var colorShaderProgram: ColorShaderProgram
    private var texture: Int = 0

    private val viewMatrix = FloatArray(16)  //视图矩阵存储在viewMatrix中
    private val viewProjectionMatrix = FloatArray(16)
    private val modelViewProjectionMatrix = FloatArray(16)//用于保存矩阵乘法的结果

    private var malletPressed=false//malletPressed来跟踪木槌当前是否已被按到
    private lateinit var blueMalletPosition:Point   //将木槌的位置存储在blueMalletPosition

    /**
     * [viewProjectionMatrix]的逆矩阵
     */
    private val invertedViewProjectionMatrix=FloatArray(16)

    /**
     * 边界
     */
    private val leftBound=-0.5F
    private val rightBound=0.5F
    private val farBound=-0.8F
    private val nearBound=0.8F
    //保证value的值不小于min而且不大于max
    private fun clamp(value:Float,min:Float,max:Float): Float{
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
    companion object {  //伴生类
        private const val POSITION_COMPONENT_COUNT = 4//每个顶点有4个分量，x,y,z,w
        private const val BYTES_PER_FLOAT = 4 //Java中的浮点有32位，而一个字节有8位。也就是说每个浮点占用4个字节

        private const val COLOR_COMPONENT_COUNT = 3
        private const val STRIDE =
            (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT  //使用STRIDE来告诉OpenGL每个位置数据之间有多少字节，以便它知道需要跳过多远
    }


    override fun onSurfaceCreated(p0: GL10?, p1: javax.microedition.khronos.egl.EGLConfig?) {
        //设置Clear颜色
        glClearColor(0f,0f,0f,0f)
        table= Table()
        mallet=Mallet(0.08F,0.15F,32)
        puck=Puck(0.06f,0.02f,32)
        blueMalletPosition = Point(0F, mallet.height / 2F, 0.4F)

        textureShaderProgram= TextureShaderProgram(context)
        colorShaderProgram= ColorShaderProgram(context)
        texture=context.loadTexture(R.drawable.table)

    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
       glViewport(0, 0, width, height)//这个方法设置了viewport的大小，这告诉了OpenGL可用于渲染的surface的大小

        // 创建透视投影
        MatrixHelper.perspectivew(
            projectionMatrix,
            55f,
            width.toFloat() / height.toFloat(),
            1f,
            10f
        )


        setLookAtM(viewMatrix,0,0f,1.2f,2.2f,0f,0f,0f,0f,1f,0f)

    }

    /**
     * 往屏幕绘制内容
     * 使用纹理来进行绘制
     */
    override fun onDrawFrame(gl: GL10?) {
        //清除之前绘制内容
        glClear(GL_COLOR_BUFFER_BIT)
        multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0)//投影矩阵和视图矩阵相乘的结果缓存到viewProjectionMatrix

        //求4*4矩阵的逆矩阵
        invertM(invertedViewProjectionMatrix,0,viewProjectionMatrix,0)//我们可以使用它将二维接触点转换为一对三维坐标

        positionTableInScene()
        textureShaderProgram.useProgram()
        textureShaderProgram.setUniforms(modelViewProjectionMatrix, texture)
        table.bindData(textureShaderProgram)
        table.draw()
       // 绘制Mallet
        positionObjectInScene(0F, mallet.height / 2F, -0.4F)
        colorShaderProgram.useProgram()
        colorShaderProgram.setUniforms(modelViewProjectionMatrix, Color(1f, 0f, 0f))
        mallet.bindData(colorShaderProgram)
        mallet.draw()

       // positionObjectInScene(0F, mallet.height / 2F, 0.4F)
        positionObjectInScene(blueMalletPosition.x, blueMalletPosition.y,
            blueMalletPosition.z)

        colorShaderProgram.setUniforms(modelViewProjectionMatrix, Color(0f, 0f, 1f))
      // 请注意，我们不必两次定义对象数据，我们只要指定不同的位置，使用不同的颜色。
        mallet.draw()
      // 绘制Puck
        positionObjectInScene(0F, puck.height / 2F, 0F)
        colorShaderProgram.setUniforms(modelViewProjectionMatrix, Color(0.8f, 0.8f, 1f))
        puck.bindData(colorShaderProgram)
        puck.draw()

    }
    private fun positionTableInScene() {
        setIdentityM(modelMatrix, 0)
        // 这张桌子是用X、Y坐标定义的，所以我们把它旋转90度，使它平放在xoz平面上
        rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f)
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix,
            0, modelMatrix, 0)//viewProjectionMatrix和modelMatrix相乘，并将结果存储在modelViewProjectionMatrix中
    }
    private fun positionObjectInScene(x: Float, y: Float, z: Float) {
        setIdentityM(modelMatrix, 0)
        translateM(modelMatrix, 0, x, y, z)
        multiplyMM(
            modelViewProjectionMatrix, 0, viewProjectionMatrix,
            0, modelMatrix, 0
        )
    }

    fun handleTouchPress(normalizedX: Float, normlizedY: Float) {
        // 计算出触摸点在三维世界中形成的线
        val ray:Ray = convertNormalized2DPointToRay(normalizedX,normlizedY)
        //创建木槌的包围球
        val malletBoundingSphere = Sphere(
            Point(
                blueMalletPosition.x,
                blueMalletPosition.y,
                blueMalletPosition.z

            ),
            mallet.height/2F
        )
        // 判定是否触摸到木槌，设置malletPressed的值
        malletPressed=malletBoundingSphere.intersects(ray)

    }

    fun handleTouchDrag(normalizedX: Float, normlizedY: Float) {
        if (malletPressed){
            val ray=convertNormalized2DPointToRay(normalizedX,normlizedY)
            //定义一个平面以表示我们的桌子
            val plane = Plane(Point(0F, 0F, 0F), Vector(0F, 1F, 0F))
            //找出接触点与平面相交的位置，我们将沿着这个平面移动木zhui
            var touchedPoint:Point=plane.intersectionPoint(ray)
            blueMalletPosition=Point(touchedPoint.x,mallet.height/2f,touchedPoint.z)

        }

    }
    private fun convertNormalized2DPointToRay(normalizedX: Float, normalizedY: Float): Ray {
        // 先在NDC坐标空间下进行考虑，给出触摸点对应的空间上的两点,NDC就是个正方体,肯定是其中某个点的z坐标为-1，而另一个点的z坐标为1
        val nearPointNdc = floatArrayOf(normalizedX, normalizedY, -1F, 1F)
        val farPointNdc = floatArrayOf(normalizedX, normalizedY, 1F, 1F)
        val nearPointWorld = FloatArray(4)  //近点世界坐标
        val farPointWorld = FloatArray(4)   //远点世界坐标
        // 接下来用逆矩阵对两个点分别撤销变换，得到真实的世界坐标系下的坐标
        multiplyMV(
            nearPointWorld, 0, invertedViewProjectionMatrix, 0, nearPointNdc, 0 //将每个点与invertedViewProjectionMatrix相乘，得到世界空间中的坐标。
        )
        multiplyMV(
            farPointWorld, 0, invertedViewProjectionMatrix, 0, farPointNdc, 0
        )

        //
        divideByW(nearPointWorld)
        divideByW(farPointWorld)

        val nearPointRay= Point(nearPointWorld[0],nearPointWorld[1],nearPointWorld[2])
        val farPointRay= Point(farPointWorld[0],farPointWorld[1],farPointWorld[2])
        return Ray(
            nearPointRay,
            nearPointRay to farPointRay
        )

    }
    private fun divideByW(vector: FloatArray){
        vector[0] /=vector[3]
        vector[1] /=vector[3]
        vector[2] /=vector[3]
    }



}