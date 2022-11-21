package Collision;

/**
 * @author xiaosongChen
 * @create 2022-11-21 15:50
 * @description :使用Bullet物理引擎来进行碰撞检测。
 * 运行此程序，你将看到一个绿色小球从高处落下，然后滚到地板边缘，最后“掉出”这个世界。
 * 不弹
 */

import AppState.AxisAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.MeshCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.shape.Sphere;

public class TestSolidFloor_Bullet extends SimpleApplication {

    @Override
    public void simpleInitApp() {
        stateManager.attach(new AxisAppState());

        cam.setLocation(new Vector3f(0f, 16f, 20f));
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);

        viewPort.setBackgroundColor(ColorRGBA.White);

        /**
         * 场景
         */
        // 方块
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Green);
        Geometry sphere = new Geometry("Green", new Sphere(8, 8, 1));
        sphere.setMaterial(mat);
        sphere.move(-10, 0, 0);

        // 地板
        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Pink);

        Geometry floor = new Geometry("Floor", new Quad(12, 4));
        floor.rotate(-FastMath.HALF_PI, 0f, 0f);
        floor.move(-10, 0, 2);
        floor.setMaterial(mat);

        rootNode.attachChild(sphere);
        rootNode.attachChild(floor);

        /**
         * 物理引擎
         */
        BulletAppState bullet = new BulletAppState();
        stateManager.attach(bullet);

        PhysicsSpace space = bullet.getPhysicsSpace();

        // 地板
        // 刚体控件(形状, 质量)。质量为0将不受任何力的影响，适合当做地面。
        //RigidBodyControl()一个物理控件，将PhysicsRigidBody链接到一个空间。
        //MeshCollisionShape()基于指定的JME网格实例化一个碰撞形状，优化内存使用。
        RigidBodyControl rigid = new RigidBodyControl(new MeshCollisionShape(new Quad(12, 4)), 0);
        floor.addControl(rigid);
        space.add(rigid);

        // 球体
        rigid = new RigidBodyControl(new SphereCollisionShape(1f), 3f);
        sphere.addControl(rigid);
        space.add(rigid);

        rigid.setLinearVelocity(new Vector3f(2, 8, 0));// 线速度
        rigid.setGravity(new Vector3f(0, -9.8f, 0));// 重力加速度
    }

    public static void main(String[] args) {
        TestSolidFloor_Bullet app = new TestSolidFloor_Bullet();
        app.setShowSettings(false);
        app.start();
    }

}