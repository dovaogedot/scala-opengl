package gpu


import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.system.MemoryUtil
import scala.collection.immutable.HashMap
import org.lwjgl.system.MemoryStack
import org.joml.Vector2f


case class Renderer private (uniforms: HashMap[String, Int]) {

    def draw[T](geometry: Geometry, shader: Shader) = { // , uniforms: Map[String, T])(using u: Uniform[T]) = {
        shader.bind()
        geometry.bind()

        // uniforms.foreach { case (name, value) => shader.setUniform(name, value) }

        glDrawElements(GL_TRIANGLES, geometry.vertexCount, GL_UNSIGNED_INT, 0)
    }

}


object Renderer {

    def apply(): Renderer = {
        new Renderer(HashMap())
    }

}
