package com.example.airhockey.programs

import android.content.Context

import android.opengl.GLES20.glUniform4f
import android.opengl.GLES20.glUniformMatrix4fv
import androidx.compose.ui.graphics.Color

import com.example.airhockey.R

/**
 *@Author QiYuZhen
 *
 * 这个程序来绘制木槌。通过将着色器程序与使用这些程序绘制的数据分离，我们可以更容易地重用代码，我们可以使用颜色着色器程序绘制任何具有颜色属性的对象。
 */


class ColorShaderProgram(
    context: Context
): ShaderProgram(context, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader) {

    private val uMatrixLocation: Int = getUniformLocation(U_MATRIX)
    private val uColorLocation: Int = getUniformLocation(U_COLOR)

    val aPositionLocation: Int = getAttribLocation(A_POSITION)

    fun setUniforms(matrix: FloatArray, color: Color) {
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
        glUniform4f(uColorLocation, color.red, color.green, color.blue, color.alpha)
    }

    companion object {
        private const val U_MATRIX = "u_Matrix"
        private const val U_COLOR = "u_Color"

        private const val A_POSITION = "a_Position"
    }
}

