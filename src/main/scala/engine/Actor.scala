package engine


import scala.reflect.ClassTag
import scala.collection.mutable.Map


class Actor {
    private val components: Map[Class[?], Component] = Map()


    def update(deltaTime: Double) = {
        components.values.foreach(_.update(this, deltaTime))
    }


    def addComponent[T <: Component](component: T): Actor = {
        components += (component.getClass -> component)
        this
    }


    def getComponent[T <: Component](using ct: ClassTag[T]): Option[T] = {
        components.get(ct.runtimeClass).asInstanceOf[Option[T]]
    }

}
