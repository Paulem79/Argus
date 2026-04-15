package net.paulem.argus.core.entity;

import lombok.Getter;
import org.joml.Vector3f;

public class Camera {
    @Getter
    private Vector3f position, rotation;

    public Camera() {
        this(new Vector3f(), new Vector3f());
    }

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void movePosition(float x, float y, float z) {
        if(z != 0) {
            position.x += (float) Math.sin(Math.toRadians(rotation.y)) * -1.0f * z;
            position.z += (float) Math.cos(Math.toRadians(rotation.y)) * z;
        }
        if(x != 0) {
            position.x += (float) Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * x;
            position.z += (float) Math.cos(Math.toRadians(rotation.y - 90)) * x;
        }
        position.y += y;
    }

    public void setPosition(float x, float y, float z) {
        position.set(x, y, z);
    }

    public void setRotation(float x, float y, float z) {
        rotation.set(x, y, z);
    }

    public void moveRotation(float x, float y, float z) {
        rotation.add(x, y, z);
    }
}
