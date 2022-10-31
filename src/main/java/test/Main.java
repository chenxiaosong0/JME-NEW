package test;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;

import java.util.prefs.BackingStoreException;

/**
 * @author xiaosongChen
 * @create 2022-10-22 15:43
 * @description :
 */
public class Main extends SimpleApplication {


    public static void main(String[] args) throws BackingStoreException {
//        PrintStream ps= null;
//        try {
//            ps = new PrintStream(new FileOutputStream("log.txt"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        System.setOut(ps);

        AppSettings settings = new AppSettings(true);

        settings.setTitle("你的名字");
        settings.setVSync(true);
        settings.setWidth(1280);
        settings.setHeight(800);
        Main app = new Main();
        app.setSettings(settings);
        app.setDisplayStatView(true); // 设置状态小窗是否可见
        app.setShowSettings(true); // 设置界面是否显示
        app.setPauseOnLostFocus(false); // 设置程序后台运行，默认为true，即焦点不在程序上则停止显示渲染
        app.start();
    }
    @Override
    public void simpleInitApp() {
        StartScreenState startScreenState = new StartScreenState();
        stateManager.attach(startScreenState);
    }

}

