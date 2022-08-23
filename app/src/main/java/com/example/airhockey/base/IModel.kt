package com.example.airhockey.base

import com.example.airhockey.data.VertexArray
import com.example.airhockey.objects.DrawCommand
import com.example.airhockey.objects.Line
import com.example.airhockey.programs.ColorShaderProgram

interface IModel {



    fun bindData(colorShaderProgram: ColorShaderProgram)
    fun draw()
}