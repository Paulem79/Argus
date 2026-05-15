package net.paulem.argus.core.entity;

import lombok.Getter;
import lombok.Setter;
import net.paulem.argus.core.entity.terrain.Terrain;
import net.paulem.argus.core.lightning.DirectionalLight;
import net.paulem.argus.core.lightning.PointLight;
import net.paulem.argus.core.lightning.SpotLight;
import net.paulem.argus.utils.Constants;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SceneManager {
    private List<Entity> entities;
    private List<Terrain> terrains;

    private Vector3f ambientLight;
    private SpotLight[] spotLights;
    private PointLight[] pointLights;
    private DirectionalLight directionalLight;

    private float lightAngle;
    private float spotAngle = 0;
    private float spotInc = 1;

    public SceneManager(float lightAngle) {
        entities = new ArrayList<>();
        terrains = new ArrayList<>();
        ambientLight = Constants.AMBIENT_LIGHT;
        this.lightAngle = lightAngle;
    }

    public void setAmbientLight(float r, float g, float b) {
        ambientLight.set(r, g, b);
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void addTerrain(Terrain terrain) {
        terrains.add(terrain);
    }
}
