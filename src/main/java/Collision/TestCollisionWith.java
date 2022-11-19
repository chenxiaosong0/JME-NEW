package Collision;

/**
 * @author xiaosongChen
 * @create 2022-11-19 16:41
 * @description :基于包围体的碰撞检测。
 */

import AppState.AxisAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingSphere;
import com.jme3.collision.CollisionResults;
import com.jme3.input.RawInputListener;
import com.jme3.input.event.*;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

public class TestCollisionWith extends SimpleApplication implements RawInputListener {

    private Geometry green;
    private Geometry pink;

    public static void main(String[] args) {
        TestCollisionWith app = new TestCollisionWith();
        app.start();
    }

    public TestCollisionWith() {
        super(new FlyCamAppState());//,new AxisAppState()
    }

    @Override
    public void simpleInitApp() {
        flyCam.setDragToRotate(true);
        cam.setLocation(new Vector3f(0f, 18f, 22f));
//        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        cam.setRotation(new Quaternion(-0.038325474f, 0.96150225f, -0.20146479f, -0.18291113f));
//        flyCam.setMoveSpeed(10);
        viewPort.setBackgroundColor(ColorRGBA.White);
        // 参考坐标系
        stateManager.attach(new AxisAppState());
        // 绿色物体
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Green);

        green = new Geometry("Green", new Sphere(6, 12, 1));
        green.setMaterial(mat);
        green.setModelBound(new BoundingSphere());//设置此几何图形绑定使用的模型: 使用包围球
        green.updateModelBound();// 更新包围球

        // 粉色物体
        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Pink);
        pink = new Geometry("Pink", new Box(1, 1, 1));
        pink.setMaterial(mat);

        rootNode.attachChild(green);
        rootNode.attachChild(pink);

        inputManager.addRawInputListener(this);
    }

    @Override
    public void onMouseMotionEvent(MouseMotionEvent evt) {
        // 根据鼠标的位置来改变绿色方块的坐标，并将其限制在 (-10, -10) 到 (10, 10)的范围内。
        float x = evt.getX();
        float y = evt.getY();
        System.out.println("x = " + x + " y = " + y);
        // 坐标系大小为 20 * 20
        x = x * 20 / cam.getWidth() - 10;
        y = y * 20 / cam.getHeight() - 10;
        green.setLocalTranslation(x, 0, -y);

        // 碰撞检测
        CollisionResults results = new CollisionResults();
        pink.collideWith(green.getWorldBound(), results);

        if (results.size() > 0) {
            green.getMaterial().setColor("Color", ColorRGBA.Red);
        } else {
            green.getMaterial().setColor("Color", ColorRGBA.Green);
        }
    }

    @Override public void beginInput() {}
    @Override public void endInput() {}
    @Override public void onJoyAxisEvent(JoyAxisEvent evt) {}
    @Override public void onJoyButtonEvent(JoyButtonEvent evt) {}
    @Override public void onMouseButtonEvent(MouseButtonEvent evt) {}
    @Override public void onKeyEvent(KeyInputEvent evt) {}
    @Override public void onTouchEvent(TouchEvent evt) {}

}
