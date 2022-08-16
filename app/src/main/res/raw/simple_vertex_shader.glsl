//创建顶点着色器
uniform mat4 u_Matrix;//mat4意味着这个变量代表一个4x4矩阵
// attribute修饰符表示只读的顶点数据，只用在顶点着色器中。数据来自当前的顶点状态或者顶点数组。它必须是全局范围声明的，不能在函数内部。一个attribute可以是浮点数类型的标量，向量，或者矩阵。不可以是数组或则结构体

attribute vec4 a_Position;
//attribute vec4 a_Color;
//varying vec4 v_Color;  //varying是一种特殊类型的变量，它混合给定给它的值
void main() {//着色器的主要入口点
    // v_Color = a_Color;
    gl_Position = u_Matrix * a_Position;//将矩阵与位置向量相乘,顶点数组不再被解释为标准化设备坐标，而是被解释为存在于被矩阵定义的虚拟坐标空间中
    gl_PointSize = 10.0;//点的尺寸大小
}
