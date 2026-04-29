package net.paulem.argus.core.managers;

import lombok.Getter;
import lombok.Setter;
import net.paulem.argus.core.Argus;
import net.paulem.argus.core.ILogic;
import net.paulem.argus.core.MouseInput;
import net.paulem.argus.utils.Constants;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

public class EngineManager {

    public static final long NANOSECOND = 1_000_000_000L;
    public static final long FRAMERATE = 10000;

    @Getter
    @Setter
    private static int fps;
    private static float frameTime = 1.0f / FRAMERATE;

    @Getter
    @Setter
    private static int renderedTriangles;

    @Getter
    @Setter
    private static int renderedVertices;

    private boolean isRunning = false;

    private WindowManager window;
    private MouseInput mouseInput;
    private GLFWErrorCallback errorCallback;
    private ILogic gameLogic;

    private void init() throws Exception {
        GLFW.glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        window = Argus.INSTANCE.getWindow();
        gameLogic = Argus.INSTANCE.getGame();
        mouseInput = new MouseInput();
        window.init();
        gameLogic.init();
        mouseInput.init();
    }

    public void start() throws Exception {
        init();
        if(isRunning) {
            return;
        }

        run();
    }

    public void run() {
        this.isRunning = true;

        int frames = 0;
        long frameCounter = 0;
        long lastTime = System.nanoTime();
        double unprocessedTime = 0;

        while(isRunning) {
            boolean render = false;
            long startTime = System.nanoTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += (passedTime / (double) NANOSECOND);
            frameCounter += passedTime;

            input();

            while(unprocessedTime > frameTime) {
                render = true;
                unprocessedTime -= frameTime;

                if(window.windowShouldClose()) {
                    stop();
                }

                if(frameCounter >= NANOSECOND) {
                    setFps(frames);
                    window.setTitle(Constants.TITLE + " - " + getFps() + " FPS - Triangles: " + getRenderedTriangles() + " - Vertices: " + getRenderedVertices());
                    frames = 0;
                    frameCounter = 0;
                }
            }

            if(render) {
                update(frameTime);
                render();
                frames++;
            }
        }

        cleanup();
    }

    public void stop() {
        if(!isRunning) {
            return;
        }

        isRunning = false;
    }

    public void input() {
        mouseInput.input();
        gameLogic.input();
    }

    public void render() {
        gameLogic.render();
        window.update();
    }

    public void update(float interval) {
        gameLogic.update(interval, mouseInput);
    }

    public void cleanup() {
        window.cleanup();
        gameLogic.cleanup();
        errorCallback.free();
        GLFW.glfwTerminate();
    }
}
