package com.example.airhockey.base

import com.example.airhockey.programs.TextureShaderProgram

interface ITextureModel {
    /**
     * ModelDrawInfo接口对象，存储了模型绘制成图形的信息。
     * val shaper: Int
     * val first: Int
     * val count: Int
     */
    val drawInfo: ModelDrawInfo get() = setDrawInfo()

    fun setDrawInfo(): ModelDrawInfo

    /**
     * 顶点数据，设置模型各顶点具体位置（模型形状及大小）
     */
    val vertexData: FloatArray get() = setVertexData()

    /**
     * 设置顶点数据，设置模型各顶点具体位置（模型形状及大小）
     */
    fun setVertexData(): FloatArray

    fun bindData(textureShaderProgram: TextureShaderProgram)

    fun draw(drawShaper: Int, first: Int, count: Int)

    fun draw()
}

/**
 * 模型绘制信息
 * @param shaper GL_TRIANGLE_FAN GL_TRIANGLES GL_POINTS GL_LINES ...
 */
interface ModelDrawInfo {
    val shaper: Int
    val first: Int
    val count: Int
}