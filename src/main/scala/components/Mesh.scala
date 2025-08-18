package components


import gpu.Geometry
import gpu.Shader


case class Mesh(
    geometry: Geometry,
    shader: Shader
) extends Component
