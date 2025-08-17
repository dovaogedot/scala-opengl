import gpu.{Shader, Window}
import engine.Actor


case class Game private (actors: List[Actor]) {

    def frame(deltaTime: Float) = {
        for
            actor     <- actors
            component <- actor.components
        yield component.update(deltaTime)
    }


    def addActor(actor: Actor): Game = {
        this.copy(actors = actor +: actors)
    }

}


object Game {

    def apply() = {
        new Game(List())
    }

}
