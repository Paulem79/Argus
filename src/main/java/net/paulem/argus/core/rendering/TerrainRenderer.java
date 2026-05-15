package net.paulem.argus.core.rendering;

import lombok.Getter;
import net.paulem.argus.core.Argus;
import net.paulem.argus.core.entity.Camera;
import net.paulem.argus.core.entity.Entity;
import net.paulem.argus.core.entity.Model;
import net.paulem.argus.core.entity.terrain.Terrain;
import net.paulem.argus.core.lightning.DirectionalLight;
import net.paulem.argus.core.lightning.PointLight;
import net.paulem.argus.core.lightning.SpotLight;
import net.paulem.argus.core.managers.EngineManager;
import net.paulem.argus.core.managers.ShaderManager;
import net.paulem.argus.utils.Constants;
import net.paulem.argus.utils.Transformation;
import net.paulem.argus.utils.Utils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.paulem.argus.core.rendering.RenderManager.renderLights;

public class TerrainRenderer implements IRenderer<Terrain> {
    ShaderManager shader;
    @Getter
    private List<Terrain> terrains;

    public TerrainRenderer() throws Exception {
        terrains = new ArrayList<>();
        shader = new ShaderManager();
    }

    @Override
    public void init() throws Exception {
        shader.createVertexShader(Utils.loadResource("/shaders/terrain_vertex.vert"));
        shader.createFragmentShader(Utils.loadResource("/shaders/terrain_fragment.frag"));
        shader.link();
        shader.createUniform("backgroundTexture");
        shader.createUniform("redTexture");
        shader.createUniform("greenTexture");
        shader.createUniform("blueTexture");
        shader.createUniform("blendMap");
        shader.createUniform("transformationMatrix");
        shader.createUniform("projectionMatrix");
        shader.createUniform("viewMatrix");
        shader.createUniform("ambientLight");
        shader.createMaterialUniform("material");
        shader.createUniform("specularPower");
        shader.createDirectionalLightUniform("directionalLight");
        shader.createPointLightListUniform("pointLights", Constants.MAX_POINT_LIGHTS);
        shader.createSpotLightListUniform("spotLights", Constants.MAX_SPOT_LIGHTS);
    }

    @Override
    public void render(Camera camera, PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight) {
        shader.bind();
        shader.setUniform("projectionMatrix", Argus.INSTANCE.getWindow().updateProjectionMatrix());
        renderLights(shader, directionalLight, pointLights, spotLights);

        for (Terrain terrain : terrains) {
            bind(terrain.getModel());
            prepare(terrain, camera);
            GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            unbind();
        }

        terrains.clear();

        shader.unbind();
    }

    @Override
    public void bind(Model model) {
        GL30.glBindVertexArray(model.getId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        shader.setUniform("backgroundTexture", 0);
        shader.setUniform("redTexture", 1);
        shader.setUniform("greenTexture", 2);
        shader.setUniform("blueTexture", 3);
        shader.setUniform("blendMap", 4);

        shader.setUniform("material", model.getMaterial());
    }

    @Override
    public void unbind() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    @Override
    public void prepare(Terrain terrain, Camera camera) {
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getBlendMapTerrain().getBackground().getId());
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getBlendMapTerrain().getRedTexture().getId());
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getBlendMapTerrain().getGreenTexture().getId());
        GL13.glActiveTexture(GL13.GL_TEXTURE3);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getBlendMapTerrain().getBlueTexture().getId());
        GL13.glActiveTexture(GL13.GL_TEXTURE4);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getBlendMap().getId());

        shader.setUniform("transformationMatrix", Transformation.createTransformationMatrix(terrain));
        shader.setUniform("viewMatrix", Transformation.getViewMatrix(camera));
    }

    @Override
    public void cleanup() {
        shader.cleanup();
    }
}
