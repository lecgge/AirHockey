package com.example.airhockey.objects

import Point
import android.content.Context
import android.opengl.GLES20.*
import android.opengl.Matrix.rotateM
import android.opengl.Matrix.setIdentityM
import androidx.annotation.DrawableRes
import com.example.airhockey.R
import com.example.airhockey.data.Constants.BYTES_PER_FLOAT
import com.example.airhockey.data.VertexArray
import com.example.airhockey.programs.TextureShaderProgram
import com.example.airhockey.util.loadTexture

class Car(
    val id : Int,
    val context: Context,
    @DrawableRes val resId: Int,
    var position: Point
) {

    private val vertexArray: VertexArray = VertexArray(vertexData)

    var texture: Int = 0
    init {
        texture = context.loadTexture(resId)
    }

    companion object {
        private const val POSITION_COMPONENT_COUNT = 2
        private const val TEXTURE_COORDINATES_COMPONENT_COUNT = 2
        private const val STRIDE: Int = (POSITION_COMPONENT_COUNT
                + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT

        private val vertexData: FloatArray = floatArrayOf(
            // 数据顺序: X, Y, S, T
            // 三角形扇形
            0f, 0f, 0.5f, 0.5f,
            -0.1f, -0.25f, 0f, 0.9f,
            0.1f, -0.25f, 1f, 0.9f,
            0.1f, 0.25f, 1f, 0.1f,
            -0.1f, 0.25f, 0f, 0.1f,
            -0.1f, -0.25f, 0f, 0.9f
        )
    }

    fun bindData(textureShaderProgram: TextureShaderProgram) {
        // 获取程序对象的变量的位置，并绑定vertexArray包含的数据
        vertexArray.setVertexAttribPointer(
            dataOffset = 0,
            attributeLocation = textureShaderProgram.aPositionLocation,
            componentCount = POSITION_COMPONENT_COUNT,
            stride = STRIDE
        )
        vertexArray.setVertexAttribPointer(
            dataOffset = POSITION_COMPONENT_COUNT,
            attributeLocation = textureShaderProgram.aTextureCoordinatesLocation,
            componentCount = TEXTURE_COORDINATES_COMPONENT_COUNT,
            stride = STRIDE
        )
    }

    fun draw() {
        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6)
    }
}
