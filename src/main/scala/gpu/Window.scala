package gpu


import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.GL11.*;
import org.lwjgl.system.MemoryUtil.*;


class Window private (id: Long) {

    def loop(frame: Float => Unit) = {
        var lastTime    = System.currentTimeMillis() / 1000f
        var currentTime = 0f
        var deltaTime   = 0f

        while !glfwWindowShouldClose(id) do
            glClear(GL_COLOR_BUFFER_BIT)
            glfwPollEvents()

            currentTime = System.currentTimeMillis() / 1000f
            deltaTime = currentTime - lastTime
            lastTime = currentTime

            frame(deltaTime)

            glfwSwapBuffers(id)
        end while

        glfwTerminate()
    }

}


object Window {

    def apply(width: Int, height: Int, title: String): Either[String, Window] = {
        GLFWErrorCallback.createPrint(System.err).set()

        if !glfwInit() then
            return Left("Failed to initialize GLFW")

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        val windowId = glfwCreateWindow(width, height, "Hello World!", NULL, NULL)

        if windowId == NULL then
            return Left("Failed to create the GLFW window")

        glfwSetKeyCallback(
            windowId,
            (w, key, scancode, action, mods) =>
                if key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE then
                    print("Closing")
                    glfwSetWindowShouldClose(w, true); // We will detect this in the rendering loop
        );

        glfwMakeContextCurrent(windowId)
        glfwSwapInterval(1)

        glfwShowWindow(windowId)

        GL.createCapabilities()
        glClearColor(0.2f, 0.2f, 0.2f, 1f)

        return Right(new Window(windowId))
    }

}
