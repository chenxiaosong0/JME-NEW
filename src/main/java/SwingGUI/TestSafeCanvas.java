package SwingGUI;

/**
 * @author xiaosongChen
 * @create 2022-11-15 22:34
 * @description :将jme的画布添加进jframe画布中
 */

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TestSafeCanvas extends SimpleApplication {

    public static void main(String[] args) throws InterruptedException{

        AppSettings settings = new AppSettings(true);
        settings.setWidth(640);
        settings.setHeight(480);


        final TestSafeCanvas app = new TestSafeCanvas();
        app.setPauseOnLostFocus(false);
        app.setSettings(settings);
        //初始化应用程序的画布以便使用。
        //调用此方法后，将getContext()上下文转换为JmeCanvasContext，
        // 然后使用JmeCanvasContext. getcanvas()获取画布并将其附加到AWT/Swing Frame。
        // 渲染线程将在画布在屏幕上显示时启动，但是如果你希望立即启动上下文，可以调用startCanvas()来强制启动渲染线程。
        app.createCanvas();

        app.startCanvas(true);

        JmeCanvasContext context = (JmeCanvasContext) app.getContext();
        Canvas canvas = context.getCanvas();
        canvas.setSize(settings.getWidth(), settings.getHeight());



        Thread.sleep(3000);

        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                app.stop();
            }
        });
        frame.getContentPane().add(canvas);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Thread.sleep(3000);

        frame.getContentPane().remove(canvas);

        Thread.sleep(3000);

        frame.getContentPane().add(canvas);
    }

    @Override
    public void simpleInitApp() {
        flyCam.setDragToRotate(true);

        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//        mat.setTexture("ColorMap", assetManager.loadTexture("Interface/Logo/Monkey.jpg"));
        geom.setMaterial(mat);
        rootNode.attachChild(geom);
    }
}
