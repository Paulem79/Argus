package net.paulem.argus.core.lightning;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector3f;

public class DirectionalLight {
    @Getter
    @Setter
    Vector3f color, direction;
    @Getter
    @Setter
    private float intensity;

    public DirectionalLight(Vector3f color, Vector3f direction, float intensity) {
        this.color = color;
        this.direction = direction;
        this.intensity = intensity;
    }
}
