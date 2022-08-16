uniform  mat4 u_Matrix;
attribute vec4 a_Position;
attribute vec2 a_TextureCoordinates;//定义为vec2，因为有两个分量：S坐标和T坐标
varying vec2 v_TextureCoordinates;

void main(){
    v_TextureCoordinates=a_TextureCoordinates;
    gl_Position=u_Matrix * a_Position;
}