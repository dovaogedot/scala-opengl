package gpu

import org.lwjgl.opengl.GL11.*


object Renderer {

    def draw(vertexCount: Int) = {
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0)
    }

}
