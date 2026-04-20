package net.paulem.argus.core.entity;

import lombok.Getter;
import lombok.Setter;
import net.paulem.argus.utils.Constants;
import org.joml.Vector4f;

public class Material {
    @Getter
    private Vector4f ambientColor, diffuseColor, specularColor;
    @Getter
    @Setter
    private float reflectance;
    @Getter
    @Setter
    private Texture texture;

    public Material() {
        this(Constants.DEFAULT_COLOR, 0);
    }

    public Material(Texture texture) {
        this(Constants.DEFAULT_COLOR, 0, texture);
    }

    public Material(Vector4f color, float reflectance) {
        this(color, reflectance, null);
    }

    public Material(Vector4f color, float reflectance, Texture texture) {
        this(color, color, color, reflectance, texture);
    }

    public Material(Vector4f ambientColor, Vector4f diffuseColor, Vector4f specularColor, float reflectance, Texture texture) {
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.reflectance = reflectance;
        this.texture = texture;
    }

    public boolean hasTexture() {
        return texture != null;
    }
}
