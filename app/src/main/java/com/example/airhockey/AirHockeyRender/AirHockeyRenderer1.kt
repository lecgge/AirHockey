package com.example.airhockey.AirHockeyRender

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import com.example.airhockey.R
import com.example.airhockey.ShaderHelper
import com.example.airhockey.util.isDebugVersion
import com.example.airhockey.util.readStringFromRaw
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 *@Author QiYuZhen
 *@Date  12:24
 */
class AirHockeyRenderer1(private val context: Context) : GLSurfaceView.Renderer {
    /**
     * 缓存u_Color变量的·位置
     *
     */
    private var uColorLocation = 0
    private var programId = 0 //添加一个类变量来缓存程序id
    private var aPositionLocation = 0
    companion object {  //伴生类

        private const val POSITION_COMPONENT_COUNT = 2//
        private const val BYTES_PER_FLOAT = 4
        private const val U_COLOR = "u_Color"
        private const val A_POSITION = "a_Position"
    }
    //添加一个属性来存储我们得到的坐标
    private val tableVerticesWithTriangles: FloatArray = floatArrayOf(//顶点属性数组  可以存储带有小数点的位置
        -0.5F, -0.5F,
        0.5F, 0.5F,
        -0.5F, 0.5F,

        -0.5F, -0.5F,
        0.5F, -0.5F,
        0.5F, 0.5F,
        // 中线
        -0.5F, 0F,
        0.5F, 0F,
        // 顶点
        0F, -0.25F,
        0F, 0.25F


        )
    //将数据复制到本机内存
    // allocateDirect()不使用JVM堆栈而是通过操作系统来创建内存块用作缓冲区，它与当前操作系统能够更好的耦合，因此能进一步提高I/O操作速度。但是分配直接缓冲区的系统开销很大，因此只有在缓冲区较大并长期存在，或者需要经常重用时，才使用这种缓冲区
    private val vertexData: FloatBuffer = ByteBuffer//创建了一个名为vertexData的缓冲区,FloatBuffer将用于在native内存中存储数据
        .allocateDirect(tableVerticesWithTriangles.size * BYTES_PER_FLOAT)//分配了一块本机内存
        .order(ByteOrder.nativeOrder())//使用与平台相同的顺序
        .asFloatBuffer()//获得一个反映底层字节的FloatBuffer
        .put(tableVerticesWithTriangles)//将数据从Dalvik的内存复制到native内存


    override fun onSurfaceCreated(gl: GL10?, confing: EGLConfig?) {
        //  GLES20.glClearColor(1F,0F,0F,1F)//参数依次是red、green、blue、alpha,我们将红色设置为最大强度，屏幕将在被清除时变为红色（the screen will become red when cleared）（因为clearColor就是clear事件发生之后应当显示的颜色
        //设置ClearColor
        glClearColor(0f, 0f, 0f, 0f)
        //读取着色器代码
        val vertexShaderCode = context.readStringFromRaw(R.raw.simple_vertex_shader)
        val framentShaderCode = context.readStringFromRaw(R.raw.simple_fragment_shader)
        // 创建着色器，这里验证为0的情况
        val vertexShader = ShaderHelper.compileVertextShader(vertexShaderCode)
        val fragmentShader = ShaderHelper.compileFragmentShader(framentShaderCode)

        programId = ShaderHelper.linkProgram(vertexShader, fragmentShader)

        //缓存a_Position的位置
        aPositionLocation = glGetAttribLocation(programId, A_POSITION)
      //  判断是否为debug版本
        if (context.isDebugVersion()) {
            ShaderHelper.validateProgram(programId)
        }
        // 获取Uniform的位置
        uColorLocation = glGetUniformLocation(programId, U_COLOR)//获取位置
        glUseProgram(programId)//启用创建的OpenGL程序
        //将顶点数据数组与Attribute关联将顶点数据数组与Attribute关联
        vertexData.position(0)
        glVertexAttribPointer(
            aPositionLocation,
            POSITION_COMPONENT_COUNT,
            GL_FLOAT,
            false,
            0,
            vertexData
        )//

        glEnableVertexAttribArray(aPositionLocation)//启用顶点数组
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)//这个方法设置了viewport的大小，这告诉了OpenGL可用于渲染的surface的大小
    }
   /**
    * 往屏幕绘制内容
    */
    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)//我们调用glClear(GL_COLOR_BUFFER_BIT)擦除了屏幕上的所有颜色，并使用我们之前调用glClearColor()来定义的颜色填充屏幕。
        //画桌子
        glUniform4f(uColorLocation, 1F, 1F, 1F, 1F)//更新着色器代码中u_Color的值
        glDrawArrays(GL_TRIANGLES, 0, 6)//绘制桌子,第一个参数告诉OpenGL想要画三角形,第二个参数告诉OpenGL从顶点数组的开头开始读取顶点（offset）,第三个参数告诉OpenGL读取六个顶点
       //绘制分界线
        glUniform4f(uColorLocation,1F,1F,0F,1F)//u_Color修改为红色，然后要求OpenGL绘制线条
        glDrawArrays(GL_LINES,6,2)//需要从第七个顶点开始绘制，一共需要绘制两个顶点,0,1,2,3,4,5,6
       //把木槌画成点
       glUniform4f(uColorLocation,0F,0F,1F,1F)
       glDrawArrays(GL_POINTS,8,1)

       glUniform4f(uColorLocation,1F,0F,0F,1F)
       glDrawArrays(GL_POINTS,9,1)

    }




}


