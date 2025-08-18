package gpu


import scala.io.Source
import scala.util.Try

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL20.*;
import org.lwjgl.system.MemoryUtil.*;
import org.lwjgl.system.MemoryStack
import scala.util.Using


case class Shader private (programId: Int) {

    def setUniform[T](name: String, value: T)(using uniform: Uniform[T]): Shader = {
        val location = glGetUniformLocation(programId, name)
        val stack    = MemoryStack.stackPush()

        try
            val buffer = stack.mallocFloat(uniform.size(value))

            uniform.put(value, buffer)
            buffer.flip()
            uniform.upload(location, buffer)

        finally
            stack.pop()

        this
    }


    def bind()    = glUseProgram(programId)
    def unbind()  = glUseProgram(0)
    def cleanUp() = glDeleteProgram(programId)
}


enum ShaderType(val glEnum: Int) {

    case Vertex extends ShaderType(GL_VERTEX_SHADER)

    case Fragment extends ShaderType(GL_FRAGMENT_SHADER)

}


object Shader {

    def apply(vertexSource: String, fragmentSource: String): Either[String, Shader] = {
        for
            vertexId   <- createShader(vertexSource, ShaderType.Vertex)
            fragmentId <- createShader(fragmentSource, ShaderType.Fragment)

            programId <- createProgram(vertexId, fragmentId)
        yield new Shader(programId)
    }


    def apply(): Either[String, Shader] = {
        for
            vertexSource   <- defaultVertex
            fragmentSource <- defaultFragment
            shader         <- Shader(vertexSource, fragmentSource)
        yield shader
    }


    def createProgram(vertexId: Int, fragmentId: Int): Either[String, Int] = {
        val programId = glCreateProgram()

        if programId == 0 then
            Left("Failed to create a shader program")

        glAttachShader(programId, vertexId)
        glAttachShader(programId, fragmentId)

        glLinkProgram(programId)

        if glGetProgrami(programId, GL_LINK_STATUS) == 0 then
            return Left("Failed to link shader program: " + glGetProgramInfoLog(programId))

        glDetachShader(programId, vertexId)
        glDetachShader(programId, fragmentId)

        glValidateProgram(programId)

        if glGetProgrami(programId, GL_VALIDATE_STATUS) == 0 then
            return Left("Failed to validate shader program: " + glGetProgramInfoLog(programId))

        Right(programId)
    }


    def createShader(source: String, shaderType: ShaderType): Either[String, Int] = {
        val shaderId = glCreateShader(shaderType.glEnum)

        glShaderSource(shaderId, source)
        glCompileShader(shaderId)

        if glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0 then
            Left("Failed to compile shader: " + glGetShaderInfoLog(shaderId))
        else
            Right(shaderId)
    }


    val defaultVertex =
        Using(
            Source.fromResource("shaders/default.vert")
        )(_.mkString).toEither.left.map(_.getMessage())


    val defaultFragment = Using(
        Source.fromResource("shaders/default.frag")
    )(_.mkString).toEither.left.map(_.getMessage())

}
