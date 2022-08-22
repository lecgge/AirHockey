package com.example.airhockey.objects

import Point
import com.example.airhockey.data.VertexArray
import com.example.airhockey.programs.ColorShaderProgram

class Line(
    var position: Point,
    val lineWidth: Float,
    val lineHeight: Float,
) {
    private var vertexArray: VertexArray
    private var drawList: List<DrawCommand>

    init {
        val data = createLine(position,lineWidth, lineHeight)
        vertexArray = VertexArray(data.vertexData)
        drawList = data.drawCommands
    }
    fun change(){
        val data = createLine(position,lineWidth, lineHeight)
        vertexArray = VertexArray(data.vertexData)
        drawList = data.drawCommands
    }

    fun bindData(colorShaderProgram: ColorShaderProgram){
        vertexArray.setVertexAttribPointer(
            dataOffset = 0,
            attributeLocation =colorShaderProgram.aPositionLocation,
            componentCount = POSITION_COMPONENT_COUNT,
            stride = 0
        )
    }

    fun draw() {
        drawList.forEach {
            it.draw()
        }
    }

    fun createLine(
        position: Point,
        lineWidth: Float,
        lineHeight: Float
    ): GeneratedData {
        return ObjectBuilder().appendLine(position, lineWidth,lineHeight).build()
    }

    companion object {
        private const val POSITION_COMPONENT_COUNT = 3
    }
}