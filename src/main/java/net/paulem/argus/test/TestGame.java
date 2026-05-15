package net.paulem.argus.test;

import net.paulem.argus.core.*;
import net.paulem.argus.core.entity.*;
import net.paulem.argus.core.entity.terrain.BlendMapTerrain;
import net.paulem.argus.core.entity.terrain.Terrain;
import net.paulem.argus.core.entity.terrain.TerrainTexture;
import net.paulem.argus.core.lightning.DirectionalLight;
import net.paulem.argus.core.lightning.PointLight;
import net.paulem.argus.core.lightning.SpotLight;
import net.paulem.argus.core.rendering.RenderManager;
import net.paulem.argus.core.managers.WindowManager;
import net.paulem.argus.utils.Constants;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestGame implements ILogic {
    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final WindowManager window;

    private Camera camera;

    private SceneManager sceneManager;

    Vector3f cameraInc;

    public TestGame() {
        this.renderer = new RenderManager();
        this.window = Argus.INSTANCE.getWindow();
        this.loader = new ObjectLoader();
        this.camera = new Camera();
        this.cameraInc = new Vector3f();
        sceneManager = new SceneManager(-90f);
    }

    @Override
    public void init() throws Exception {
        renderer.init();

        Model model = loader.loadOBJModel("/models/cow.obj"); //loader.loadModel(vertices, textureCoords, indices);
        model.setTexture(new Texture(loader.loadTexture("/textures/cow.jpg")), 1f);

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("/textures/terrain.png"));
        TerrainTexture redTexture = new TerrainTexture(loader.loadTexture("/textures/flowers.png"));
        TerrainTexture greenTexture = new TerrainTexture(loader.loadTexture("/textures/stone.png"));
        TerrainTexture blueTexture = new TerrainTexture(loader.loadTexture("/textures/dirt.png"));
        TerrainTexture blendTexture = new TerrainTexture(loader.loadTexture("/textures/blendMap.png"));

        BlendMapTerrain blendMapTerrain = new BlendMapTerrain(backgroundTexture, redTexture, greenTexture, blueTexture);

        Terrain terrain = new Terrain(new Vector3f(0, 1, -800), loader, new Material(new Vector4f(0, 0, 0, 0), 0.1f), blendTexture, blendMapTerrain);
        Terrain terrain2 = new Terrain(new Vector3f(-800, 1, -800), loader, new Material(new Vector4f(0, 0, 0, 0), 0.1f), blendTexture, blendMapTerrain);
        sceneManager.addTerrain(terrain); sceneManager.addTerrain(terrain2);

        Random rand = new Random();
        for(int i = 0; i < 10; i++) {
            float x = rand.nextFloat() * 100 - 50;
            float y = rand.nextFloat() * 100 - 50;
            float z = rand.nextFloat() * -300;
            sceneManager.addEntity(new Entity(model, new Vector3f(x, y, z), new Vector3f(rand.nextFloat() * 180, rand.nextFloat() * 180, 0), 5));
        }
        sceneManager.addEntity(new Entity(model, new Vector3f(0, 0, -2f), new Vector3f(0, 0, 0), 5));

        float lightIntensity = 1.0f;
        // Point light
        Vector3f pointLightPosition = new Vector3f(0, 0, -3.2f);
        Vector3f pointLightColor = new Vector3f(1, 1, 1);
        PointLight pointLight = new PointLight(pointLightColor, pointLightPosition, lightIntensity, 0, 0, 1);

        // Spot light
        Vector3f coneDirection = new Vector3f(0, 0, 1);
        float cutoff = (float) Math.cos(Math.toRadians(180));
        SpotLight spotLight = new SpotLight(new PointLight(pointLightColor, new Vector3f(0, 0, 1), lightIntensity, 0, 0, 1), coneDirection, cutoff);

        SpotLight spotLight1 = new SpotLight(spotLight);

        // Directional light
        Vector3f lightPosition = new Vector3f(1, -10, 0);
        Vector3f lightColor = new Vector3f(1, 1, 1);
        DirectionalLight directionalLight = new DirectionalLight(lightColor, lightPosition, lightIntensity);
        sceneManager.setDirectionalLight(directionalLight);

        PointLight[] pointLights = new PointLight[] { pointLight };
        sceneManager.setPointLights(pointLights);

        SpotLight[] spotLights = new SpotLight[] { spotLight, spotLight1 };
        sceneManager.setSpotLights(spotLights);
    }

    @Override
    public void input() {
        cameraInc.set(0, 0, 0);

        if(window.isKeyPressed(GLFW.GLFW_KEY_W)) {
            cameraInc.z = -1;
        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_S)) {
            cameraInc.z = 1;
        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_A)) {
            cameraInc.x = -1;
        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_D)) {
            cameraInc.x = 1;
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            cameraInc.y = 1;
        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            cameraInc.y = -1;
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_O)) {
            sceneManager.getPointLights()[0].getPosition().x += 0.1f;
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_P)) {
            sceneManager.getPointLights()[0].getPosition().x -= 0.1f;
        }

        float lightPos = sceneManager.getSpotLights()[0].getPointLight().getPosition().x;
        if(window.isKeyPressed(GLFW.GLFW_KEY_N)) {
            sceneManager.getSpotLights()[0].getPointLight().getColor().z = lightPos + 0.1f;
        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_M)) {
            sceneManager.getSpotLights()[0].getPointLight().getColor().z = lightPos - 0.1f;
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {
        camera.movePosition(cameraInc.x * Constants.CAMERA_MOVE_SPEED, cameraInc.y * Constants.CAMERA_MOVE_SPEED, cameraInc.z * Constants.CAMERA_MOVE_SPEED);

        if(mouseInput.isRightButtonPress()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * Constants.MOUSE_SENSITIVITY, rotVec.y * Constants.MOUSE_SENSITIVITY, 0);
        }

        // Daylight cycle
        /*lightAngle += 0.5f;
        if(lightAngle > 90) {
            directionalLight.setIntensity(0.0f);
            if(lightAngle >= 360) {
                lightAngle = -90;
            }
        } else if(lightAngle <= -80 || lightAngle >= 80) {
            float factor = 1 - (Math.abs(lightAngle) - 80) / 10.0f;
            directionalLight.setIntensity(factor);
            directionalLight.getColor().y = Math.max(factor, 0.9f);
            directionalLight.getColor().z = Math.max(factor, 0.5f);
        } else {
            directionalLight.setIntensity(1.0f);
            directionalLight.getColor().set(1);
        }
        double angRad = Math.toRadians(lightAngle);
        directionalLight.getDirection().x = (float) Math.sin(angRad);
        directionalLight.getDirection().y = (float) Math.cos(angRad);*/

        for (Entity entity : sceneManager.getEntities()) {
            renderer.processEntity(entity);
        }

        for (Terrain terrain : sceneManager.getTerrains()) {
            renderer.processTerrain(terrain);
        }
    }

    @Override
    public void render() {
        if(window.isResized()) {
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(true);
        }

        renderer.render(camera, sceneManager);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        loader.cleanup();
    }
}
