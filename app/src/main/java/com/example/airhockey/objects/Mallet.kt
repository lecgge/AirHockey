package com.example.airhockey.objects

import Circle
import Cylinder
import Point
import android.opengl.GLES20
import com.example.airhockey.data.Constants
import com.example.airhockey.data.VertexArray
import com.example.airhockey.programs.ColorShaderProgram


/**
 *@Author QiYuZhen
 *
 *
 */
class Mallet (val baseRadius:Float,
              val height: Float,
numPointsAroundMallet: Int){
    private val vertexArray:VertexArray
    private val drawList: List<DrawCommand>
    init {
        val data=createMallet(
            center = Point(0F,0F,0F),
            radius = baseRadius,
            height=height,
            numPoints = numPointsAroundMallet
        )
        vertexArray = VertexArray(data.vertexData)
        drawList=data.drawCommands
    }



    fun bindData(colorShaderProgram: ColorShaderProgram){
        vertexArray.setVertexAttribPointer(
            dataOffset = 0,
            attributeLocation =colorShaderProgram.aPositionLocation,
            componentCount = POSITION_COMPONENT_COUNT,
            stride = 0
        )
    }

    fun draw(){
      //  GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 2)
        drawList.forEach{
            it.draw()
        }
    }
    /**
     * 构建木槌
     * @param center 木槌整体的中心
     * @param radius 底座的半径
     * @param height 木槌整体高度
     * @param numPoints 底座与手柄的圆周需要以几个顶点模拟
     */
    fun createMallet(center: Point, radius: Float, height: Float, numPoints: Int): GeneratedData {
        val builder = ObjectBuilder()
        // 计算底座的高度
        val baseHeight = height * 0.25F

        // 底座顶部的圆
        val baseCircle = Circle(
            // 下降0.25个高度就是底座顶部圆心
            center = center.translateY(-baseHeight),
            radius = radius
        )
        val baseCylinder = Cylinder(
            // 顶部再下降自身一半的高度
            center = baseCircle.center.translateY(-baseHeight / 2F),
            radius = radius,
            height = baseHeight
        )
        builder.appendCircle(baseCircle, numPoints)
            .appendOpenCylinder(baseCylinder, numPoints)
        //计算手柄的高度
        val handleHeight = height - baseHeight
        //计算手柄半径
        val handleRadius=radius/3F
        val handleCircle=Circle(
            center=center.translateY(height/2F),
            radius=handleRadius
        )
        val handleCylinder=Cylinder(
             center = handleCircle.center.translateY(-handleHeight/2F),
            radius = handleRadius,
            height = handleHeight
        )
        return builder.appendCircle(handleCircle,numPoints)
            .appendOpenCylinder(handleCylinder,numPoints)
            .build()

    }

    companion object {
        private const val POSITION_COMPONENT_COUNT = 3

    }

}