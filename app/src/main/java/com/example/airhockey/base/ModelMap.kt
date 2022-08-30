package com.example.airhockey.base

interface ModelMap {

    //纹理模型Map
    val textureModelMap : MutableMap<Int, BaseTextureModel>
    //非纹理模型Map（颜色模型Map）
    val colorModelMap : MutableMap<Int,BaseColorModel>

}
