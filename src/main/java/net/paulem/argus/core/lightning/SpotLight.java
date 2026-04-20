package net.paulem.argus.core.lightning;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector3f;

@Getter
@Setter
public class SpotLight {
    private PointLight pointLight;
    private Vector3f coneDirection;
    private float cutoff;

    public SpotLight(PointLight pointLight, Vector3f coneDirection, float cutoff) {
        this.pointLight = pointLight;
        this.coneDirection = coneDirection;
        this.cutoff = cutoff;
    }

    public SpotLight(SpotLight spotLight) {
        this(spotLight.getPointLight(), spotLight.getConeDirection(), spotLight.getCutoff());
    }
}
