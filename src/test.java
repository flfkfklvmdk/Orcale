import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class test extends JFrame implements ActionListener {
    //创建按钮
    static JButton jtb1=new JButton("点我啊");
    //创建按钮
    static JButton jtb2=new JButton("来点我啊");

    public void text() {
        JFrame frame = new JFrame();
        frame.setSize(603,600);
        frame.setAlwaysOnTop(true);
        frame.setTitle("事件演示");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);


        //设置按钮
        jtb1.setBounds(0,0,100,100);
        //设置按钮位置
        jtb2.setLocation(100,100);
        //创建动作监听,我要给组件添加那个动作监听（鼠标左键，空格）
        //参数：事件被执行后执行的代码
        jtb1.addActionListener(this);
        //jtb.addActionListener(new MyactionListener());(这个业务逻辑只用过一次，故使用匿名内部类)
                //把按钮添加到界面中
                frame.getContentPane().add(jtb1);



        //设置按钮
        jtb2.setBounds(0,0,100,100);
        //设置位置
        jtb2.setLocation(200,200);
        //创建动作监听,我要给组件添加那个动作监听（鼠标左键，空格）
        //参数：事件被执行后执行的代码
        jtb2.addActionListener(this);
        //jtb.addActionListener(new MyactionListener());(这个业务逻辑只用过一次，故使用匿名内部类)
        //把按钮添加到界面中
        frame.getContentPane().add(jtb2);

        frame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource()==jtb1){
           jtb1.setSize(50,50);
       }
       else if(e.getSource()==jtb2){
           jtb2.setLocation(450,450);
       }
    }
}

