package systems

import engine.Game


trait System:
    def run(deltaTime: Double)(world: Game): Game
