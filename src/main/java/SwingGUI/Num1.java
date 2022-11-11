package SwingGUI;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.jgoodies.forms.layout.FormLayout;
import com.sun.org.apache.bcel.internal.generic.NEW;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.Locale;

/**
 * @author xiaosongChen
 * @create 2022-11-10 20:45
 * @description :Swing
 */

public class Num1 {
    private JPanel start;
    private JTree tree1;
    private JPanel bottom;
    private JPanel Top;
    private JPanel Left;
    private JPanel Right;
    private JSplitPane splitPane;
    private JPanel center ;
    private JButton button, button1;
    private JScrollPane num1;
    private JScrollPane num2;
    private MenuBar menuBar;
    private Menu file, New;
    private MenuItem open, project, set, insert, save;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Num1");
        Num1 num1 = new Num1();
        frame.setContentPane( num1.start);
        frame.setLocation(400, 200);
        frame.setResizable(true);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

//        Container frameContentPane = frame.getContentPane();
//        for (int i = 0; i < frameContentPane.getComponents().length; i++) {
//            System.out.println(frameContentPane.getComponents()[i]);
//        }
        num1.createUIComponents(frame);
        num1.action();
        frame.setVisible(true);

    }

    private void createUIComponents(JFrame frame){
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

        Label label = new Label("This is  Center");
        button = new JButton("Click");
        center.setBackground(Color.lightGray);
        Top.setBackground(Color.white);
        tree1.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        bottom.setBackground(Color.LIGHT_GRAY);

        center.add(button);
    }
    private void createUIComponents(){

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
                    System.out.println("You chose to open this file: " +
                            fileChooser.getSelectedFile().getName());
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

    {
        try {//获取当前系统界面格式并同步
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                 | UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Num1(JPanel start, JTree tree1, JPanel bottom,
                JPanel top, JPanel left, JPanel right,
                JSplitPane splitPane, JPanel center,
                JButton button, JButton button1,
                JScrollPane num1, JScrollPane num2,
                MenuBar menuBar, Menu file, Menu aNew,
                MenuItem open, MenuItem project, MenuItem set,
                MenuItem insert, MenuItem save) {
        this.start = start;
        this.tree1 = tree1;
        this.bottom = bottom;
        Top = top;
        Left = left;
        Right = right;
        this.splitPane = splitPane;
        this.center = center;
        this.button = button;
        this.button1 = button1;
        this.num1 = num1;
        this.num2 = num2;
        this.menuBar = menuBar;
        this.file = file;
        New = aNew;
        this.open = open;
        this.project = project;
        this.set = set;
        this.insert = insert;
        this.save = save;
    }

    public Num1() {
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
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
        start.setMinimumSize(new Dimension(1000, 800));
        start.setPreferredSize(new Dimension(1000, 800));
        start.setToolTipText("3333");
        start.setVerifyInputWhenFocusTarget(false);
        start.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.BELOW_TOP, this.$$$getFont$$$(null, -1, -1, start.getFont()), new Color(-4473925)));
        Top = new JPanel();
        Top.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        start.add(Top, BorderLayout.NORTH);
        Top.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        button1 = new JButton();
        button1.setText("Button");
        Top.add(button1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        Top.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        bottom = new JPanel();
        bottom.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        bottom.setMinimumSize(new Dimension(150, 100));
        bottom.setPreferredSize(new Dimension(100, 100));
        start.add(bottom, BorderLayout.SOUTH);
        final JLabel label1 = new JLabel();
        label1.setText("Meeeage.......");
        bottom.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Left = new JPanel();
        Left.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        start.add(Left, BorderLayout.CENTER);
        splitPane = new JSplitPane();
        Left.add(splitPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(912, 552), null, 0, false));
        splitPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        center = new JPanel();
        center.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        center.setToolTipText("");
        center.setVerifyInputWhenFocusTarget(false);
        splitPane.setRightComponent(center);
        tree1 = new JTree();
        tree1.putClientProperty("JTree.lineStyle", "");
        splitPane.setLeftComponent(tree1);
        Right = new JPanel();
        Right.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        Right.setMinimumSize(new Dimension(200, 49));
        Right.setPreferredSize(new Dimension(200, 49));
        start.add(Right, BorderLayout.EAST);
        num1 = new JScrollPane();
        Right.add(num1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("message");
        num1.setViewportView(label2);
        num2 = new JScrollPane();
        Right.add(num2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Label");
        num2.setViewportView(label3);
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
