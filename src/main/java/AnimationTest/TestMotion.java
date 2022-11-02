package AnimationTest;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.SimpleApplication;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.*;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;

/**
 * @author xiaosongChen
 * @create 2022-10-28 22:28
 * @description:运动路径演示
 * :P: 隐藏/显示路线
 * I: 切换路线类型（直线/曲线）
 * 空格：开始/停止移动
 * U: 增加曲率（仅曲线模式下有效）
 * J: 减少曲率（仅曲线模式下有效）
 */
public class TestMotion extends SimpleApplication {

    public static void main(String[] args) {
        // 启动程序
        TestMotion app = new TestMotion();
        app.start();
    }

    /**
     * 缩放系数
     */
    final static float SCALE_FACTOR = 5f;

    private Spatial player;// 玩家
    private Spatial stage;// 舞台

    private boolean active = true;
    private boolean playing = false;

    private MotionPath motionPath;
    private MotionEvent motionControl;
    private MotionEvent cammotionControl;
    private CameraNode camNode;//运动相机节点
    private ChaseCamera chaseCamera;//追踪相机

    @Override
    public void simpleInitApp() {

        flyCam.setMoveSpeed(10f);
        initLights();

        initInputs();

        initMotionPath();

        stage = assetManager.loadModel("Models/Stage/Stage.j3o");
        stage.scale(SCALE_FACTOR);
        rootNode.attachChild(stage);

        player = assetManager.loadModel("Models/Jaime/Jaime.j3o");
        rootNode.attachChild(player);

        AnimControl ac = player.getControl(AnimControl.class);
        AnimChannel channel = ac.createChannel();
        channel.setAnim("Run");
        channel.setSpeed(1f);

         camNode = new CameraNode("Motion cam", cam);//摄像机
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        camNode.setEnabled(false);
        cammotionControl = new MotionEvent(camNode , motionPath);
        motionControl = new MotionEvent(player, motionPath);//猴子
//        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));

        cammotionControl.setLookAt(player.getParent().getWorldTranslation(), Vector3f.UNIT_Y);
        cammotionControl.setDirectionType(MotionEvent.Direction.Path);
        rootNode.attachChild(camNode);
        //追踪相机
        chaseCamera =new ChaseCamera(cam,player);
        chaseCamera.registerWithInput(inputManager);
        chaseCamera.setSmoothMotion(false);//设置丝滑与否
        chaseCamera.setMaxDistance(50);//设置距离
        chaseCamera.setDefaultDistance(10);
//        cammotionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Z));

        // 在行进中，注视某个位置
//		Vector3f position = new Vector3f(0, 0, 0);
//		motionControl.setLookAt(position, Vector3f.UNIT_Y);
//		motionControl.setDirectionType(MotionEvent.Direction.LookAt);

        // 在行进中，面朝固定方向
//		Quaternion rotation = new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y);
//		motionControl.setRotation(rotation);
//		motionControl.setDirectionType(Direction.Rotation);

        // 在行进中，面朝前进方向
        motionControl.setDirectionType(MotionEvent.Direction.Path);

        // 设置走完全程所需的时间（单位：秒）。
        motionControl.setInitialDuration(10f);
    }

    /**
     * 初始化光源
     */
    private void initLights() {
        AmbientLight ambient = new AmbientLight(new ColorRGBA(0.4f, 0.4f, 0.4f, 1f));

        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(0.6486864f, -0.72061276f, 0.24479222f));
        sun.setColor(new ColorRGBA(0.8f, 0.8f, 0.8f, 1f));

        rootNode.addLight(ambient);
        rootNode.addLight(sun);
    }

    /**
     * 路径点数据
     */
    final static float[] WayPoints = {
            1.3660254f, -1f, 0.5f,// 0
            0.8660254f, -1f, 0.5f,// 1
            -0.8660254f, 0f, 0.5f,// 2
            -1.3660254f, 0f, 0.5f,// 3
            -1.3660254f, 0f, -0.5f,// 4
            -0.8660254f, 0f, -0.5f,// 5
            0.8660254f, 1f, -0.5f,// 6
            1.3660254f, 1f, -0.5f// 7
    };

    /**
     * 建造路径点
     */
    private void initMotionPath() {

        motionPath = new MotionPath();

        // 路径点个数
        int count = WayPoints.length / 3;

        for(int i = 0; i < count; i++) {
            // 按比例放大顶点坐标
            int n = i * 3;
            float x = SCALE_FACTOR * WayPoints[n];
            float y = SCALE_FACTOR * WayPoints[n + 1];
            float z = SCALE_FACTOR * WayPoints[n + 2];

            motionPath.addWayPoint(new Vector3f(x, y, z));
        }
        //设置路径为直线
        motionPath.setPathSplineType(Spline.SplineType.Linear);
        //显示路径
        motionPath.enableDebugShape(assetManager, rootNode);

    }

    /**
     * 初始化输入
     */
    private void initInputs() {
        inputManager.addMapping("display_hidePath", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("SwitchPathInterpolation", new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping("tensionUp", new KeyTrigger(KeyInput.KEY_U));
        inputManager.addMapping("tensionDown", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("play_stop", new KeyTrigger(KeyInput.KEY_SPACE));
        ActionListener acl = new ActionListener() {

            public void onAction(String name, boolean keyPressed, float tpf) {
                if (name.equals("display_hidePath") && keyPressed) {
                    if (active) {
                        active = false;
                        motionPath.disableDebugShape();
                    } else {
                        active = true;
                        motionPath.enableDebugShape(assetManager, rootNode);
                    }
                }

                if (name.equals("play_stop") && keyPressed) {
                    if (playing) {
                        playing = false;
//                        chaseCamera.setEnabled(true);
//                        camNode.setEnabled(false);
                        motionControl.stop();
//                        cammotionControl.stop();
                    } else {
                        playing = true;
//                        chaseCamera.setEnabled(true);
//                        camNode.setEnabled(true);
                        motionControl.play();
//                        cammotionControl.play();
                    }
                }

                if (name.equals("SwitchPathInterpolation") && keyPressed) {
                    if (motionPath.getPathSplineType() == Spline.SplineType.CatmullRom){
                        motionPath.setPathSplineType(Spline.SplineType.Linear);
                    } else {
                        motionPath.setPathSplineType(Spline.SplineType.CatmullRom);
                    }
                }

                if (name.equals("tensionUp") && keyPressed) {
                    motionPath.setCurveTension(motionPath.getCurveTension() + 0.1f);
                    System.err.println("Tension : " + motionPath.getCurveTension());
                }
                if (name.equals("tensionDown") && keyPressed) {
                    motionPath.setCurveTension(motionPath.getCurveTension() - 0.1f);
                    System.err.println("Tension : " + motionPath.getCurveTension());
                }


            }
        };

        inputManager.addListener(acl, "display_hidePath", "play_stop", "SwitchPathInterpolation", "tensionUp", "tensionDown");
    }

}
