package gpu


import java.nio.FloatBuffer
import org.lwjgl.opengl.GL20.{glUniform1fv, glUniform2fv, glUniform3fv, glUniformMatrix4fv}
import org.joml.{Vector2f, Vector3f, Matrix4f}


trait Uniform[T] {
    def size(value: T): Int
    def put(value: T, buffer: FloatBuffer): Unit
    def upload(location: Int, buffer: FloatBuffer): Unit
}


object Uniform {

    given Uniform[Float] with
        def size(value: Float): Int                          = 1
        def put(value: Float, buffer: FloatBuffer): Unit     = buffer.put(value)
        def upload(location: Int, buffer: FloatBuffer): Unit = glUniform1fv(location, buffer)


    given Uniform[Vector2f] with
        def size(value: Vector2f): Int                       = 2
        def put(value: Vector2f, buffer: FloatBuffer): Unit  = buffer.put(value.x).put(value.y)
        def upload(location: Int, buffer: FloatBuffer): Unit = glUniform2fv(location, buffer)


    given Uniform[Vector3f] with
        def size(value: Vector3f): Int                       = 3
        def put(value: Vector3f, buffer: FloatBuffer): Unit  = buffer.put(value.x).put(value.y).put(value.z)
        def upload(location: Int, buffer: FloatBuffer): Unit = glUniform3fv(location, buffer)


    given Uniform[Matrix4f] with
        def size(value: Matrix4f): Int                       = 4
        def put(value: Matrix4f, buffer: FloatBuffer): Unit  = value.get(buffer)
        def upload(location: Int, buffer: FloatBuffer): Unit = glUniformMatrix4fv(location, false, buffer)

}
