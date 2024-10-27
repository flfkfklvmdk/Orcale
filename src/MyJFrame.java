import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyJFrame extends JFrame implements MouseListener {

    JButton jtb=new JButton("请点击我");

    public MyJFrame() {
        this.setSize(500, 500);
        this.setTitle("MyJFrame");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
        this.setLayout(null);

        //给按钮设置位置和宽高
        jtb.setBounds(0,0,100,50);

        //给按钮绑定鼠标事件
        jtb.addMouseListener(this);

        //将按钮添加到整个界面中
        this.getContentPane().add(jtb);

        //让整个界面显示出来
        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //单击
        System.out.println("单击");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //按下不松
        System.out.println("按下不松");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //松开
        System.out.println("松开");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //划入
        System.out.println("划入");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //划出
        System.out.println("划出");
    }
}
