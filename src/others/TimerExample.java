package others;
import javax.swing.Timer;
import javax.swing.*;
import java.awt.event.*;

public class TimerExample extends JFrame {
    private JLabel timerLabel;
    private Timer timer;
    private int seconds;

    public TimerExample() {
        // 初始化界面
        initJFrame();

        // 初始化计时器
        initTimer();

        // 添加计时器标签
        timerLabel = new JLabel("Time: 0 seconds");
        timerLabel.setBounds(100, 80, 107, 73);
        this.getContentPane().add(timerLabel);

        // 启动计时器
        timer.start();
    }


    private void initJFrame() {
        // 设置界面宽，高
        this.setSize(603, 680);
        // 设置标题
        this.setTitle("Timer Example");
        // 设置界面置顶(不会受其他应用的影响)
        this.setAlwaysOnTop(true);
        // 设置界面居中
        this.setLocationRelativeTo(null);
        // 设置关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 取消默认放置方式
        this.setLayout(null);
    }

    private void initTimer() {
        seconds = 0;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                updateTimerLabel();
            }
        });
    }

    private void updateTimerLabel() {
        timerLabel.setText("Time: " + seconds + " seconds");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TimerExample().setVisible(true);
            }
        });
    }
}
