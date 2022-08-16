package com.example.airhockey.objects

import Circle

import Cylinder
import Point
import android.opengl.GLES20.*



/**
 *@Author QiYuZhen
 *
 */
class ObjectBuilder {
    private val vertexDataList: MutableList<Float> = mutableListOf()
    private val commands = mutableListOf<DrawCommand>()

    fun createLine(point1: Point, point2: Point) : ObjectBuilder {
        vertexDataList.apply {
            //添加顶点
            add(point1.x)
            add(point1.y)
            add(point1.z)

            add(point2.x)
            add(point2.y)
            add(point2.z)
        }

        return this
    }

    //计算绘制一个圆实际需要的顶点数，等于圆周顶点数+2
    private fun sizeOfCircleInVertices(numPoints: Int): Int = numPoints + 2

    //计算绘制一个圆筒实际需要的顶点数，等于（圆周顶点数+1)*2
    private fun sizeOfOpenCylinderInVertices(numPoints: Int): Int = (numPoints + 1) * 2

    /**
     *  使用三角形扇形构建圆
     */

    fun appendCircle(circle: Circle, numPoints: Int): ObjectBuilder {
        val numVertices = sizeOfCircleInVertices(numPoints)
        val startVertex = vertexDataList.size / FLOATS_PER_VERTEX
        //计算2PI
        val pi2: Double = Math.PI * 2
        //缓存每个点的角度
        var angleInRadians: Double
        vertexDataList.apply {
            //添加圆心
            add(circle.center.x)
            add(circle.center.y)
            add(circle.center.z)
            //因为我们想重复加入第一个点，因此允许i赋值为numPoints
            for (i in 0..numPoints) {
                //计算该点的角度
                angleInRadians = (i.toDouble()) / (numPoints.toDouble()) * pi2
                add(circle.center.x + circle.radius * kotlin.math.cos(angleInRadians).toFloat())
                add(circle.center.y)
                add(circle.center.z + circle.radius * kotlin.math.sin(angleInRadians).toFloat())
            }
        }
        commands.add {
            glDrawArrays(
                GL_TRIANGLE_FAN,
                startVertex,
                numVertices
            )//glDrawArrays的功能：提供绘制功能，从数组数据中提取数据渲染基本图元
            //调用 glDrawArrays时，将使用 每个启用数组中的顺序元素来构造几何基元序列，从 第一 个元素开始。
        }

        return this
    }

    /**
     * 使用三角形带来构建圆筒
     */
    fun appendOpenCylinder(cylinder: Cylinder, numPoints: Int): ObjectBuilder {
        val numVertices = sizeOfCircleInVertices(numPoints)
        //添加顶点之前先计算起始顶点 的index
        val startVertex = vertexDataList.size / FLOATS_PER_VERTEX
        //计算2pi
        val pi2: Double = Math.PI * 2
        //下圆周的y分量
        val yStart: Float = cylinder.center.y - (cylinder.height / 2F)
        //上圆周的y分量
        val yEnd: Float = cylinder.center.y + (cylinder.height / 2F)
        //缓存每个点的角度
        var angleInRadians: Double
        //缓存点的x,z分量
        var xPosition: Float
        var zPosition: Float
        vertexDataList.apply { //因为我们想重复加入第一个点，因此允许i赋值为numPoints
            for (i in 0..numPoints) {
                //计算该点的角度
                angleInRadians = (i.toDouble()) / (numPoints.toDouble()) * pi2

                xPosition = cylinder.center.x + cylinder.radius * kotlin.math.cos(angleInRadians).toFloat()
                zPosition = cylinder.center.z + cylinder.radius * kotlin.math.sin(angleInRadians).toFloat()

                add(xPosition)
                add(yStart)
                add(zPosition)

                add(xPosition)
                add(yEnd)
                add(zPosition)
            }
        }
        commands.add {
            glDrawArrays(GL_TRIANGLE_STRIP, startVertex, numVertices)
        }
        return this
    }

    fun build(): GeneratedData = GeneratedData(
        vertexData = vertexDataList.toFloatArray(), drawCommands = commands
    )


    companion object {
        private const val FLOATS_PER_VERTEX = 3 //ObjectBuilder认为顶点数据由三个分量x、y、z组成
    }

}

fun interface DrawCommand {
    //接口DrawCommand是一个描述绘制物体所需要的具体指令的一个函数式接口（SAM）
    fun draw()
}

class GeneratedData(
    val vertexData: FloatArray,
    val drawCommands: List<DrawCommand>
)
