package DtsDeom;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.RenderState;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaosongChen
 * @create 2022-11-26 10:48
 * @description :
 */
public class SelcetCombine extends BaseAppState implements ActionListener {
    private SimpleApplication simpleApp;
    private AssetManager assetManager;
    private AppStateManager   stateManager;
    private InputManager inputManager;
    private ViewPort viewPort;
    private Camera camera;
    // 空格键：切换摄像机模式,鼠标左键：拾取
    public final static String CHANGE_CAM_MODE = "change_camera_mode",PICKING = "pick",CTRL = "ctrl";
    private Node ClosestNode = new Node("ClosestNode"),foegrinNode = new Node("foegrinNode");
    private boolean ctrl = false;//是否多选
    private Geometry closetGeom,LastGeom;;    //射线检测到最近的几何体
    private  Node AxisNode; //获取主场景中的节点
    private OnliAxisAppState axisAppState = new OnliAxisAppState();//坐标轴
    private List<Geometry> PickList = new ArrayList<>(),PickLast;
    private List<Spatial> children;
    // 射线
    private Ray ray;

    public SelcetCombine() {
// 初始化射线
        ray = new Ray();
    }

    @Override
    protected void initialize(Application app) {
        this.setId("Choose");
        //拿到主场景的相关资源
//        super.initialize(stateManager, app);
        this.simpleApp    = (SimpleApplication) app;
        this.assetManager = app.getAssetManager();
        this.stateManager = app.getStateManager();
        this.inputManager = app.getInputManager();
        this.viewPort     = app.getViewPort();
        this.camera       = app.getCamera();
         children = simpleApp.getRootNode().getChildren();//获取根节点中所有的节点
        if (!children.isEmpty()){
            System.out.println(children);
            for (int i = 0;  !children.isEmpty(); ) {
                foegrinNode.attachChild(children.get(i));
             }
            System.out.println( "foegrinNode.getChildren(): " + foegrinNode.getChildren());
        }
        stateManager.attach(axisAppState);//加载坐标系
        stateManager.getState(axisAppState.getClass()).setEnabled(false);//刚开始坐标轴不需要显示
        AxisNode = stateManager.getState(axisAppState.getClass()).getAxisNode();//坐标轴节点
        ClosestNode.attachChild(AxisNode);
        // 用户输入
        inputManager.addMapping(PICKING, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping(CHANGE_CAM_MODE, new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping(CTRL, new KeyTrigger(KeyInput.KEY_LCONTROL));
        inputManager.addListener(this, PICKING, CHANGE_CAM_MODE,CTRL);
    }
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (isPressed) {
            if (CTRL.equals(name)) {
                ctrl = true;
            }else if (CHANGE_CAM_MODE.equals(name)) {
                if (simpleApp.getFlyByCamera().isDragToRotate()) {
                    // 自由模式
                    simpleApp.getFlyByCamera().setDragToRotate(false);
                } else {
                    // 拖拽模式
                    simpleApp.getFlyByCamera().setDragToRotate(true);
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
    private void pick() {
        Ray ray =  updateRay();
        CollisionResults results = new CollisionResults();
        foegrinNode.collideWith(ray, results);// 外部节点进行碰撞检测
        print(results);// 打印检测结果
        //判断检测结果
        if (results.size() > 0) {
            closetGeom = results.getClosestCollision().getGeometry();
            if (closetGeom.getWorldBound() != null)//坐标轴跟新：对AxisNode节点的位置设置成射线检测到的对象包围体的中心点
            {
//                BoundingBox bound = (BoundingBox)closetGeom.getWorldBound();
                BoundingVolume bound = closetGeom.getWorldBound();
                AxisNode.setLocalTranslation(bound.getCenter());
                closetGeom.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);//透明操作
                closetGeom.setQueueBucket(RenderQueue.Bucket.Transparent);
                if (PickList.isEmpty()){
                    PickList.add(closetGeom);
                }else {
                    if (!PickList.contains(closetGeom)){
                        System.out.println("不包含");
                        if (ctrl == false){//单选
                            for (int i1 = 0; i1 < PickList.size(); i1++) {
                                System.out.println("单选 取消"+ PickList.get(i1));
                                PickList.get(i1).getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Off);
                                PickList.remove(i1);
                            }
                        }else {
                            System.out.println("进入多选 "+ ctrl);
                        }
                        PickList.add(closetGeom);
                    }else {
                        System.out.println("已包含");
                        closetGeom.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Off);
                    }
//                    for (int i = 0; i < PickList.size(); i++) {
//                        System.out.println("closetGeom = " + closetGeom+ " PickList.get "+ i + " = " + PickList.get(i));
//                        if (closetGeom != PickList.get(i)){
//                            if (ctrl == false){//单选
//                                for (int i1 = 0; i1 < PickList.size(); i1++) {
//                                    System.out.println("单选 取消"+ PickList.get(i1));
//                                    PickList.get(i1).getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Off);
//                                    PickList.remove(i1);
//                                }
//                            }else {
//                                System.out.println("进入多选 "+ ctrl);
//                            }
//                            PickList.add(closetGeom);
//                        }else {
//                            System.out.println("已选中");
////                            PickList.get(i).getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Off);
////                            PickList.remove(i);
//                        }
//                    }
                }
//                if (closetGeom != LastGeom ){//空选 或 下一个对象
//                    ////选中其他物体时取消上一个物体的透明操作
//                    if (ctrl == false) {//单选
//                        if (LastGeom != null) {
//                            LastGeom.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Off);
//                            foegrinNode.attachChild(LastGeom);//脱离选中节点，添加回原场景节点
//                        }
//                    }
//                    LastGeom = closetGeom;//保存当前最近的几何体
//                    ClosestNode.attachChild(LastGeom);//添加选中对象进节点
//                }
//            simpleApp.getRootNode().attachChild(AxisNode);
//                simpleApp.getRootNode().attachChild(ClosestNode);//将 选中节点 添加进场景
//                System.out.println( "选中节点有：" + ClosestNode.getChildren());
            }else {
                System.out.println("没有BoundingBox");
            }
        } else {
            for (int i = 0; !PickList.isEmpty(); i++) {
                PickList.get(i).getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Off);
                PickList.remove(i);
            }
//            PickLast = null;
//            for ( int i = 0;!ClosestNode.getChildren().isEmpty();) {//对应多选对象取消透明操作
//                System.out.println("ClosestNode.getChildren().size()= " + ClosestNode.getChildren().size());
//                Geometry closestNodeChild = (Geometry) ClosestNode.getChild(i);
//                closestNodeChild.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Off);//透明度复位
//                foegrinNode.attachChild(closestNodeChild);//添加回原节点
//                LastGeom = null;//LastGeom 复位后才能再次选中
//                System.out.println( "删除选中节点对象后有：" + closestNodeChild.getName());
//            }
//            AxisNode.removeFromParent();
//            ClosestNode.removeFromParent();
        }
    }
    private Ray updateRay() {
        // 使用摄像机的位置作为射线的原点
        ray.setOrigin(camera.getLocation());
        if (!simpleApp.getFlyByCamera().isDragToRotate()) {
            /**
             * 自由模式下，直接使用摄像机方向即可。
             */
            ray.setDirection(camera.getDirection());
        } else {
            /**
             * 拖拽模式下，通过鼠标的位置计算射线方向
             */
            Vector2f screenCoord = inputManager.getCursorPosition();
            Vector3f worldCoord = camera.getWorldCoordinates(screenCoord, 1f);
            // 计算方向
            Vector3f dir = worldCoord.subtract(camera.getLocation());
            dir.normalizeLocal();//将向量规范化为长度=1，并返回(修改后的)当前实例。如果向量的长度为0，则它不变。
            ray.setDirection(dir);
        }
        return ray;
    }
    private void print(CollisionResults results) {
        System.out.println("碰撞结果：" + results.size());
        System.out.println("射线：" + ray);
        /**
         * 判断检测结果
         */
        if (results.size() > 0) {
            // 从近到远，打印出射线途径的所有交点。
//            for (int i = 0; i < results.size(); i++) {
//                CollisionResult result = results.getCollision(i);
//                float dist = result.getDistance();//距离
//                Vector3f point = result.getContactPoint();//交点
//                Vector3f normal = result.getContactNormal();//交点法线
//                Geometry geom = result.getGeometry();//返回相交的几何体
//                System.out.printf("序号：%d, 距离：%.2f, 物体名称：%s, 交点：%s, 交点法线：%s\n", i, dist, geom.getName(), point, normal);
//            }
            // 离射线原点最近的交点
            Vector3f closest = results.getClosestCollision().getContactPoint();
            // 离射线原点最远的交点
            Vector3f farthest = results.getFarthestCollision().getContactPoint();
            System.out.printf("最近点：%s, 最远点：%s\n", closest, farthest);
        }
    }
    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {
        simpleApp.getRootNode().attachChild(axisAppState.getRootNode());
        simpleApp.getRootNode().attachChild(ClosestNode);
        simpleApp.getRootNode().attachChild(foegrinNode);
//        simpleApp.getRootNode().attachChild(AxisNode);
    }

    @Override
    protected void onDisable() {
        simpleApp.getRootNode().detachChild(axisAppState.getRootNode());
        simpleApp.getRootNode().detachChild(ClosestNode);
        simpleApp.getRootNode().detachChild(foegrinNode);
//        simpleApp.getRootNode().detachChild(AxisNode);
    }



    @Override
    public String getId() {
        return "Choose";
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {

    }
    @Override
    public void stateDetached(AppStateManager stateManager) {

    }

    @Override
    public void update(float tpf) {

    }

    @Override
    public void render(RenderManager rm) {

    }

    @Override
    public void postRender() {

    }


}
