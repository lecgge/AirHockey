package com.example.airhockey.objects

import Circle
import Cylinder
import Point
import com.example.airhockey.data.VertexArray
import com.example.airhockey.programs.ColorShaderProgram

class Line {
    private val vertexArray: VertexArray
    private val drawList: List<DrawCommand>

    init {
        val data = createLine(
        )
        vertexArray = VertexArray(data.vertexData)
        drawList = data.drawCommands
    }

    private fun createLine(): GeneratedData {
        // 添加顶部的圆，再添加配套的圆筒
        return ObjectBuilder()
            .createLine(Point(-0.5f,0.5f,0f),Point(-0.5f,-0.5f,0f))
            .build()
    }

    fun bindData(colorShaderProgram: ColorShaderProgram) {
        vertexArray.setVertexAttribPointer(
            dataOffset = 0,
            attributeLocation = colorShaderProgram.aPositionLocation,
            componentCount = POSITION_COMPONENT_COUNT,
            stride = 0
        )
    }

    fun draw() {
        drawList.forEach {
            it.draw()
        }
    }


    companion object {
        private const val POSITION_COMPONENT_COUNT = 3
    }
}