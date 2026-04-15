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
    private Texture texture;

    public Model(int id, int vertexCount) {
        this(id, vertexCount, null);
    }

    public Model(int id, int vertexCount, Texture texture) {
        this.id = id;
        this.vertexCount = vertexCount;
        this.texture = texture;
    }

    public Model(Model model, Texture texture) {
        this(model.getId(), model.getVertexCount(), texture);
    }
}
