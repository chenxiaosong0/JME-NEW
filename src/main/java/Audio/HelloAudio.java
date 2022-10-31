package Audio;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.audio.Environment;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 * @author xiaosongChen
 * @create 2022-10-23 17:20
 * @description :
 */
public class HelloAudio extends SimpleApplication {

    public static void main(String[] args) {
        // 启动程序
        HelloAudio app = new HelloAudio();
        app.start();
    }

    /**
     * 枪声
     */
    private AudioNode audioGun;
    /**
     * 环境音效
     */
    private AudioNode audioNature;

    /**
     * 开枪
     */
    private final static String SHOOT = "Shoot";

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(40);

        /**
         * 制作一个蓝色的小方块，用来表示音源的位置。
         */
        Geometry geom = new Geometry("Player", new Box(1, 1, 1));
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
        rootNode.attachChild(geom);

        /**
         * 初始化用户输入
         */
        initKeys();

        /**
         * 初始化音效
         */
        initAudio();

    }

    /**
     * 保持音频监听器（Audio Listener）和摄像机（Camera）同步运动。
     */
    @Override
    public void simpleUpdate(float tpf) {
        listener.setLocation(cam.getLocation());
        listener.setRotation(cam.getRotation());
    }

    /**
     * 创建两个AudioNode作为音源，并添加到场景中。
     */
    private void initAudio() {
        /**
         * 创建一个“枪声”音源，用户点击鼠标时会发出枪声。
         */
        audioGun = new AudioNode(assetManager, "Sound/Effects/Gun.wav", AudioData.DataType.Buffer);
        audioGun.setLooping(false);// 禁用循环播放
        audioGun.setPositional(false);// 设置为非定位音源，玩家无法通过耳机辨别音源的位置。常用于背景音乐。
        audioGun.setVolume(2);
        // 将音源添加到场景中
        audioGun.setTimeOffset(0.5f);//从音乐的第几秒处开始播放
        rootNode.attachChild(audioGun);

        /**
         * 创建一个自然音效（海潮声），这个音源会一直循环播放。
         */
        audioNature = new AudioNode(assetManager, "Sound/Environment/Ocean Waves.ogg", AudioData.DataType.Stream);
        audioNature.setLooping(true); // 循环播放
        audioNature.setPositional(true);// 设置为定位音源，这将产生3D音效，玩家能够通过耳机来辨别音源的位置。
        audioNature.setVolume(3);// 音量
        // 将音源添加到场景中
        rootNode.attachChild(audioNature);
        audioNature.setPitch(1.5f);//设置音源数据的频率（采样率）倍数，取值范围是0.5至2.0。大于1.0倍，播放会加速；小于1.0，播放时间会被拉长。
        audioNature.play(); // 持续播放
        audioNature.setReverbEnabled(true);//开启混响模式
        audioRenderer.setEnvironment(Environment.Dungeon);//设置混响环境：洞穴
    }

    /**
     * 定义“开枪(SHOOT)”动作，用户点击鼠标左键时触发此动作。
     */
    private void initKeys() {
        inputManager.addMapping(SHOOT, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(actionListener, SHOOT);
    }

    /**
     * 定义一个监听器，用来处理开枪动作，当玩家点击鼠标左键时发出枪声。
     */
    private ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (SHOOT.equals(name) && isPressed) {
                audioGun.playInstance(); // 只播放一次
            }
        }
    };

}
