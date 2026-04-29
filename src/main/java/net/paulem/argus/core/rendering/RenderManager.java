package net.paulem.argus.core.rendering;

import net.paulem.argus.core.entity.Camera;
import net.paulem.argus.core.entity.Entity;
import net.paulem.argus.core.entity.Model;
import net.paulem.argus.core.lightning.DirectionalLight;
import net.paulem.argus.core.lightning.PointLight;
import net.paulem.argus.core.lightning.SpotLight;
import net.paulem.argus.core.managers.ShaderManager;
import net.paulem.argus.utils.Constants;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class RenderManager {
    private EntityRenderer entityRenderer;

    public RenderManager() {
    }

    public void init() throws Exception {
        entityRenderer = new EntityRenderer();

        entityRenderer.init();
    }

    public static void renderLights(ShaderManager shader, DirectionalLight directionalLight, PointLight[] pointLights, SpotLight[] spotLights) {
        shader.setUniform("ambientLight", Constants.AMBIENT_LIGHT);
        shader.setUniform("specularPower", Constants.SPECULAR_POWER);

        int numLights = pointLights != null ? pointLights.length : 0;
        for(int i = 0; i < numLights; i++) {
            shader.setUniform("pointLights", pointLights[i], i);
        }

        numLights = spotLights != null ? spotLights.length : 0;
        for(int i = 0; i < numLights; i++) {
            shader.setUniform("spotLights", spotLights[i], i);
        }

        shader.setUniform("directionalLight", directionalLight);
    }

    public void render(Camera camera, DirectionalLight directionalLight, PointLight[] pointLights, SpotLight[] spotLights) {
        clear();

        entityRenderer.render(camera, pointLights, spotLights, directionalLight);
    }

    public void processEntity(Entity entity) {
        List<Entity> entities = entityRenderer.getEntities().get(entity.getModel());
        if(entities != null) {
            entities.add(entity);
        } else {
            entities = new ArrayList<>();
            entities.add(entity);

            entityRenderer.getEntities().put(entity.getModel(), entities);
        }
    }

    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void cleanup() {
        entityRenderer.cleanup();
    }
}
