package net.paulem.argus.core.rendering;

import lombok.Getter;
import net.paulem.argus.core.Argus;
import net.paulem.argus.core.entity.Camera;
import net.paulem.argus.core.entity.Entity;
import net.paulem.argus.core.entity.Model;
import net.paulem.argus.core.lightning.DirectionalLight;
import net.paulem.argus.core.lightning.PointLight;
import net.paulem.argus.core.lightning.SpotLight;
import net.paulem.argus.core.managers.EngineManager;
import net.paulem.argus.core.managers.ShaderManager;
import net.paulem.argus.utils.Transformation;
import net.paulem.argus.utils.Utils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.paulem.argus.core.rendering.RenderManager.renderLights;

public class EntityRenderer implements IRenderer<Entity> {
    ShaderManager shader;
    @Getter
    private Map<Model, List<Entity>> entities;

    public EntityRenderer() throws Exception {
        entities = new HashMap<>();
        shader = new ShaderManager();
    }

    @Override
    public void init() throws Exception {
        shader.createVertexShader(Utils.loadResource("/shaders/entity_vertex.vert"));
        shader.createFragmentShader(Utils.loadResource("/shaders/entity_fragment.frag"));
        shader.link();
        shader.createUniform("textureSampler");
        shader.createUniform("transformationMatrix");
        shader.createUniform("projectionMatrix");
        shader.createUniform("viewMatrix");
        shader.createUniform("ambientLight");
        shader.createMaterialUniform("material");
        shader.createUniform("specularPower");
        shader.createDirectionalLightUniform("directionalLight");
        shader.createPointLightListUniform("pointLights", 5);
        shader.createSpotLightListUniform("spotLights", 5);
    }

    @Override
    public void render(Camera camera, PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight) {
        shader.bind();
        shader.setUniform("projectionMatrix", Argus.INSTANCE.getWindow().updateProjectionMatrix());
        renderLights(shader, directionalLight, pointLights, spotLights);

        int triangles = 0;
        int vertices = 0;

        for (Model model : entities.keySet()) {
            bind(model);
            List<Entity> entities = this.entities.get(model);
            for (Entity entity : entities) {
                prepare(entity, camera);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
                vertices += model.getVertexCount();
                triangles += model.getVertexCount() / 3;
            }
            unbind();
        }

        EngineManager.setRenderedTriangles(triangles);
        EngineManager.setRenderedVertices(vertices);

        entities.clear();

        shader.unbind();
    }

    @Override
    public void bind(Model model) {
        GL30.glBindVertexArray(model.getId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        shader.setUniform("material", model.getMaterial());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getMaterial().getTexture().getId());
    }

    @Override
    public void unbind() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    @Override
    public void prepare(Entity entity, Camera camera) {
        shader.setUniform("textureSampler", 0);
        shader.setUniform("transformationMatrix", Transformation.createTransformationMatrix(entity));
        shader.setUniform("viewMatrix", Transformation.getViewMatrix(camera));
    }

    @Override
    public void cleanup() {
        shader.cleanup();
    }
}
