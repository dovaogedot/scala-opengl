package game


import engine.Actor
import gpu.Vertex
import gpu.Shader
import gpu.Geometry
import gpu.Renderer
import engine.MeshComponent
import engine.Game


object Triangle {

    def game(): Either[String, Game] = {
        for
            triangle <- triangle()
        yield Game().addActor(triangle)
    }


    def triangle(): Either[String, Actor] = {
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
            var renderer      = Renderer()
            val meshComponent = MeshComponent(geometry, shader, renderer)
            val myComponent   = MyComponent()

            Actor()
                .addComponent(meshComponent)
                .addComponent(myComponent)
    }

}
