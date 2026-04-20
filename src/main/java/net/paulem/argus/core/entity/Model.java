package net.paulem.argus.core.entity;

import lombok.Getter;
import lombok.Setter;

public class Model {
    @Getter
    private int id;
    @Getter
    private int vertexCount;
    @Getter
    @Setter
    private Material material;

    public Model(int id, int vertexCount) {
        this(id, vertexCount, null);
    }

    public Model(int id, int vertexCount, Material material) {
        this.id = id;
        this.vertexCount = vertexCount;
        this.material = material;
    }

    public Model(Model model, Texture texture) {
        Material material = model.getMaterial();
        this(model.getId(), model.getVertexCount(), material != null ? material : new Material(texture));

        if(material != null) {
            this.material.setTexture(texture);
        }
    }

    public void setTexture(Texture texture, float reflectance) {
        if(this.material == null) {
            this.material = new Material();
        }
        this.material.setTexture(texture);
        this.material.setReflectance(reflectance);
    }
}
