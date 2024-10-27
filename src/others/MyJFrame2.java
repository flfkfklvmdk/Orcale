package others;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyJFrame2 extends JFrame implements KeyListener {
    public MyJFrame2() {
        JButton jtb = new JButton("请点击我");
        this.setSize(500, 500);
        this.setTitle("MyJFrame");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
        this.setLayout(null);

        //给按钮设置位置和宽高
        jtb.setBounds(0, 0, 100, 50);

        //给按钮绑定鼠标事件
        jtb.addKeyListener(this);

        //将按钮添加到整个界面中
        this.getContentPane().add(jtb);

        //让整个界面显示出来
        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("按下");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("松开");
        //获取按键编号
        int source=e.getKeyCode();
        //判断
        if(source==65){
            System.out.println("按的是a");
        }
        else System.out.println("未知");
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
