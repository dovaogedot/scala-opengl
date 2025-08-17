package engine


import engine.Component
import gpu.Geometry
import gpu.Shader
import gpu.Renderer
import org.joml.Vector2f


class MeshComponent(
    var geometry: Geometry,
    var shader: Shader,
    var renderer: Renderer
) extends Component {

    def update(actor: Actor, deltaTime: Double) = {
        renderer.draw(geometry, shader)
    }

}
