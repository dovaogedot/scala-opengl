import gpu.Window
import engine.Square
import engine.Game


object Main {

    @main def run() = {
        val result =
            for
                window <- Window(800, 480, "Game")
                game   <- Square.game()
            yield window.loop(game, Game.step)

        println(result.fold(
            left => left,
            right => "Ok"
        ))
    }

}
