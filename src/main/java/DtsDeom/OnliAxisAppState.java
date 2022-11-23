package DtsDeom;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.shape.Sphere;

/**
 * @author xiaosongChen
 * @create 2022-10-21 15:51
 * @description :坐标系
 */
public class OnliAxisAppState extends BaseAppState implements ActionListener {


    public final static String TOGGLE_AXIS = "toggle_axis";

    private AssetManager assetManager;

    private Node rootNode = new Node("AxisRoot");
    private Geometry grid;
    private Node AxisNode = new Node("AxisNode") ;


    public Node getAxisNode() {
//        rootNode.detachChild(grid);
        return AxisNode;
    }

    @Override
    protected void initialize(Application app) {
        assetManager = app.getAssetManager();

        // 网格
//        grid = new Geometry("Grid", new Grid(21, 21, 1));
//        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//        mat.setColor("Color", ColorRGBA.DarkGray);
//        grid.setMaterial(mat);
//        grid.center().move(0, 0, 0);
//        grid.setShadowMode(RenderQueue.ShadowMode.Off);
//        rootNode.attachChild(grid);
        //坐标轴-原点
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.Black);
//        mat.getAdditionalRenderState().setWireframe(true);

        Geometry geom = new Geometry("flag", new Sphere(3, 3, 0.1f));
        geom.setMaterial(material);
        AxisNode.attachChild(geom);

        // 坐标
        createArrow("X", Vector3f.UNIT_X.mult(10), ColorRGBA.Red);
        createArrow("Y", Vector3f.UNIT_Y.mult(10), ColorRGBA.Green);
        createArrow("Z", Vector3f.UNIT_Z.mult(10), ColorRGBA.Blue);
        rootNode.attachChild(AxisNode);
//        toggleAxis();
    }
    public void AxisSpatial(){
        grid.removeFromParent();
    }
    public Node getRootNode() {
        return rootNode;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public Geometry getGrid() {
        return grid;
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
        SimpleApplication simpleApp = (SimpleApplication) getApplication();
        simpleApp.getRootNode().attachChild(rootNode);

        // 注册按键
        InputManager inputManager = getApplication().getInputManager();
        inputManager.addMapping(TOGGLE_AXIS, new KeyTrigger(KeyInput.KEY_F4));
        inputManager.addListener(this, TOGGLE_AXIS);
    }

    @Override
    protected void onDisable() {
        rootNode.removeFromParent();

        // 移除按键
        InputManager inputManager = getApplication().getInputManager();
        inputManager.removeListener(this);
        inputManager.deleteMapping(TOGGLE_AXIS);

    }

    @Override
    public void onAction(String name, boolean keyPressed, float tpf) {
        if (name.equals(TOGGLE_AXIS) && keyPressed) {
            toggleAxis();
        }
    }

    /**
     * 坐标轴开/关
     *
     * @return
     */
    public boolean toggleAxis() {
        SimpleApplication simpleApp = (SimpleApplication) getApplication();
        if (simpleApp.getRootNode().hasChild(rootNode)) {
            simpleApp.getRootNode().detachChild(rootNode);
            return false;
        } else {
            simpleApp.getRootNode().attachChild(rootNode);
            return true;
        }
    }

    /**
     * 创建一个箭头
     *
     * @param vec3
     *            箭头向量
     * @param color
     *            箭头颜色
     */
    private void createArrow(String name, Vector3f vec3, ColorRGBA color) {
        // 创建材质，设定箭头的颜色
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        mat.getAdditionalRenderState().setLineWidth(3f);
        mat.getAdditionalRenderState().setWireframe(true);

        // 创建几何物体，应用箭头网格。
        Geometry geom = new Geometry("Axis_" + name, new Arrow(vec3));
        geom.setMaterial(mat);
        geom.setShadowMode(RenderQueue.ShadowMode.Off);

        // 添加到场景中
        AxisNode.attachChild(geom);
    }
}
