package com.example.airhockey.data

import android.opengl.GLES20.*
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 *@Author QiYuZhen
 *
 */

class VertexArray(
    vertexData: FloatArray
) {
    private val floatBuffer: FloatBuffer = ByteBuffer
        .allocateDirect(vertexData.size * Constants.BYTES_PER_FLOAT)
        .order(ByteOrder.nativeOrder())
        .asFloatBuffer()
        .put(vertexData)

    /**
     * 将[floatBuffer]内的数据作为属性数据传输到OpenGL中
     *  dataOffset 第一个该属性数据的位置
     * attributeLocation 属性的位置id
     *  componentCount 属性包含多少分量
     * stride 读取属性时需要的步长
     */
    fun setVertexAttribPointer(
        dataOffset: Int, attributeLocation: Int,
        componentCount: Int, stride: Int
    ) {
        floatBuffer.position(dataOffset)
        glVertexAttribPointer(
            attributeLocation, componentCount, GL_FLOAT,
            false, stride, floatBuffer
        )
        glEnableVertexAttribArray(attributeLocation)
        // 将偏移量复原
        floatBuffer.position(0)
    }

    companion object {
        private const val BYTES_PER_FLOAT = 4
    }
}
