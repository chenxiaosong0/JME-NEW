package DtsDeom;

/**
 * @author xiaosongChen
 * @create 2022-11-16 22:36
 * @description :这个例子演示了游戏中常见的两种拾取模式。
 * 其一是第一人称视角，你通过准星来瞄准场景中的物体**；
 * 另一种是**第三人称视角（上帝视角），通过鼠标点击来选择场景中的物体。
 * 使用空格键来切换这两种模式。
 */

import Collision.CubeAppState;
import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingBox;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.material.TechniqueDef.LightMode;
import com.jme3.math.*;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;

/**
 * 利用射线检测，实现拾取。
 *
 * @author yanmaoyuan
 *
 */
public class SelectFunction extends SimpleApplication implements ActionListener {

    // 空格键：切换摄像机模式,鼠标左键：拾取
    public final static String CHANGE_CAM_MODE = "change_camera_mode",PICKING = "pick",CTRL = "ctrl";
    //
    private Node ClosestNode = new Node();
    private boolean ctrl = false;//是否多选
    // 准星
    private Spatial cross;
    // 拾取标记
    private Spatial flag;
    //射线检测到最近的几何体
    private Geometry closetGeom,LastGeom;;
    private CubeAppState cubeAppState = new CubeAppState();//场景
    private OnliAxisAppState axisAppState = new OnliAxisAppState();//坐标轴

    // 射线
    private Ray ray;

//    public static void main(String[] args) {
//        // 启动程序
//        SelectFunction app = new SelectFunction();
//        app.start();
//    }

    public SelectFunction() {
        super(new FlyCamAppState(), new DebugKeysAppState());

        // 初始化射线
        ray = new Ray();
        // 设置检测最远距离，可将射线变为线段。
         ray.setLimit(500);
    }

    @Override
    public void simpleInitApp() {

        // 初始化摄像机
        flyCam.setMoveSpeed(20f);
        cam.setLocation(new Vector3f(89.0993f, 20.044929f, -86.18647f));
        cam.setRotation(new Quaternion(0.063343525f, 0.18075047f, -0.01166729f, 0.9814177f));

        stateManager.attachAll(cubeAppState,axisAppState);

        stateManager.getState(axisAppState.getClass()).setEnabled(false);//刚开始坐标轴不需要显示


        // 设置灯光渲染模式为单通道，这样更加明亮。
        renderManager.setPreferredLightMode(LightMode.SinglePass);
        renderManager.setSinglePassLightBatchSize(2);

        // 做个准星
        cross = makeCross();
        // 做个拾取标记
//        flag = makeFlag();
        // 用户输入
        inputManager.addMapping(PICKING, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping(CHANGE_CAM_MODE, new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping(CTRL, new KeyTrigger(KeyInput.KEY_LCONTROL));
        inputManager.addListener(this, PICKING, CHANGE_CAM_MODE,CTRL);
    }

    /**
     * 在摄像机镜头正中央贴一张纸，充当准星。
     */
    private Spatial makeCross() {
        // 采用Gui的默认字体，做个加号当准星。
        BitmapText text = guiFont.createLabel("+");
        text.setColor(ColorRGBA.Red);// 红色

        // 居中
        float x = (cam.getWidth() - text.getLineWidth()) * 0.5f;
        float y = (cam.getHeight() + text.getLineHeight()) * 0.5f;
        text.setLocalTranslation(x, y, 0);
        guiNode.attachChild(text);
        return text;
    }

    /**
     * 制作一个小球，用于标记拾取的地点。
     *
     * @return
     */
    private Spatial makeFlag() {
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Green);
//        mat.getAdditionalRenderState().setWireframe(true);
        Geometry geom = new Geometry("flag", new Sphere(8, 8, 1));
        geom.setMaterial(mat);
        return geom;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (isPressed) {
            if (CTRL.equals(name)) {
                ctrl = true;
            }else if (CHANGE_CAM_MODE.equals(name)) {
                if (flyCam.isDragToRotate()) {
                    // 自由模式
                    flyCam.setDragToRotate(false);
                    guiNode.attachChild(cross);
                } else {
                    // 拖拽模式
                    flyCam.setDragToRotate(true);
                    cross.removeFromParent();
                }
            }else if (PICKING.equals(name) ){//单选
                // 拾取
                pick();
            }
        }
        else {
            if (CTRL.equals(name)){//ctrl已经按下，松开再后复位
                ctrl = false;
                System.out.println("ctrl复位");
            }
        }
    }



    /**
     * 使用射线检测，判断离摄像机最近的点。
     */
    private void pick() {
        Ray ray =  updateRay();
        CollisionResults results = new CollisionResults();
        // rootNode.collideWith(ray, results);// 碰撞检测
        Node cubeSceneNode = stateManager.getState(cubeAppState.getClass()).getRootNode();//场景节点
        Node AxisNode = stateManager.getState(axisAppState.getClass()).getAxisNode();//坐标轴节点
        Node pickable = (Node) cubeSceneNode.getChild("pickable");//场景中要射线检测的节点
        pickable.collideWith(ray, results);// 只和地板上的几何体进行碰撞检测
        print(results);// 打印检测结果
        //判断检测结果
        if (results.size() > 0) {
            // 放置拾取标记
//            Vector3f position = results.getClosestCollision().getContactPoint();
            closetGeom = results.getClosestCollision().getGeometry();
            BoundingBox bound = (BoundingBox)closetGeom.getWorldBound();
            //坐标轴跟新：对AxisNode节点的位置设置成射线检测到的对象包围体的中心点
            AxisNode.setLocalTranslation(bound.getCenter());
            closetGeom.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);//透明操作
            closetGeom.setQueueBucket(RenderQueue.Bucket.Transparent);
            if (closetGeom != LastGeom ){//空选 或 下一个对象
                ////选中其他物体时取消上一个物体的透明操作
                if (ctrl == false) {//单选
                    if (LastGeom != null) {
                        LastGeom.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Off);
                        pickable.attachChild(LastGeom);//脱离选中节点，添加回原场景节点
                    }
                }
                LastGeom = closetGeom;//保存当前最近的几何体
                ClosestNode.attachChild(LastGeom);//添加选中对象进节点
            }
            rootNode.attachChild(AxisNode);
            rootNode.attachChild(ClosestNode);//将 选中节点 添加进场景
            System.out.println( "选中节点有：" + ClosestNode.getChildren());
        } else {
            // 移除标记
//            if (closetGeom != null){
//                closetGeom.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Off);
//            }
            for ( int i = 0;!ClosestNode.getChildren().isEmpty();) {//对应多选对象取消透明操作
                System.out.println("ClosestNode.getChildren().size()= " + ClosestNode.getChildren().size());
                Geometry closestNodeChild = (Geometry) ClosestNode.getChild(i);
                closestNodeChild.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Off);//透明度复位
                pickable.attachChild(closestNodeChild);//添加回原节点
                LastGeom = null;//LastGeom 复位后才能再次选中
                System.out.println( "删除选中节点对象后有：" + closestNodeChild.getName());
            }
            AxisNode.removeFromParent();
            ClosestNode.removeFromParent();
        }
    }


    /**
     * 更新射线参数
     *
     * @return
     */
    private Ray updateRay() {
        // 使用摄像机的位置作为射线的原点
        ray.setOrigin(cam.getLocation());

        if (!flyCam.isDragToRotate()) {
            /**
             * 自由模式下，直接使用摄像机方向即可。
             */
            ray.setDirection(cam.getDirection());
        } else {
            /**
             * 拖拽模式下，通过鼠标的位置计算射线方向
             */
            Vector2f screenCoord = inputManager.getCursorPosition();
            Vector3f worldCoord = cam.getWorldCoordinates(screenCoord, 1f);

            // 计算方向
            Vector3f dir = worldCoord.subtract(cam.getLocation());
            dir.normalizeLocal();//将向量规范化为长度=1，并返回(修改后的)当前实例。如果向量的长度为0，则它不变。

            ray.setDirection(dir);
        }

        return ray;
    }

    /**
     * 打印检测结果
     *
     * @param results
     */
    private void print(CollisionResults results) {
        System.out.println("碰撞结果：" + results.size());
        System.out.println("射线：" + ray);

        /**
         * 判断检测结果
         */
        if (results.size() > 0) {

            // 从近到远，打印出射线途径的所有交点。
            for (int i = 0; i < results.size(); i++) {
                CollisionResult result = results.getCollision(i);

                float dist = result.getDistance();//距离
                Vector3f point = result.getContactPoint();//交点
                Vector3f normal = result.getContactNormal();//交点法线
                Geometry geom = result.getGeometry();//返回相交的几何体

                System.out.printf("序号：%d, 距离：%.2f, 物体名称：%s, 交点：%s, 交点法线：%s\n", i, dist, geom.getName(), point, normal);
            }

            // 离射线原点最近的交点
            Vector3f closest = results.getClosestCollision().getContactPoint();
            //最近的点的物体变色
//            results.getClosestCollision().getGeometry().getMaterial().setColor("Diffuse",ColorRGBA.Red.mult(0.2f));

            // 离射线原点最远的交点
            Vector3f farthest = results.getFarthestCollision().getContactPoint();

            System.out.printf("最近点：%s, 最远点：%s\n", closest, farthest);
        }
    }


}
