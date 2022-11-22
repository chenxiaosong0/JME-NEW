package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author xiaosongChen
 * @create 2022-11-22 16:59
 * @description :触发按钮后，主线程在执行监听时间，而UI界面就停止更新
 */
public class swingtest extends JFrame {
    //定义属性
    private JFrame fr = new JFrame();
    private JPanel jp = new JPanel(); //面板
    private JTextField jt = new JTextField(10);
    private JButton jb = new JButton("Start");


    /**
     * 按钮的监听函数
     */
    //第一次优化：触发按钮后，可以拖动改变界面大小
    //重新创建一个线程，在这个新线程中执行所有操作，包括延时及界面更新，这样可以显示我们想要的结果。
    //但是，不能在其他线程中操作EDT线程，会导致界面不稳定
    private void JbListener(){
        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //定义匿名内部类，该类实现Runnable接口
                new Thread(new Runnable() {
                    public void run() {
                        try{
                            jt.setText("1.检查数据合法性...");
                            Thread.sleep(3000);//模仿检测数据合法性
                            jt.setText("2.正在导入数据...");
                            Thread.sleep(4000);//模仿导入数据
                            jt.setText("3.导入成功!");
                        }catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
//    private void JbListener(){
//        jb.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                try{
//                    jt.setText("1.检查数据合法性...");
//                    Thread.sleep(3000);//模仿检测数据合法性
//                    jt.setText("2.正在导入数据...");
//                    Thread.sleep(4000);//模仿导入数据
//                    jt.setText("3.导入成功!");
//                }catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        });
//    }


    /**
     * 初始化Swing
     */
    public void Demo(){
        jp.add(jt);
        jp.add(jb);
        jp.setLayout(new FlowLayout());
        fr.add(jp);
        JbListener();
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setSize(300, 100);
        fr.setVisible(true);

    }


    public static void main(String[] args) {

        new swingtest().Demo();

    }
}
