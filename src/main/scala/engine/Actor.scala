package engine


case class Actor private (components: Set[Component]) {

    def addComponent(component: Component): Actor = {
        copy(components = components + component)
    }

}


object Actor {

    def apply(): Actor = {
        new Actor(Set())
    }

}
