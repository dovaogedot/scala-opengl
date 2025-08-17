package engine


import gpu.{Shader, Window}
import engine.Actor
import scala.collection.mutable.ArrayBuffer


class Game {
    private val actors: ArrayBuffer[Actor] = ArrayBuffer()


    def frame(deltaTime: Double) = {
        actors.foreach(_.update(deltaTime))
    }


    def addActor(actor: Actor): Game = {
        actors += actor
        this
    }

}
