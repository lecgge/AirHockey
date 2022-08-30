package com.example.airhockey.base

import Point
import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.Log
import androidx.annotation.DrawableRes
import com.example.airhockey.data.Constants
import com.example.airhockey.data.VertexArray
import com.example.airhockey.programs.TextureShaderProgram
import com.example.airhockey.util.loadTexture

abstract class BaseTextureModel(
    val id: Int,
    val context: Context,
    @DrawableRes
    val resId: Int,
    var position: Point,
    var isTurn:Boolean,
    var turn:Float
) : ITextureModel{

    val vertexArray: VertexArray = VertexArray(vertexData)
    var texture: Int = 0
    init {
        texture = context.loadTexture(resId)
    }
    companion object {
        var POSITION_COMPONENT_COUNT = 2
        var TEXTURE_COORDINATES_COMPONENT_COUNT = 2
        var STRIDE: Int = (POSITION_COMPONENT_COUNT
                + TEXTURE_COORDINATES_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT

    }

    override fun bindData(textureShaderProgram: TextureShaderProgram) {
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

    override fun draw(drawShaper: Int, first: Int, count: Int) {
        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)
        GLES20.glDrawArrays(drawShaper, first, count)
    }


}