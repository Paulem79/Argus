package net.paulem.argus.core.entity;

import lombok.Getter;
import org.joml.Vector3f;

public class Entity {
    @Getter
    private Model model;
    @Getter
    private Vector3f pos, rotation;
    @Getter
    private float scale;

    public Entity(Model model, Vector3f pos, Vector3f rotation, float scale) {
        this.model = model;
        this.pos = pos;
        this.rotation = rotation;
        this.scale = scale;
    }

    public void addPos(float x, float y, float z) {
        this.pos.add(x, y, z);
    }

    public void setPos(float x, float y, float z) {
        this.pos.set(x, y, z);
    }

    public void addRotation(float x, float y, float z) {
        this.rotation.add(x, y, z);
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.set(x, y, z);
    }
}
