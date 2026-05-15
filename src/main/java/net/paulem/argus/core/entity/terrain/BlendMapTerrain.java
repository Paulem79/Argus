package net.paulem.argus.core.entity.terrain;

import lombok.Getter;

public class BlendMapTerrain {
    @Getter
    TerrainTexture background, redTexture, greenTexture, blueTexture;

    public BlendMapTerrain(TerrainTexture background, TerrainTexture redTexture, TerrainTexture greenTexture, TerrainTexture blueTexture) {
        this.background = background;
        this.redTexture = redTexture;
        this.greenTexture = greenTexture;
        this.blueTexture = blueTexture;
    }
}
