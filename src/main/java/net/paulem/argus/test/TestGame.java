package net.paulem.argus.test;

import net.paulem.argus.core.Argus;
import net.paulem.argus.core.ILogic;
import net.paulem.argus.core.RenderManager;
import net.paulem.argus.core.WindowManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class TestGame implements ILogic {
    private int direction = 0;
    private float color = 0.0f;

    private final RenderManager renderer;
    private final WindowManager window;

    public TestGame() {
        this.renderer = new RenderManager();
        this.window = Argus.INSTANCE.getWindow();
    }

    @Override
    public void init() throws Exception {
        renderer.init();
    }

    @Override
    public void input() {
        if(window.isKeyPressed(GLFW.GLFW_KEY_UP)) {
            direction = 1;
        } else if(window.isKeyPressed(GLFW.GLFW_KEY_DOWN)) {
            direction = -1;
        } else {
            direction = 0;
        }
    }

    @Override
    public void update() {
        color += direction * 0.01f;
        if(color > 1) {
            color = 1;
        } else if(color < 0) {
            color = 0;
        }
    }

    @Override
    public void render() {
        if(window.isResize()) {
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(true);
        }

        window.setClearColor(color, color, color, 0);
        renderer.clear();
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
    }
}
