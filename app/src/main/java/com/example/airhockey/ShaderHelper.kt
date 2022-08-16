package com.example.airhockey

import android.opengl.GLES20.*
import com.example.airhockey.util.LogU


/**
 *@Author QiYuZhen
 *
 *
 *
 * 创建一个新的OpenGL着色器对象，编译着色器代码，并返回该着色器代码的着色器对象
 */
object ShaderHelper {
    private const val tag = "ShaderHelper"

    fun compileVertextShader(shaderCode: String) =
        compileShader(GL_VERTEX_SHADER, shaderCode)//GL_VERTEX_SHADER等常量位于android.opengl.GLES20包内

    fun compileFragmentShader(shaderCode: String) = compileShader(
        GL_FRAGMENT_SHADER,
        shaderCode
    )//创建的着色器的类型,只能是GL_VERTEX_SHADER或GL_FRAGMENT_SHADER

    //创建新的着色器对象
    fun compileShader(type: Int, shaderCode: String): Int {
        val shaderObjectId =
            glCreateShader(type)//调用GLES20中的静态方法glCreateShader()创建一个新的着色器对象，并将该对象的ID存储在shaderObjectId
        if (type == 0) {
            LogU.w(tag = tag, message = "Could not create new shader")
            return 0  //返回值0表示对象创建失败
        }
        //传输并编译代码
        glShaderSource(shaderObjectId, shaderCode)//将着色器源代码上传到着色器对象中,上传源代码
        glCompileShader(shaderObjectId)    //来编译着色器
        // 检索编译状态，获得代码编译结果
        val compileStatus: IntArray = IntArray(1)
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0)
        //获取着色器信息日志
        LogU.v(
            tag = tag, message = "Results of compiling source:" +
                    "\n $shaderCode\n:${glGetShaderInfoLog(shaderObjectId)}"
        )
        if (compileStatus[0] == 0) {
            //编译失败，删除着色
            glDeleteShader(shaderObjectId)
            LogU.w(tag, "Compilation of shader failed.")
            return 0
        }
        return shaderObjectId

    }

    //将着色器链接到OpenGL程序中
    fun linkProgram(vertexShaderId: Int, fragmentShader: Int): Int {
        val programObjectId = glCreateProgram()//创建一个新的程序对象
        if (programObjectId == 0) {
            LogU.w(tag, "Could not create new program")
            return 0
        }
        //glAttachShader()把顶点着色器和片元着色器附加到程序对象
        glAttachShader(programObjectId, vertexShaderId)
        glAttachShader(programObjectId, fragmentShader)
        //链接程序
        glLinkProgram(programObjectId)
        val linkStatus = IntArray(1)//创建一个新的int数组来保存结果
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0)//将结果存储在这个数组中
        LogU.v(
            tag,
            "Results of linking program:\n${glGetProgramInfoLog(programObjectId)}"
        )//检查程序信息日志
        if (linkStatus[0] == 0) {//验证链接状态并返回程序对象ID
            glDeleteProgram(programObjectId)
            LogU.w(tag, "Linking of program failed.")
            return 0
        }
        return programObjectId
    }

    //验证我们的OpenGL程序
    fun validateProgram(programObjectId: Int): Boolean {
        //验证这个程序
        glValidateProgram(programObjectId)
        //获取验证状态
        val validateStatus = IntArray(1)
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0)
        LogU.v(
            tag, "Results of validating program: " + validateStatus[0]
                    + "\nLog:" + glGetProgramInfoLog(programObjectId)
        )
        return validateStatus[0] != 0
    }

    //为纹理着色器程序创建一个类
    //编译vertexShaderSource和fragmentShaderSource定义的着色器，并将它们链接到一个程序中，它还将验证程序
    fun buildProgram(vertextShaderSource: String, fragmentShaderSource: String): Int {
        val vertexShaderId = compileVertextShader(vertextShaderSource)
        val fragmentShaderId = compileFragmentShader(fragmentShaderSource)
        val program = linkProgram(vertexShaderId, fragmentShaderId)
        validateProgram(program)
        return program

    }


}
