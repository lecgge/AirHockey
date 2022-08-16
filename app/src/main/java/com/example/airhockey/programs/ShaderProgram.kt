package com.example.airhockey.programs

import android.content.Context
import android.opengl.GLES20.*
import com.example.airhockey.ShaderHelper
import com.example.airhockey.util.readStringFromRaw

/**
 *@Author QiYuZhen
 *
 */
abstract class ShaderProgram(context : Context,
                             vertexShaderResId :Int,
                             fragmentShaderResId:Int

) {

    protected val programId:Int


    init {
        with(context){
            programId=ShaderHelper.buildProgram(
                readStringFromRaw(vertexShaderResId),
                readStringFromRaw(fragmentShaderResId)
            )
        }
    }
    fun getAttribLocation(name:String):Int=glGetAttribLocation(programId,name)// glGetAttribLocation返回属性变量的位置.programId指定要查询的程序对象。name要查询其位置的属性变量的名称
    fun getUniformLocation(name:String):Int= glGetUniformLocation(programId,name)//glGetUniformLocation返回一个整数，表示程序对象中特定统一变量的位置。 name必须是不包含空格的空终止字符串。 name必须是程序中的活动统一变量名，它不能是结构，也不能是结构数组或向量或矩阵的子组件。
     fun useProgram(){

      glUseProgram(programId) //glUseProgram使用程序对象program作为当前渲染状态的一部分
  }

}