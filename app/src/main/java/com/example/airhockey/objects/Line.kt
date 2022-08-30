package com.example.airhockey.objects

import Point
import com.example.airhockey.base.BaseColorModel
import com.example.airhockey.data.VertexArray

class Line(
    id:Int,
    position: Point,
    val lineWidth: Float,
    val lineHeight: Float,
) : BaseColorModel(id,position) {
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