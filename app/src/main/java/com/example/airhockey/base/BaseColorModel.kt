package com.example.airhockey.base

import Point
import com.example.airhockey.data.VertexArray
import com.example.airhockey.objects.DrawCommand
import com.example.airhockey.programs.ColorShaderProgram

abstract class BaseColorModel(var id: Int, var position: Point,var hierarchy:Int) : IModel {
    lateinit var vertexArray: VertexArray
    lateinit var drawList: List<DrawCommand>

    override fun bindData(colorShaderProgram: ColorShaderProgram) {
        vertexArray.setVertexAttribPointer(
            dataOffset = 0,
            attributeLocation = colorShaderProgram.aPositionLocation,
            componentCount = POSITION_COMPONENT_COUNT,
            stride = 0
        )
    }

    companion object {
        private const val POSITION_COMPONENT_COUNT = 3
    }

    override fun draw() {
        drawList.forEach {
            it.draw()
        }
    }
}