package com.example.airhockey.objects

import Point
import android.content.Context
import android.opengl.GLES20.GL_TRIANGLE_FAN
import android.opengl.GLES20.glDrawArrays
import com.example.airhockey.base.BaseTextureModel
import com.example.airhockey.base.ModelDrawInfo
import com.example.airhockey.data.Constants
import com.example.airhockey.data.VertexArray
import com.example.airhockey.programs.TextureShaderProgram

/**
 *@Author QiYuZhen
 *
 * 添加桌子数据
 */
class Table(
    id: Int,
    context: Context,
    resId: Int,
    position: Point,
    isPerspective: Boolean = true
) : BaseTextureModel(id, context, resId, position, isPerspective) {


    override fun draw(){
        glDrawArrays(GL_TRIANGLE_FAN,0,6)
    }



    override fun setVertexData(): FloatArray {
        return floatArrayOf(
            // 数据顺序: X, Y, S, T
            // 三角形扇形
            0f, 0f, 0.5f, 0.5f,
            -0.5f, -0.8f, 0f, 0.9f,
            0.5f, -0.8f, 1f, 0.9f,
            0.5f, 0.8f, 1f, 0.1f,
            -0.5f, 0.8f, 0f, 0.1f,
            -0.5f, -0.8f, 0f, 0.9f
        )
    }

    override fun setDrawInfo(): ModelDrawInfo {
        return object : ModelDrawInfo{
            override val shaper: Int
                get() = GL_TRIANGLE_FAN
            override val first: Int
                get() = 0
            override val count: Int
                get() = 6
        }
    }

}