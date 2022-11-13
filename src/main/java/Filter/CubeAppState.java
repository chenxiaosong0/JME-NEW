package Filter;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.util.SkyFactory;

/**
 * @author xiaosongChen
 * @create 2022-11-12 20:28
 * @description :
 */
public class CubeAppState extends BaseAppState {

    private Node rootNode = new Node("Scene root");

    private AmbientLight ambient;
    private PointLight point;
    private DirectionalLight sun;
    private Vector3f sunDirection = new Vector3f(-0.65093255f, -0.11788898f, 0.7499261f);

    private AssetManager assetManager;

    @Override
    protected void initialize(Application app) {

        this.assetManager = app.getAssetManager();

        FileLocator locator = new FileLocator();
        locator.setRootPath("D:\\develope_space\\study_space\\JME-NEW\\src\\main\\resources\\Textures\\Terrain\\Pond");
        // 创造地板
        Material mat = assetManager.loadMaterial("Textures/Terrain/Pond/Pond.j3m");

        Quad quad = new Quad(200, 200);
        quad.scaleTextureCoordinates(new Vector2f(20, 20));
        Geometry geom = new Geometry("Floor", quad);
        geom.setMaterial(mat);
        geom.rotate(-FastMath.HALF_PI, 0, 0);
        rootNode.attachChild(geom);

        float scalar = 20;
        float side = 3f;
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                geom = new Geometry("Cube", new Box(side, side*2, side));
                geom.setMaterial(getMaterial(new ColorRGBA(1 - x / 8f, y / 8f, 1f, 1f)));
                geom.move((x + 1) * scalar, side*2, -(y + 1) * scalar);
                rootNode.attachChild(geom);
            }
        }

        // 天空
        Spatial sky = SkyFactory.createSky(assetManager, "Scenes/Beach/FullskiesSunset0068.dds", SkyFactory.EnvMapType.CubeMap);
        sky.setLocalScale(350);
        rootNode.attachChild(sky);

        // 创造光源
        sun = new DirectionalLight();
        sun.setDirection(sunDirection);
        sun.setColor(new ColorRGBA(0.6f, 0.6f, 0.6f, 1f));

        ambient = new AmbientLight();
        ambient.setColor(new ColorRGBA(0.4f, 0.4f, 0.4f, 1f));

        point = new PointLight();
        point.setPosition(new Vector3f(100, 200, 100));
        point.setRadius(1000);
    }

    private Material getMaterial(ColorRGBA color) {
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setColor("Diffuse", color);
        mat.setColor("Ambient", color);
        mat.setColor("Specular", ColorRGBA.White);
        mat.setFloat("Shininess", 20f);
        mat.setBoolean("UseMaterialColors", true);

        return mat;
    }

    public Vector3f getSunDirection() {
        return sunDirection;
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
        SimpleApplication app = (SimpleApplication) getApplication();

        app.getRootNode().attachChild(rootNode);
        app.getRootNode().addLight(ambient);
        app.getRootNode().addLight(point);
        app.getRootNode().addLight(sun);
    }

    @Override
    protected void onDisable() {
        SimpleApplication app = (SimpleApplication) getApplication();

        app.getRootNode().detachChild(rootNode);
        app.getRootNode().removeLight(ambient);
        app.getRootNode().removeLight(point);
        app.getRootNode().removeLight(sun);
    }

}
