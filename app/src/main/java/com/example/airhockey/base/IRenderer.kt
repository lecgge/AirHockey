package com.example.airhockey.base

interface IRenderer {

    //碰撞检测方法
    fun clash()

    //绘制纹理模型
    fun drawTextureModel()

    //绘制颜色模型
    fun drawColorModel()

    //添加纹理模型
    fun addTextureModel(model: BaseTextureModel)
    //移动纹理模型
    fun moveTextureModel(model: BaseTextureModel)
    fun addColorModel(model: BaseColorModel)
    fun moveColorModel(model: BaseColorModel)
}