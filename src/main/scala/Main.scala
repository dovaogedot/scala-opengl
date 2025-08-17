import gpu.Window
import gpu.Vertex
import engine.Actor
import gpu.Geometry
import gpu.Shader
import engine.Mesh


object Main {

    @main def run() = {
        val result =
            for
                window   <- Window(800, 480, "Game")
                triangle <- triangle()
            yield
                val game = Game().addActor(triangle)
                window.loop(game.frame)

        print(result.fold(
            left => left,
            right => "Ok"
        ))
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
            geometry <- Geometry(vertices, indices, shader.id).toRight(
                            "Failed to create geometry. Check vertices and indices."
                        )
        yield
            val mesh = Mesh(geometry, shader)
            Actor().addComponent(mesh)
    }

}
