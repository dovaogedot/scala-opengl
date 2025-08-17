import gpu.Window
import gpu.Vertex
import engine.Actor
import gpu.Geometry
import gpu.Shader
import org.joml.Vector2f
import gpu.Renderer
import game.Triangle


object Main {

    @main def run() = {
        val result =
            for
                window <- Window(800, 480, "Game")
                game   <- Triangle.game()
            yield window.loop(game.frame)

        result.fold(
            left => left,
            right => "Ok"
        )
    }

}
