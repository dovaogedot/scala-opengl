package gpu


import org.lwjgl.system.MemoryUtil
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.*

import scala.collection.mutable.LinkedHashMap
import scala.collection.mutable.ListBuffer


case class Geometry private (
    vao: Int,
    ebo: Int,
    vbos: List[Int],
    vertexCount: Int
) {

    def bind() = {
        glBindVertexArray(vao)
    }


    def unbind() = {
        glBindVertexArray(0)
    }


    def delete() = {
        glDisableVertexAttribArray(0)

        glBindBuffer(GL_ARRAY_BUFFER, 0)
        vbos.foreach(glDeleteBuffers)
        glDeleteBuffers(ebo)

        glBindVertexArray(0)
        glDeleteVertexArrays(vao)
    }

}


object Geometry {

    def apply(vertices: List[Vertex], indices: List[Int], programId: Int): Either[String, Geometry] = {
        if vertices.length < 1 || indices.length < 1 then
            Left("Failed to create geometry. Check vertices and indices.")

        // VAO
        val vao = glGenVertexArrays()
        glBindVertexArray(vao)

        // VBO
        val componentsPerAttr = vertices.head.attributes.mapValues(_.length)

        val vbos = vertices.view
            .flatMap(_.attributes.toList)
            .groupBy(_._1)
            .map { case (attr, attrs) =>
                val values = attrs.flatMap(_._2).toArray

                val buffer = MemoryUtil.memAllocFloat(values.length)
                buffer.put(values).flip()

                val vbo = glGenBuffers()
                glBindBuffer(GL_ARRAY_BUFFER, vbo)
                glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW)

                MemoryUtil.memFree(buffer)

                val components = componentsPerAttr.getOrElse(attr, 0)
                val location   = glGetAttribLocation(programId, attr)
                println(s"$attr: $components")

                val err = glGetError()
                if err != GL_NO_ERROR then
                    println(s"$attr: OpenGL error: $err")

                glVertexAttribPointer(location, components, GL_FLOAT, false, 0, 0)
                glEnableVertexAttribArray(location)

                vbo
            }.to(List)

        // EBO
        val buffer = MemoryUtil.memAllocInt(indices.length)
        buffer.put(indices.toArray).flip()

        val ebo = glGenBuffers()

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW)

        MemoryUtil.memFree(buffer)
        Right(new Geometry(vao, ebo, vbos, indices.length))
    }

}
