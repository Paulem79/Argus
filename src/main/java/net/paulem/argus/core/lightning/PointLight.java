package net.paulem.argus.core.lightning;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector3f;

public class PointLight {
    @Getter
    @Setter
    private Vector3f color, position;
    @Getter
    @Setter
    private float intensity, constant, linear, exponent;

    public PointLight(Vector3f color, Vector3f position, float intensity, float constant, float linear, float exponent) {
        this.color = color;
        this.position = position;
        this.intensity = intensity;
        this.constant = constant;
        this.linear = linear;
        this.exponent = exponent;
    }

    public PointLight(Vector3f color, Vector3f position, float intensity) {
        this(color, position, intensity, 1, 0, 0);
    }
}
