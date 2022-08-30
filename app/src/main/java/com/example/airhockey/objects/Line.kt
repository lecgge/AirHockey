package com.example.airhockey.objects

import Point
import com.example.airhockey.base.BaseColorModel
import com.example.airhockey.data.VertexArray

class Line(
    id:Int,
    position: Point,
    val lineWidth: Float,
    val lineHeight: Float,
    hierarchy:Int
) : BaseColorModel(id,position,hierarchy) {
    init {
        val data = createLine(position,lineWidth, lineHeight)
        vertexArray = VertexArray(data.vertexData)
        drawList = data.drawCommands
    }

    fun createLine(
        position: Point,
        lineWidth: Float,
        lineHeight: Float
    ): GeneratedData {
        return ObjectBuilder().appendLine(position, lineWidth,lineHeight).build()
    }

}