package engine


trait Component {
    def update(actor: Actor, deltaTime: Double): Unit
}
