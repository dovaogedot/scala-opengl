import gpu.Window
import gpu.Vertex
import gpu.Geometry
import gpu.Shader
import org.joml.Vector2f
import gpu.Renderer
import engine.Triangle
import engine.Game
import scala.reflect.ClassTag


trait A
case class Mesh() extends A
object Mesh       extends A


object Main {

    @main def run() = {
        val result =
            for
                window <- Window(800, 480, "Game")
                game   <- Triangle.game()
            yield window.loop(game, Game.step)

        result.fold(
            left => left,
            right => "Ok"
        )
    }

}
