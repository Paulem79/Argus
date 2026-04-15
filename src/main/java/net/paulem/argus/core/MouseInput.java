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

        if(previousPos.x > 0 && previousPos.y > 0 && inWindow) {
            double x = currentPos.x - previousPos.x;
            double y = currentPos.y - previousPos.y;
            boolean rotateX = x != 0;
            boolean rotateY = y != 0;

            if(rotateX) {
                displVec.y = (float) x;
            }
            if(rotateY) {
                displVec.x = (float) y;
            }
        }

        previousPos.set(currentPos);
    }
}
