package engine


import scala.reflect.ClassTag

import components.Component
import systems.System


type EntityId = Long


case class Game(
    components: Map[EntityId, Map[Class[?], Component]],
    systems: List[System]
) {

    def get[C <: Component](entity: EntityId)(using ct: ClassTag[C]): Option[C] = {
        components.get(entity).flatMap(_.get(ct.runtimeClass).map(_.asInstanceOf[C]))
    }


    def upsert[C <: Component](entity: EntityId, component: C): Game =
        val updatedComponents = components
            .getOrElse(entity, Map.empty)
            .updated(component.getClass, component)
        copy(components = components.updated(entity, updatedComponents))

}


object Game {

    def step(world: Game, deltaTime: Double): Game = {
        world.systems.foldLeft(world)((w, system) => system.run(deltaTime)(w))
    }

}
