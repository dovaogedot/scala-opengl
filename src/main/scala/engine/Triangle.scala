package engine


import gpu.Vertex
import gpu.Shader
import gpu.Geometry
import gpu.Renderer

import components.Component
import components.Mesh
import components.MyComponent
import engine.EntityId
import systems.RenderSystem


object Triangle {

    def game(): Either[String, Game] = {
        for
            triangle <- triangle()
        yield
            val game = Game(Map.empty, List(RenderSystem))
            triangle.foldLeft(game)((acc, c) => acc.upsert(0, c))
    }


    def triangle(): Either[String, List[Component]] = {
        val vertices = List(
            Vertex(-0.5f, -0.5f, 0f).set("color", 1f, 0f, 0f),
            Vertex(-0.5f, 0.5f, 0f).set("color", 0f, 1f, 0f),
            Vertex(0.5f, 0.5f, 0f).set("color", 1f, 1f, 0f),
            Vertex(0.5f, -0.5f, 0f).set("color", 0f, 0f, 1f)
        )

        val indices = List(0, 1, 2, 0, 2, 3)

        for
            shader   <- Shader()
            geometry <- Geometry(vertices, indices, shader.programId)
        yield
            val mesh        = Mesh(geometry, shader)
            val myComponent = MyComponent(System.nanoTime() / 1e9, 0, 0)
            List(mesh, myComponent)
    }

}
