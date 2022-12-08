package DtsDeom;

import Collision.CubeAppState;
import SwingGUI.Num1;
import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.TechniqueDef;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.debug.Grid;
import com.jme3.system.AppSettings;
import com.jme3.system.awt.AwtPanel;
import com.jme3.system.awt.AwtPanelsContext;
import com.jme3.system.awt.PaintMode;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author xiaosongChen
 * @create 2022-11-10 20:45
 * @description :Swing
 */

public class MainFrame extends SimpleApplication {
    private JPanel start;
    private JTree tree1;
    private JPanel bottom;
    private JPanel Top;
    private JPanel Left;
    private JPanel Right;
    private JSplitPane splitPane;
    private JPanel center;
    private JButton button, button1;
    private JScrollPane num1;
    private JScrollPane num2;
    private MenuBar menuBar;
    private Menu file, New;
    private MenuItem open, project, set, insert, save;
    final private static CountDownLatch panelsAreReady = new CountDownLatch(1);
    private static MainFrame app = new MainFrame();
    private static AwtPanel panel;
    private Geometry grid;
    public static void main(String[] args) {
        Logger.getLogger(Num1.class.getName()).setLevel(Level.WARNING);//添加记录器并指定日志级别
        app.setShowSettings(false);
        AppSettings settings = new AppSettings(true);
        settings.setCustomRenderer(AwtPanelsContext.class);
        app.setPauseOnLostFocus(false);
//        settings.setFrameRate(-1);
        app.setSettings(settings);
        app.start();

        // 在处理完所有挂起的AWT事件后发生。当应用程序线程需要更新GUI时，应该使用此方法。
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException exception) {
                    return;
                }
                final AwtPanelsContext ctx = (AwtPanelsContext) app.getContext();
                panel = ctx.createPanel(PaintMode.Accelerated);
                ctx.setInputSource(panel);//获取鼠标等监听操作
                createWindowForPanel(panel);
                panelsAreReady.countDown();
            }
        });
    }

    private void createUIComponents(JFrame frame) {
        // TODO: place custom component creation code here
        menuBar = new MenuBar();
        file = new Menu("File");
        open = new MenuItem("Open");
        save = new MenuItem("Save");
        New = new Menu("New");
        project = new MenuItem("Project");
        set = new MenuItem("Setting..");
        insert = new MenuItem("Insert");
        file.add(open);
        file.add(save);
        file.add(set);
        file.add(insert);
        New.add(project);
        menuBar.add(file);
        menuBar.add(New);
        frame.setMenuBar(menuBar);
        button = new JButton("Click");
        center.setBackground(Color.lightGray);
        Top.setBackground(Color.white);
        tree1.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        bottom.setBackground(Color.LIGHT_GRAY);
        bottom.add(button);
        app.action();
    }

    //创建jframe窗口
    private static void createWindowForPanel(AwtPanel panel) {
        JFrame frame = new JFrame("Num1");
        frame.setContentPane(app.start);
        frame.setLocation(400, 200);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();//自适应
        app.createUIComponents(frame);
        app.addAWTtoCenter(panel);//将jme的AWT画布添加进JPanel中
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("close");
                app.stop();
            }
        });
    }

    private void addAWTtoCenter(AwtPanel panel) {
        center.add(panel);
    }

    //jme的画面
    @Override
    public void simpleInitApp() {

        flyCam.setDragToRotate(true);
        flyCam.setMoveSpeed(20f);
        cam.setLocation(new Vector3f(4.5114727f, 6.176994f, 13.277485f));
        cam.setRotation(new Quaternion(-0.038325474f, 0.96150225f, -0.20146479f, -0.18291113f));
        // 设置灯光渲染模式为单通道，这样更加明亮。
        renderManager.setPreferredLightMode(TechniqueDef.LightMode.SinglePass);
        renderManager.setSinglePassLightBatchSize(2);
        // 地板网格
        grid = new Geometry("Grid", new Grid(21, 21, 1));
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.DarkGray);
        grid.setMaterial(mat);
        grid.center().move(0, 0, 0);
        grid.setShadowMode(RenderQueue.ShadowMode.Off);
        rootNode.attachChild(grid);
//        viewPort.setBackgroundColor(ColorRGBA.LightGray);

        rootNode.addLight(new DirectionalLight(new Vector3f(-1, -2, -3)));
        rootNode.addLight(new AmbientLight(new ColorRGBA(0.2f, 0.2f, 0.2f, 1f)));

        stateManager.attach(new CubeAppState());
        stateManager.attach(new SelcetCombine());
        /*
         * Wait until  AWT panels are ready.
         */
        try {
            panelsAreReady.await();
        } catch (InterruptedException exception) {
            throw new RuntimeException("Interrupted while waiting for panels", exception);
        }
        panel.attachTo(false, viewPort);//true窗口出现加载混乱
        guiViewPort.setClearFlags(true, true, true);
    }

    //触发事件
    private void action() {
        //open
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(2);//文件，文件夹都能选
                fileChooser.setMultiSelectionEnabled(false);//设置多选
                int result = fileChooser.showOpenDialog(null);//显示对话框
                if (result == JFileChooser.APPROVE_OPTION) {
                    System.out.println("You chose to open this file: " + fileChooser.getSelectedFile().getName());
                }
            }
        });
        //save
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
//                fileChooser.setFileFilter(new FileNameExtensionFilter("图片文件", "jpg", "jpeg", "png", "XML文件", "xml"));//设置文件名后缀过滤器
                //显示对话框
                int saveDialog = fileChooser.showSaveDialog(null);
                if (saveDialog == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("You chose to save this file: " + selectedFile.getAbsoluteFile());
                }
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Clicked");
            }
        });
    }

    //获取当前系统界面格式并同步
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                 | UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public MainFrame() {
        super(new FlyCamAppState(), new DebugKeysAppState());//添加了场景
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        start = new JPanel();
        start.setLayout(new BorderLayout(5, 5));
        start.setBackground(new Color(-1));
        Font startFont = this.$$$getFont$$$("JetBrains Mono", Font.BOLD, 14, start.getFont());
        if (startFont != null) start.setFont(startFont);
        start.setForeground(new Color(-8221551));
        start.setInheritsPopupMenu(true);
        start.setMinimumSize(new Dimension(1200, 800));
        start.setPreferredSize(new Dimension(1200, 800));
        start.setToolTipText("3333");
        start.setVerifyInputWhenFocusTarget(false);
        start.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.BELOW_TOP, this.$$$getFont$$$(null, -1, -1, start.getFont()), new Color(-4473925)));
        Top = new JPanel();
        Top.setLayout(new BorderLayout(0, 0));
        start.add(Top, BorderLayout.NORTH);
        Top.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        button1 = new JButton();
        button1.setText("Button");
        Top.add(button1, BorderLayout.WEST);
        bottom = new JPanel();
        bottom.setLayout(new BorderLayout(0, 0));
        bottom.setMinimumSize(new Dimension(1200, 100));
        bottom.setPreferredSize(new Dimension(100, 100));
        start.add(bottom, BorderLayout.SOUTH);
        Left = new JPanel();
        Left.setLayout(new BorderLayout(0, 0));
        start.add(Left, BorderLayout.CENTER);
        splitPane = new JSplitPane();
        splitPane.setContinuousLayout(true);
        splitPane.setDividerLocation(150);
        splitPane.setDividerSize(3);
        Left.add(splitPane, BorderLayout.CENTER);
        splitPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        center = new JPanel();
        center.setLayout(new BorderLayout(0, 0));
        center.setAutoscrolls(false);
        center.setRequestFocusEnabled(true);
        center.setToolTipText("you need add jme window");
        center.setVerifyInputWhenFocusTarget(true);
        splitPane.setRightComponent(center);
        center.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "show", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, this.$$$getFont$$$(null, -1, -1, center.getFont()), new Color(-4473925)));
        tree1 = new JTree();
        tree1.putClientProperty("JTree.lineStyle", "");
        splitPane.setLeftComponent(tree1);
        Right = new JPanel();
        Right.setLayout(new BorderLayout(0, 0));
        Right.setMinimumSize(new Dimension(160, 49));
        Right.setPreferredSize(new Dimension(160, 49));
        start.add(Right, BorderLayout.EAST);
        num1 = new JScrollPane();
        num1.setPreferredSize(new Dimension(52, 20));
        Right.add(num1, BorderLayout.CENTER);
        final JLabel label1 = new JLabel();
        label1.setText("message");
        num1.setViewportView(label1);
        num2 = new JScrollPane();
        num2.setMinimumSize(new Dimension(14, 200));
        num2.setPreferredSize(new Dimension(31, 300));
        Right.add(num2, BorderLayout.SOUTH);
        final JLabel label2 = new JLabel();
        label2.setText("Label");
        num2.setViewportView(label2);
        start.setNextFocusableComponent(start);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return start;
    }

    public JPanel getStart() {
        return start;
    }

    public JTree getTree1() {
        return tree1;
    }

    public JPanel getBottom() {
        return bottom;
    }

    public JPanel getTop() {
        return Top;
    }

    public JPanel getLeft() {
        return Left;
    }

    public JPanel getRight() {
        return Right;
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }

    public JPanel getCenter() {
        return center;
    }

    public JButton getButton() {
        return button;
    }

    public JButton getButton1() {
        return button1;
    }

    public JScrollPane getNum1() {
        return num1;
    }

    public JScrollPane getNum2() {
        return num2;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public Menu getFile() {
        return file;
    }

    public Menu getNew() {
        return New;
    }

    public MenuItem getOpen() {
        return open;
    }

    public MenuItem getProject() {
        return project;
    }

    public MenuItem getSet() {
        return set;
    }

    public MenuItem getInsert() {
        return insert;
    }

    public MenuItem getSave() {
        return save;
    }

}
