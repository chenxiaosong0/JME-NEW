package SwingGUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/**
 * @author xiaosongChen
 * @create 2022-11-10 21:33
 * @description :
 */
public class MyJFrame  {
    public static void main(String[] args) {
        Mframe mframe = new Mframe();
        mframe.MainFrame();
    }

    public void fame(){

    }


}
class Mframe extends JFrame{
    JFrame frame;
    Border border;

    public void MainFrame(){
        frame = new JFrame("jFrame");
        frame.setSize(1000,800);
        frame.setLocation(600,100);

        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


}