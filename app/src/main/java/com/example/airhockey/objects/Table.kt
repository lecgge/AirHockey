package com.example.airhockey.objects

import android.opengl.GLES20.GL_TRIANGLE_FAN
import android.opengl.GLES20.glDrawArrays
import com.example.airhockey.data.Constants
import com.example.airhockey.data.VertexArray
import com.example.airhockey.programs.TextureShaderProgram

/**
 *@Author QiYuZhen
 *
 * 添加桌子数据
 */
class Table {
    private val vertexArray:VertexArray = VertexArray(vertexData)
    companion object {  //定义了位置组件计数、纹理组件计数和步幅
        private const val POSITION_COMPONENT_COUNT = 2
        private const val TEXTURE_COORDINATES_COMPONENT_COUNT = 2
        private const val STRIDE: Int =
            (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT

        //添加顶点数据
        private val vertexData: FloatArray= floatArrayOf(
            //数据顺序：x,y,s,t
        //三角形扇形
            0f, 0f, 0.5f, 0.5f,
            -0.5f, -0.8f, 0f, 0.9f,
            0.5f, -0.8f, 1f, 0.9f,
            0.5f, 0.8f, 1f, 0.1f,
            -0.5f, 0.8f, 0f, 0.1f,
            -0.5f, -0.8f, 0f, 0.9f

        )
    }
    fun bindData(textureShaderProgram: TextureShaderProgram){
        //获取程序对象的变量位置
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
    fun draw(){
        glDrawArrays(GL_TRIANGLE_FAN,0,6)
    }

}