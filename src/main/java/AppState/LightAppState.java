package AppState;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;

/**
 * @author xiaosongChen
 * @create 2022-10-21 10:52
 * @description :在 initialize() 中初始化了光源
 */
public class LightAppState implements AppState {
    private boolean initialized = false;
    private boolean enabled = true;

    // 点光源
    private Vector3f lightPos;
    private ColorRGBA pointLightColor;
    private PointLight pointLight;

    // 环境光
    private ColorRGBA ambientLightColor = new ColorRGBA(0.2f, 0.2f, 0.2f, 1f);
    private AmbientLight ambientLight;

    // 背景色
    private ColorRGBA bgColor = new ColorRGBA(0.7f, 0.8f, 0.85f, 1f);

    /**
     * 灯光应该引用到整个场景中，所以需要保存SimpleApplication中的根节点。
     */
    private Node rootNode;
    @Override
    public void initialize(AppStateManager appStateManager, Application application) {
        SimpleApplication simpleApplication = (SimpleApplication) application;
        this.rootNode = simpleApplication.getRootNode();
        //创建光源
        lightPos = new Vector3f(1,2,3);
        pointLightColor = new ColorRGBA(0.8f,0.8f,0.0f,1f);
        pointLight = new PointLight(lightPos, pointLightColor);

        ambientLightColor = new ColorRGBA(0.2f, 0.2f, 0.2f, 1f);
        ambientLight = new AmbientLight(ambientLightColor);
        // 设置背景色，在关灯之后你依然能看到场景中漆黑的物体。
        application.getViewPort().setBackgroundColor(bgColor);
        // 初始化完毕
        initialized = true;
        if (enabled){
            turnOn();
        }
    }
    // 开灯
    public void turnOn() {
        rootNode.addLight(pointLight);
        rootNode.addLight(ambientLight);
    }
    // 关灯
    public void turnOff() {
        rootNode.removeLight(pointLight);
        rootNode.removeLight(ambientLight);
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
        if (this.enabled == b){
            return;
        }
        this.enabled = b;
        if (!initialized){
            return;
        }
        if (enabled){
            turnOn();
        }else {
            turnOff();
        }
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
            turnOff();
        }
        initialized = false;
    }
}
