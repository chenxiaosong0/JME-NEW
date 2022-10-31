package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author xiaosongChen
 * @create 2022-10-26 9:07
 * @description :
 */
public class MyJframe extends JFrame {
    public static void main(String[] args) {
        new MyJframe();
    }
    public  MyJframe(){
        Container container = getContentPane();//创建一个容器
        container.setLayout(null);
        JLabel j1 = new JLabel("JFrame窗体");
        j1.setHorizontalAlignment(SwingConstants.HORIZONTAL);//设置标签文字位置
        container.add(j1);
        JButton jButton = new JButton("工具栏");
        jButton.setBounds(10,10,100,20);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MyJDiadlog(MyJframe.this).setVisible(true);//使MyJDialog可见
            }
        });
        container.add(jButton);//将按钮添加到容器中
        container.setBackground(Color.LIGHT_GRAY);
        setSize(200,200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
class MyJDiadlog extends JDialog{
    public MyJDiadlog(MyJframe frame) {
        super(frame,"工具",true);//实例化一个JDialog类对象，指定对话框的父窗体，窗体标题和类型
        Container container = getContentPane();//创建一个容器
        container.add(new JLabel("当前没有工具"));//在容器中添加标签
        setBounds(120,120,100,100);//设置对话框窗体的大小
    }
}