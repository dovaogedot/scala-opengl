package game


import engine.Component
import engine.Actor
import engine.MeshComponent
import org.joml.Vector2f


class MyComponent extends Component {
    var time: Double = 0


    def update(actor: Actor, deltaTime: Double) = {
        time += deltaTime

        val x = Math.sin(time).toFloat
        val y = Math.cos(time).toFloat

        // println(s"$x, $y")

        val meshOption = actor.getComponent[MeshComponent]

        meshOption match
            case None       => ()
            case Some(mesh) => mesh.shader.setUniform("offset", new Vector2f(x, y))
    }

}
