package net.paulem.argus.core.entity.terrain;

import lombok.Getter;
import lombok.Setter;
import net.paulem.argus.core.ObjectLoader;
import net.paulem.argus.core.entity.Material;
import net.paulem.argus.core.entity.Model;
import net.paulem.argus.core.entity.Texture;
import org.joml.Vector3f;

public class Terrain {

    private static final float SIZE = 800;
    private static final int VERTEX_COUNT = 128;

    @Getter
    @Setter
    private Vector3f position;
    @Getter
    @Setter
    private Model model;

    public Terrain(Vector3f position, ObjectLoader loader, Material material) {
        this.position = position;
        this.model = generateTerrain(loader);
        this.model.setMaterial(material);
    }

    private Model generateTerrain(ObjectLoader loader) {
        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
        int vertexPointer = 0;

        for (int i = 0; i < VERTEX_COUNT; i++) {
            for (int j = 0; j < VERTEX_COUNT; j++) {
                vertices[vertexPointer * 3] = j / (VERTEX_COUNT - 1.0f) * SIZE;
                vertices[vertexPointer * 3 + 1] = 0; // height map
                vertices[vertexPointer * 3 + 2] = i / (VERTEX_COUNT - 1.0f) * SIZE;
                normals[vertexPointer * 3] = 0;
                normals[vertexPointer * 3 + 1] = 1;
                normals[vertexPointer * 3 + 2] = 0;
                textureCoords[vertexPointer * 2] = j / (VERTEX_COUNT - 1.0f);
                textureCoords[vertexPointer * 2 + 1] = i / (VERTEX_COUNT - 1.0f);
                vertexPointer++;
            }
        }

        int pointer = 0;
        for (int z = 0; z < VERTEX_COUNT - 1; z++) {
            for (int x = 0; x < VERTEX_COUNT - 1; x++) {
                int topLeft = (z * VERTEX_COUNT) + x;
                int topRight = topLeft + 1;
                int bottomLeft = ((z + 1) * VERTEX_COUNT) + x;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }

        return loader.loadModel(vertices, normals, textureCoords, indices);
    }

    public Material getMaterial() {
        return model.getMaterial();
    }

    public Texture getTexture() {
        return getMaterial().getTexture();
    }
}
