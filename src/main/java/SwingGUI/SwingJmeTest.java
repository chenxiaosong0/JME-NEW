package SwingGUI;

/**
 * @author xiaosongChen
 * @create 2022-11-18 9:33
 * @description :
 */
import com.formdev.flatlaf.FlatLightLaf;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import org.lwjgl.LWJGLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SwingJmeTest extends JFrame {

    private JPanel contentPane;

    class JmeApplication extends SimpleApplication{

        private Geometry geometry;

        @Override
        public void simpleInitApp() {
            inputManager.setCursorVisible(true);
            flyCam.setEnabled(false);
            geometry = new Geometry("", new Box(1, 1, 1));
            Material material = new Material(assetManager, Materials.UNSHADED);
            material.setColor("Color", ColorRGBA.randomColor());
            geometry.setMaterial(material);
            rootNode.attachChild(geometry);
        }

        @Override
        public void simpleUpdate(float tpf) {
            geometry.rotate(0, tpf, 0);
        }
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SwingJmeTest frame = new SwingJmeTest();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public SwingJmeTest() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080,800);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JSplitPane splitPane = new JSplitPane();
        splitPane.setResizeWeight(0.25);
        JPanel left = new JPanel();
        left.setBackground(Color.red);
        splitPane.setLeftComponent(left);

        JPanel jPanel = new JPanel(new BorderLayout());
        Canvas canvas = new Canvas();
        try {
            org.lwjgl.opengl.Display.setParent(canvas);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        jPanel.add(canvas);
        splitPane.setRightComponent(jPanel);
        contentPane.add(splitPane);

        new Thread(() -> {
            AppSettings settings = new AppSettings(true);
            settings.setWidth(1080);
            settings.setHeight(600);
            settings.setFrameRate(40);
            settings.setSamples(16);
            settings.setAudioRenderer(null);
            settings.setGammaCorrection(true);

            JmeApplication application = new JmeApplication();

            application.setShowSettings(false);
            application.setSettings(settings);
            application.setPauseOnLostFocus(false);
            application.start();
        }).start();

    }


}

