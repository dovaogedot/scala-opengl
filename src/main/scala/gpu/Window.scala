package gpu


import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.GL11.*;
import org.lwjgl.system.MemoryUtil.*;


case class Window private (id: Long) {

    def loop[T](initial: T, frame: (T, Double) => T): T = {
        var state    = initial
        var lastTime = System.nanoTime() / 1e9

        while (!glfwWindowShouldClose(id)) {
            glClear(GL_COLOR_BUFFER_BIT)
            glfwPollEvents()
            glfwSwapBuffers(id)

            val currentTime = System.nanoTime() / 1e9
            val deltaTime   = currentTime - lastTime

            state = frame(state, deltaTime)
            lastTime = currentTime
        }

        glfwDestroyWindow(id)
        glfwTerminate()
        state
    }

    // def loop[T](initial: T, frame: (T, Double) => T): T = {
    //     val finalState = step(initial, frame, System.nanoTime() / 1e9)
    //     glfwDestroyWindow(id)
    //     glfwTerminate()
    //     finalState
    // }

    // @annotation.tailrec
    // private def step[T](state: T, frame: (T, Double) => T, lastTime: Double): T = {
    //     if glfwWindowShouldClose(id) then
    //         return state

    //     glClear(GL_COLOR_BUFFER_BIT)
    //     glfwPollEvents()
    //     glfwSwapBuffers(id)

    //     val currentTime = System.nanoTime() / 1e9
    //     val deltaTime   = currentTime - lastTime

    //     step(frame(state, deltaTime), frame, currentTime)
    // }

}


object Window {

    def apply(width: Int, height: Int, title: String): Either[String, Window] = {
        GLFWErrorCallback.createPrint(System.err).set()

        if !glfwInit() then
            return Left("Failed to initialize GLFW")

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        val windowId = glfwCreateWindow(width, height, "Hello World!", NULL, NULL)

        if windowId == NULL then
            return Left("Failed to create the GLFW window")

        glfwSetKeyCallback(
            windowId,
            (w, key, scancode, action, mods) =>
                if key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE then
                    glfwSetWindowShouldClose(w, true); // We will detect this in the rendering loop
        );

        glfwMakeContextCurrent(windowId)
        glfwSwapInterval(1)

        glfwShowWindow(windowId)

        GL.createCapabilities()
        glClearColor(0.2f, 0.2f, 0.2f, 1f)
        glViewport(0, 0, width, height)

        return Right(new Window(windowId))
    }

}
