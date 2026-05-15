package net.paulem.argus.core.entity.terrain;

import lombok.Getter;

public class TerrainTexture {
    @Getter
    private final int id;

    public TerrainTexture(int id) {
        this.id = id;
    }
}
