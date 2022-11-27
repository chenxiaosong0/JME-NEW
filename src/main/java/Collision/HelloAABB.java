package Collision;

import AppState.AxisAppState;
import Control.FloatControl;
import Control.RotateControl;
import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingBox;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.TechniqueDef;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.WireBox;
import com.jme3.scene.shape.Cylinder;
import com.jme3.system.AppSettings;

/**
 * @author xiaosongChen
 * @create 2022-11-19 11:17
 * @description :演示轴对齐包围盒（Axis Align Bounding Box）
 */
public class HelloAABB extends SimpleApplication {

    private Geometry debug;
    private Geometry cylinder;
    private static HelloAABB helloAABB = new HelloAABB();

    @Override
    public void simpleInitApp() {
        // 初始化摄像机
        flyCam.setDragToRotate(true);
        AppSettings appSettings = new AppSettings(true);
        helloAABB.setShowSettings(false);
        helloAABB.setPauseOnLostFocus(false);
        renderManager.setPreferredLightMode(TechniqueDef.LightMode.SinglePass);
        renderManager.setSinglePassLightBatchSize(2);
        cam.setLocation(new Vector3f(4.5114727f, 6.176994f, 13.277485f));
        cam.setRotation(new Quaternion(-0.038325474f, 0.96150225f, -0.20146479f, -0.18291113f));
        flyCam.setMoveSpeed(10);

        viewPort.setBackgroundColor(ColorRGBA.LightGray);
        helloAABB.setSettings(appSettings);
        // 参考坐标系
        stateManager.attach(new AxisAppState());
        Node AxisNode = stateManager.getState(AxisAppState.class).getAxisNode();
        // 圆柱体
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setColor("Diffuse", ColorRGBA.Yellow);
        mat.setColor("Ambient", ColorRGBA.Yellow);
        mat.setColor("Specular", ColorRGBA.White);
        mat.setFloat("Shininess", 24);
        mat.setBoolean("UseMaterialColors", true);

        cylinder = new Geometry("cylinder", new Cylinder(2, 36, 1f, 8, true));
        cylinder.setMaterial(mat);
        // 让圆柱体运动，这样才能看到包围盒的变化。
        cylinder.addControl(new RotateControl(cylinder, FastMath.PI));//旋转运动
        cylinder.addControl(new FloatControl(2, 2));//上下运动

        // 用于显示包围盒
        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Magenta);
        mat.getAdditionalRenderState().setLineWidth(2);
//        mat.getAdditionalRenderState().setWireframe(true);//启用线框渲染模式   没什么效果
        debug = new Geometry("debug", new WireBox(1, 1, 1));
        debug.setMaterial(mat);


        rootNode.attachChild(cylinder);
        rootNode.attachChild(debug);

        // 光源
        rootNode.addLight(new DirectionalLight(new Vector3f(-1, -2, -3)));
        rootNode.addLight(new AmbientLight(new ColorRGBA(0.2f, 0.2f, 0.2f, 1f)));
    }

    @Override
    public void simpleUpdate(float tpf) {
        // 根据圆柱体当前的包围盒，更新线框的位置和大小。
        BoundingBox bbox = (BoundingBox) cylinder.getWorldBound();
        debug.setLocalScale(bbox.getExtent(null));
        debug.setLocalTranslation(bbox.getCenter());
    }

    public static void main(String[] args) {
        helloAABB.start();
    }

}
