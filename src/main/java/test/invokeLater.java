package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author xiaosongChen
 * @create 2022-11-22 17:14
 * @description :
 */
public class invokeLater extends JFrame {
    //定义属性
    private JFrame fr = new JFrame();
    private JPanel jp = new JPanel(); //面板
    private JTextField jt = new JTextField(10);
    private JButton jb = new JButton("Start");


    /**
     * 按钮的监听函数
     */
    private void JbListener(){
        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //定义匿名内部类，该类实现Runnable接口
                new Thread(new Runnable() {
                    public void run() {
                        try{
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    jt.setText("1.检查数据合法性...");
                                }
                            });

                            Thread.sleep(3000);//模仿检测数据合法性
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    jt.setText("2.正在导入数据...");
                                }
                            });
                            Thread.sleep(4000);//模仿导入数据
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    jt.setText("3.导入成功!");
                                }
                            });
                        }catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }


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
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new invokeLater().Demo();
            }
        });
    }
}
