package Control;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.CurveAndSurfaceMath;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.Control;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import javafx.scene.Camera;

/**
 * @author xiaosongChen
 * @create 2022-10-21 17:11
 * @description :JME addControl
 */
public class HelloControl extends SimpleApplication {

    @Override
    public Node getGuiNode() {
        System.out.println(this.guiNode);//Gui Node (Node)
        return super.getGuiNode();
    }

    public static void main(String[] args) {
        AppSettings appSettings = new AppSettings(true);
        appSettings.setRenderer("LWJGL-OpenGL3");//渲染器：LWJGL-OpenGL3
        appSettings.setCenterWindow(false);
        appSettings.setResizable(true);//设置可拖动窗口改变大小
        appSettings.setWindowXPosition(10);
        appSettings.setWindowYPosition(60);
        System.out.println(appSettings.getRenderer());
//        appSettings.setWindowYPosition(200);
        appSettings.setResolution(800,600);
        HelloControl control = new HelloControl();
        control.setShowSettings(false);
        control.setSettings(appSettings);
        control.start();
    }
    @Override
    public void simpleInitApp() {

        flyCam.setDragToRotate(true);
        cam.setLocation(new Vector3f(3.3435764f, 3.7595856f, 6.611723f));
        cam.setRotation(new Quaternion(-0.05573249f, 0.9440857f, -0.23910178f, -0.22006002f));
        //创建一个方块
        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        Geometry spatial = new Geometry("Box", new Box(1, 1, 1));
        spatial.setMaterial(material);

        //添加控件
//        spatial.addControl(new RotateControl(spatial,FastMath.PI));
//        spatial.addControl(new FloatControl(2,3));
//
//        Control control = spatial.getControl(0);
//        Control control1 = spatial.getControl(1);
//        FloatControl control2 = spatial.getControl(FloatControl.class);

//        System.out.println(control.toString());
//        System.out.println(control1.toString());
//        spatial.addControl(new MotionControl());
        rootNode.attachChild(spatial);
        //添加光源
        rootNode.addLight(new DirectionalLight(new Vector3f(-1,-2,-3)));
    }

    @Override
    public Node getRootNode() {
        return rootNode;
    }
}
