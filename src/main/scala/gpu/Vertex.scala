package gpu

import scala.collection.mutable.LinkedHashMap


case class Vertex private (attributes: LinkedHashMap[String, Array[Float]]) {

    def set(attr: String, values: Float*): Vertex = {
        attributes.update(attr, values.toArray)
        this
    }

}


object Vertex {

    def apply(x: Float, y: Float, z: Float): Vertex = {
        new Vertex(LinkedHashMap(
            ("position", Array(x, y, z)),
            ("normal", Array(0, 0, 0)),
            ("tangent", Array(0, 0, 0)),
            ("color", Array(0, 0, 0)),
            ("uv", Array(0, 0))
        ))
    }

}
