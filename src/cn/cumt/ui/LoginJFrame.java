package cn.cumt.ui;

import javax.swing.*;

public class LoginJFrame extends JFrame {
    //表示登录界面
    public LoginJFrame() {
        //宽高
        this.setSize(488,430);
        //设置关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //设置处于界面中央
        this.setLocationRelativeTo(null);
        //设置置顶
        this.setVisible(true);
    }
}
