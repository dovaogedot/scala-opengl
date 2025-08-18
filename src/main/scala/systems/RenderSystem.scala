package systems


import components.Mesh
import engine.Game

import org.lwjgl.opengl.GL11.{glDrawElements, GL_TRIANGLES, GL_UNSIGNED_INT}


object RenderSystem extends System:

    def run(deltaTime: Double)(world: Game): Game = {
        val entities = world.components.iterator.collect {
            case (entity, components) if components.contains(classOf[Mesh]) => entity
        }

        entities.foldLeft(world) { (acc, x) =>
            val mesh = acc.get[Mesh](x).get
            render(mesh)
            acc
        }
    }


    def render(mesh: Mesh): Unit = {
        mesh.shader.bind()
        mesh.geometry.bind()

        glDrawElements(GL_TRIANGLES, mesh.geometry.vertexCount, GL_UNSIGNED_INT, 0)
    }
