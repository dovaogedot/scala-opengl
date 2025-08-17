package engine


trait Component {
    def update(deltaTime: Float): Unit
}
