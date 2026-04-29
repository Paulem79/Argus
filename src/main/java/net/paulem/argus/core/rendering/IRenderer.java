package net.paulem.argus.core.rendering;

import net.paulem.argus.core.entity.Camera;
import net.paulem.argus.core.entity.Model;
import net.paulem.argus.core.lightning.DirectionalLight;
import net.paulem.argus.core.lightning.PointLight;
import net.paulem.argus.core.lightning.SpotLight;

public interface IRenderer<T> {
    void init() throws Exception;

    void render(Camera camera, PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight);

    void bind(Model model);

    void unbind();

    void prepare(T t, Camera camera);

    void cleanup();
}
