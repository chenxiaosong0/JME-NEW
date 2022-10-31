package AnimationTest;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.simsilica.lemur.anim.Animation;

/**
 * @author xiaosongChen
 * @create 2022-10-26 20:43
 * @description :
 */
public class HelloAnimation extends SimpleApplication {
    /**
     * 按w键行走
     */
    private static final String WALK = "walk";

    /**
     * 按空格键跳跃
     */
    private static final String JUMP = "jump";

    /**
     * 记录jaime的行走状态
     */
    private boolean iswalking = false;

    /**
     * 动画模型
     */
    private Spatial spatial;
    private Animation animation;
    private AnimControl animControl;
    private AnimChannel animChannel;//通过AnimChannel来播放对应的动画。
    // 首先使用AnimControl#createChannel()创建动画通道（AnimChannel），然后调用AnimChannel#setAnim(String name)播放对应的动画。

    public static void main(String[] args) {
        new HelloAnimation().start();
    }
    @Override
    public void simpleInitApp() {
// 初始化摄像机
        initCamera();

        // 初始化灯光
        initLight();

        // 初始化按键输入
        initKeys();

        // 初始化场景
        initScene();

        // 动画控制器
        animControl = spatial.getControl(AnimControl.class);
        animControl.addListener(animEventListener);

        // 显示这个模型中有多少个动画，每个动画的名字是什么。
        System.out.println(animControl.getAnimationNames());

        animChannel = animControl.createChannel();
        // 播放“闲置”动画
        animChannel.setAnim("Idle");


    }
    /**
     * 初始化摄像机
     */
    private void initCamera(){
        //禁用第一人称摄像机
        flyCam.setEnabled(false);
        cam.setLocation(new Vector3f(1,2,3));
        cam.lookAt(new Vector3f(0,0.5f,0),new Vector3f(0,1,0));
    }
    /**
     * 初始化光源
     */
    private void initLight(){
        //定向光
        DirectionalLight sunlight = new DirectionalLight();
        sunlight.setDirection(new Vector3f(-1,-2,-3));
        sunlight.setColor(new ColorRGBA(0.8f,0.8f,0.8f,1f));
        //环境光
        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(new ColorRGBA(0.2f,0.2f,0.2f,1f));
        //将光源添加进场景中
        rootNode.addLight(sunlight);
        rootNode.addLight(ambientLight);
    }
    /**
     * 初始化按键
     */
    private void initKeys(){
        //按w键行走
        inputManager.addMapping(WALK,new KeyTrigger(KeyInput.KEY_W));
        //按空格键跳跃
        inputManager.addMapping(JUMP,new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(actionListener,WALK,JUMP);
    }
    /**
     * 初始化场景
     */
    private void initScene() {
        // 加载Jaime模型
        spatial = assetManager.loadModel("Models/Jaime/Jaime.j3o");
        rootNode.attachChild(spatial);

        // 创建一个平面作为舞台
        Geometry stage = new Geometry("Stage", new Quad(2, 2));
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setColor("Diffuse", ColorRGBA.White);
        mat.setColor("Specular", ColorRGBA.White);
        mat.setColor("Ambient", ColorRGBA.Black);
        mat.setFloat("Shininess", 0);
        mat.setBoolean("UseMaterialColors", true);
        stage.setMaterial(mat);

        stage.rotate(-FastMath.HALF_PI, 0, 0);
        stage.center();
        rootNode.attachChild(stage);
    }
    private ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            /**
             * 若jaime已经处于Jumpstart/jumping/jumpEnd状态，就不要再做其他动作
             */
            //查询当前正在播放的动画
            String curAnim = animChannel.getAnimationName();
            if (curAnim != null && curAnim.startsWith("Jump")){
                return;
            }
            if (WALK.equals(name)){
                //记录行走状态
                iswalking = isPressed;
                if (isPressed){
                    //播放行走动画
                    animChannel.setAnim("Walk");
                    animChannel.setLoopMode(LoopMode.Loop);//循环播放
                }else {
                    //播放闲置的动画
                    animChannel.setAnim("Idle");
                    animChannel.setLoopMode(LoopMode.Loop);
                }
            }else if (JUMP.equals(name)){
                if (isPressed) {
                    // 播放“起跳”动画
                    animChannel.setAnim("JumpStart");
                    animChannel.setLoopMode(LoopMode.DontLoop);
                    animChannel.setSpeed(1.5f);
                }
            }
        }
    };
    /**
     * 动画事件监听器
     */
    private AnimEventListener animEventListener = new AnimEventListener() {
        @Override
        public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
            if ("JumpStart".equals(animName)) {
                // “起跳”动作结束后，紧接着播放“着地”动画。
                channel.setAnim("JumpEnd");
                channel.setLoopMode(LoopMode.DontLoop);
                channel.setSpeed(1.5f);

            } else if ("JumpEnd".equals(animName)) {
                // “着地”后，根据按键状态来播放“行走”或“闲置”动画。
                if (iswalking) {
                    channel.setAnim("Walk");
                    channel.setLoopMode(LoopMode.Loop);
                } else {
                    channel.setAnim("Idle");
                    channel.setLoopMode(LoopMode.Loop);
                }
            }
        }

        @Override
        public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        }
    };
}
