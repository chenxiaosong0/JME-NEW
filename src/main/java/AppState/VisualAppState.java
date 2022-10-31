package AppState;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * @author xiaosongChen
 * @create 2022-10-21 10:37
 * @description :initialize() 方法用于初始化场景，update(float tpf) 方法用于控制方块旋转。
 */
public class VisualAppState implements AppState {
    private boolean initialized = false;
    private boolean enabled = true;
    /**
     * 创建一个独立的根节点，便于管理子场景。
     */
    private Node sceneNode = new Node("MyScene");

    private Geometry cube = null;

    /**
     * 对于那些我们用得上的系统对象，保存一份对象的引用。
     */
    private SimpleApplication simpleApp;
    private AssetManager assetManager;
    @Override
    public void initialize(AppStateManager appStateManager, Application app) {
        this.simpleApp = (SimpleApplication) app;
        this.assetManager = app.getAssetManager();
        // 创建一个方块
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setColor("Diffuse", ColorRGBA.Red);
        mat.setColor("Ambient", ColorRGBA.Red);
        mat.setColor("Specular", ColorRGBA.Black);
        mat.setFloat("Shininess", 1);
        mat.setBoolean("UseMaterialColors", true);

        Mesh mesh = new Box(1, 1, 1);

        cube = new Geometry("Cube", mesh);
        cube.setMaterial(mat);
        // 将方块添加到我们这个场景中。
        sceneNode.attachChild(cube);
        // 初始化完毕
        initialized = true;
        if (enabled){
            simpleApp.getRootNode().attachChild(sceneNode);
        }else {
            sceneNode.removeFromParent();
        }
    }

    @Override
    public boolean isInitialized() {
        return false;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setEnabled(boolean b) {

    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void stateAttached(AppStateManager appStateManager) {

    }

    @Override
    public void stateDetached(AppStateManager appStateManager) {

    }

    @Override
    public void update(float v) {
        cube.rotate(0,v * FastMath.PI,0);
    }

    @Override
    public void render(RenderManager renderManager) {

    }

    @Override
    public void postRender() {

    }

    @Override
    public void cleanup() {
        if (enabled){
            sceneNode.removeFromParent();
        }
        initialized = false;
    }
}
