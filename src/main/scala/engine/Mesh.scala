package engine


import engine.Component
import gpu.Geometry
import gpu.Shader
import gpu.Renderer


case class Mesh private (geometry: Geometry, shader: Shader) extends Component {

    def update(deltaTime: Float) = {
        render()
    }


    def render() = {
        shader.bind()
        geometry.bind()

        Renderer.draw(geometry.vertexCount)

        geometry.unbind()
        shader.unbind()
    }

}


object Mesh {

    def apply(geometry: Geometry, shader: Shader): Mesh = {
        new Mesh(geometry, shader)
    }

}
