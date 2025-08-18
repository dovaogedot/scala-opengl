package gpu


import org.lwjgl.glfw.{GLFWErrorCallback}
import org.lwjgl.opengl.GL

import org.lwjgl.glfw.GLFW.{
    glfwInit,
    glfwWindowHint,
    glfwCreateWindow,
    glfwDestroyWindow,
    glfwTerminate,
    glfwPollEvents,
    glfwSwapBuffers,
    glfwSetKeyCallback,
    glfwMakeContextCurrent,
    glfwSwapInterval,
    glfwShowWindow,
    glfwWindowShouldClose,
    glfwSetWindowShouldClose,
    GLFW_CONTEXT_VERSION_MAJOR,
    GLFW_CONTEXT_VERSION_MINOR,
    GLFW_VISIBLE,
    GLFW_RESIZABLE,
    GLFW_FALSE,
    GLFW_TRUE,
    GLFW_KEY_ESCAPE,
    GLFW_RELEASE,
    *
}
import org.lwjgl.opengl.GL11.{glClear, glClearColor, glViewport, GL_COLOR_BUFFER_BIT, GL_DEPTH_BUFFER_BIT}
import org.lwjgl.system.MemoryUtil.NULL


case class Window private (id: Long) {

    def loop[T](initial: T, frame: (T, Double) => T): T = {
        val finalState = step(initial, frame, System.nanoTime() / 1e9)
        glfwDestroyWindow(id)
        glfwTerminate()
        finalState
    }


    @annotation.tailrec
    private def step[T](state: T, frame: (T, Double) => T, lastTime: Double): T = {
        if glfwWindowShouldClose(id) then
            return state

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
        glfwPollEvents()

        val currentTime = System.nanoTime() / 1e9
        val deltaTime   = currentTime - lastTime

        val nextState = frame(state, deltaTime)

        glfwSwapBuffers(id)
        step(nextState, frame, currentTime)
    }

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
                    glfwSetWindowShouldClose(w, true);
        );

        glfwSetFramebufferSizeCallback(
            windowId,
            (w, width, height) =>
                glViewport(0, 0, width, height)
        )

        glfwMakeContextCurrent(windowId)
        glfwSwapInterval(1)

        glfwShowWindow(windowId)

        GL.createCapabilities()
        glClearColor(0.2f, 0.2f, 0.2f, 1f)
        glViewport(0, 0, width, height)

        return Right(new Window(windowId))
    }

}
