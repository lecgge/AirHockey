package com.example.airhockey.objects

import Point
import com.example.airhockey.base.BaseModel
import com.example.airhockey.data.VertexArray
import com.example.airhockey.programs.ColorShaderProgram

class Line(
    var position: Point,
    val lineWidth: Float,
    val lineHeight: Float,
) : BaseModel() {
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