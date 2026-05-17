package net.paulem.argus.core;

import lombok.Getter;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public class MouseInput {
    private final Vector2d previousPos, currentPos;
    @Getter
    private final Vector2f displVec;

    @Getter
    private boolean inWindow = false, leftButtonPress = false, rightButtonPress = false;

    public MouseInput() {
        this.previousPos = new Vector2d(-1, -1);
        this.currentPos = new Vector2d(0, 0);
        this.displVec = new Vector2f();
    }

    public void init() {
        long windowHandle = Argus.INSTANCE.getWindow().getWindow();
        GLFW.glfwSetInputMode(windowHandle, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);

        GLFW.glfwSetCursorPosCallback(windowHandle, (window, xpos, ypos) -> {
            currentPos.set(xpos, ypos);
        });

        GLFW.glfwSetCursorEnterCallback(windowHandle, (window, entered) -> {
            inWindow = entered;
        });

        GLFW.glfwSetMouseButtonCallback(windowHandle, (window, button, action, mods) -> {
            leftButtonPress = button == GLFW.GLFW_MOUSE_BUTTON_LEFT && action == GLFW.GLFW_RELEASE;
            rightButtonPress = button == GLFW.GLFW_MOUSE_BUTTON_RIGHT && action == GLFW.GLFW_RELEASE;
        });
    }

    public void input() {
        displVec.set(0, 0);

        long windowHandle = Argus.INSTANCE.getWindow().getWindow();
        int width = Argus.INSTANCE.getWindow().getWidth();
        int height = Argus.INSTANCE.getWindow().getHeight();

        // Center of window
        double centerX = width / 2.0;
        double centerY = height / 2.0;

        if (inWindow) {
            double x = currentPos.x - centerX;
            double y = currentPos.y - centerY;

            if (x != 0) {
                displVec.y = (float) x;
            }
            if (y != 0) {
                displVec.x = (float) y;
            }

            // Center mouse
            GLFW.glfwSetCursorPos(windowHandle, centerX, centerY);

            // Tell current pos in center
            currentPos.set(centerX, centerY);
        }

        previousPos.set(currentPos);
    }
}
