/**
 *  Description: 基于 AWT 实现的计算器
 *
 *  @author: 不会编程的程序圆
 *  @Date: 2020 -06 -27
 *  @Time: 14:17
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

public class Calculator
{

    private JFrame frame;//frame框架

    private ImageIcon icon;//图像储存

    private JTextField textField;//文本域

    private RoundedCornerButton[] button;//按钮\

    private RoundedCornerButton[] financeButtons; // New buttons for finance

    private JPanel panel;//面板

    private JLabel label;//标签

    /**
     * data：当前输入的数据
     */
    private String data = "";//当前输入的数据

    /**
     * isLeftAvailable：判断数据应该向哪一个操作数中存储
     */
    private boolean isLeftAvailable;

    /**
     * left, right：左右操作数
     */
    private double left, right;

    private String prevOperaotor = "";


    /**
     * init：初始化 frame
     */
    public void init()
    {
        setMyFrame();// 初始化 frame
        setMyIcon();// 初始化图标
        setMyTextField();// 初始化文本域
        setMyButton();// 初始化按钮
        setMyLabel();// 初始化标签
        display();// 显示 frame
    }

    /**
     *  setMyFrame：
     *  @description: 设置窗体
     */
    private void setMyFrame() {
        frame = new JFrame();
        frame.setLocation(700, 150);
        frame.setSize(550, 540); // Increased width to accommodate new column
        frame.setTitle("Shepard's Calculator");
        frame.setResizable(true);
        frame.setLayout(null);
    }

    /**
     *  setMyIcon：
     *  @description: 设置图标
     */
    private void setMyIcon()
    {
        icon = new ImageIcon("D:\\Java\\JavaCode\\JavaSE\\Calculator\\img\\1.jpg");
        // 添加图片
        frame.setIconImage(icon.getImage());
    }

    //设置左边
    private void setFinanceButtons() {
        financeButtons = new RoundedCornerButton[4];
        String[] labels = {"金融", "科学", "BMI", "我的"};

        for (int i = 0; i < financeButtons.length; i++) {
            financeButtons[i] = new RoundedCornerButton(labels[i]);
            financeButtons[i].setBounds(20, 90 + i * 88, 90, 80);
            financeButtons[i].setFont(new Font("黑体", Font.CENTER_BASELINE, 20));
            financeButtons[i].setBackground(new Color(242, 240, 235));

            int idx = i;
            financeButtons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (idx == 0) { // "金融" button
                        new FinanceCalculator().init();
                    }
                    else if (idx == 1) {// "科学" button
                        new ScientificCalculator().init();
                    }
                    else if (idx == 2) {// "BMI" button
                        new BMI_cal();
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    financeButtons[idx].setFont(new Font("黑体", Font.CENTER_BASELINE, 35));
                    financeButtons[idx].setBackground(new Color(240, 255, 255));
                    financeButtons[idx].setForeground(new Color(255, 99, 71));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    financeButtons[idx].setFont(new Font("黑体", Font.CENTER_BASELINE, 20));
                    financeButtons[idx].setBackground(new Color(242, 240, 235));
                    financeButtons[idx].setForeground(new Color(0, 0, 0));
                }
            });

            frame.add(financeButtons[i]);
        }
    }


    private void setMyTextField() {
        textField = new JTextField("0");
        textField.setBounds(120, 15, 400, 60); // Moved to the right
        textField.setFont(new Font("黑体", Font.BOLD, 35));
        textField.setBackground(new Color(230, 230, 250));
        frame.add(textField);
    }

    /**
     *  setMyButton：
     *  @description: 设置按键事件
     */
    private void setMyButton()
    {
        // 按钮文本
        String[] arr =
                          { "退格","清空","%","/",
                            "7","8","9","*",
                            "4","5","6","+",
                            "1","2","3","-",
                            "+/-","0",".","=", };
        // 按钮
        button = new RoundedCornerButton[arr.length];

        // 创建面板
        panel = new JPanel();

        // 设置面板的布局方式
        panel.setBounds(120, 90, 400, 350);

        // 网格布局
        panel.setLayout(new GridLayout(5, 4, 8, 8));

        for(int i = 0; i < button.length; i++)
        {
            // 创建按钮
            button[i] = new RoundedCornerButton(arr[i]);

            // 设置按钮字体
            button[i].setFont(new Font("黑体", Font.CENTER_BASELINE, 20));
            // 设置按钮背景颜色
            button[i].setBackground(new Color(242, 240, 235));

            // 设置 = 号的特殊颜色
            if(button.length - 1 == i)
            {
                button[i].setBackground(new Color(211, 120, 129));
            }

            // 添加事件
            int idx = i;
            // 设置鼠标监听
            button[i].addMouseListener(new MouseAdapter() {
                // 点击事件
                @Override
                public void mouseClicked(MouseEvent e) {
                    // 获取按钮上的内容
                    click(button[idx].getText());
                }

                // 鼠标进入组件事件
                @Override
                public void mouseEntered(MouseEvent e) {
                    button[idx].setFont(new Font("黑体", Font.CENTER_BASELINE, 35));
                    button[idx].setBackground(new Color(240, 255, 255));
                    button[idx].setForeground(new Color(255, 99, 71));
                }

                // 鼠标离开组件事件
                @Override
                public void mouseExited(MouseEvent e) {
                    button[idx].setFont(new Font("黑体", Font.CENTER_BASELINE, 20));
                    button[idx].setBackground(new Color(242, 240, 235));
                    button[idx].setForeground(new Color(0, 0, 0));
                }
            });

            // 按钮添加到面板
            panel.add(button[i]);
            setFinanceButtons();
        }

        frame.add(panel);
    }
    // 退格功能
    private void backspace() {
        if (!data.isEmpty()) {
            data = data.substring(0, data.length() - 1);
            updateTextField();
        }
    }
    // 清空功能
    private void clear() {
        data = "";
        updateTextField();
    }


    /**
     *  setMyLabel：
     *  @description: 设置标签
     */
    private void setMyLabel()
    {
        // 标签
        label = new JLabel();
        label.setBounds(40, 460, 300, 40);
        frame.add(label);
    }

    public void click(String content)
    {
        String operator = "";

        if("1".equals(content)) {
            data += "1";
            textField.setText(data);
        }else if("2".equals(content)){
            data += "2";
            textField.setText(data);
        }else if("3".equals(content)){
            data += "3";
            textField.setText(data);
        }else if("4".equals(content)){
            data += "4";
            textField.setText(data);
        }else if("5".equals(content)){
            data += "5";
            textField.setText(data);
        }else if("6".equals(content)){
            data += "6";
            textField.setText(data);
        }else if("7".equals(content)){
            data += "7";
            textField.setText(data);
        }else if("8".equals(content)){
            data += "8";
            textField.setText(data);
        }else if("9".equals(content)){
            data += "9";
            textField.setText(data);
        }else if("0".equals(content)){
            data += "0";
            textField.setText(data);
        }else if(".".equals(content)){
            data += ".";
            textField.setText(data);
        }else if("+/-".equals(content)){
            // 找不到负号，代表这是正数
            if(data.indexOf('-') < 0)
            {
                data = "-" + data;
            }
            // 负数
            else
            {
                data = data.substring(1);
            }
            textField.setText(data);
        }else if("%".equals(content)){
            data = Double.parseDouble(data) / 100 + "";
            textField.setText(data);
        }else if("+".equals(content)){
            operator = "+";
            cal(operator);
        }else if("-".equals(content)){
            operator = "-";
            cal(operator);
        }else if("*".equals(content)){
            operator = "*";
            cal(operator);
        }else if("/".equals(content)){
            operator = "/";
            cal(operator);
        }else if("=".equals(content)){
            operator = "=";
            cal(operator);
        }else if("del".equals(content)){
            if(data.length() != 0)
            {
                data = data.substring(0, data.length() - 1);
            }
            textField.setText(data);
        }else if("cls".equals(content)){
            data = "";
            isLeftAvailable = false;
            textField.setText(data);
        }else if("退格".equals(content)){
            backspace();
        }else if("清空".equals(content)){
                clear();
        }

    }

    /**
     * @method: display
     * @description: 设置 frame
     * @note: 如果把 display 函数内的语句写在 setMyFrame 方法中，frame 中显示不出来 label
     *        且需要点击一下 文本域 后才能显示文本域
     */
    public void display()
    {
        // 设置关闭 frame 窗口时退出程序
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置 frame 可见，默认值为 false
        frame.setVisible(true);
    }

    private void cal(String operator)
    {
        // 如果 data 为空，不执行任何操作
        if("".equals(data))
        {
            // 用户只要输入 = ，无论前面的输入是否合法，结束此轮计算
            if("=".equals(operator))
            {
                isLeftAvailable = false;
            }
            return;
        }

        /**
         * 第一次把 data 中的数据赋给 left
         * 此后，每次运算都将 right 与 left 运算的结果放入 left 中，并显示 left
         * 运算符 = 使用后可以重新向 left 中输入值
         * 每次获取到 data 中的值后清空 data
         *
         * 每次运算使用上一次传入的运算符，等号出现重新开始新的计算
         */

        if(isLeftAvailable)
        {
            right = Double.parseDouble(data);
            data = "";

            // 使用前一个运算符
            if("+".equals(prevOperaotor))
            {
                left += right;
            }
            else if("-".equals(prevOperaotor))
            {
                left -= right;
            }
            else if("*".equals(prevOperaotor))
            {
                left *= right;
            }
            else if("/".equals(prevOperaotor))
            {
                left /= right;
            }

            // 如果运算结果为整数，就用整数输出
            if(left == (int)left)
            {
                left = (int)left;
            }

        }
        else
        {
            left = Double.parseDouble(data);
            // 清空 data
            data = "";
            isLeftAvailable = true;
        }

        // 将运算结果转换为字符串
        String result = left + "";

        // "=" 操作符清理右操作数，重新开始计算
        if("=".equals(operator))
        {
            isLeftAvailable = false;
        }
        // 更新运算符
        prevOperaotor = operator;
        textField.setText(textFormat(result) );
    }

    private String textFormat(String s)
    {
        //创建一个格式化数字的对象
        NumberFormat nf = NumberFormat.getInstance();
        //        //格式化
        String fomatResult = nf.format(Double.parseDouble(s));
        return fomatResult;
    }

//自定义按钮
    public static class RoundedCornerButton extends JButton {

        public RoundedCornerButton(String text) {
            super(text);
            setContentAreaFilled(true);//填充背景
            setFocusPainted(false);//不绘制焦点
            setBorderPainted(true);//不绘制边框
        }

        //自定义按钮
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 绘制钝角的边缘
            int width = getWidth();
            int height = getHeight();
            int cornerRadius = 10; // 钝角半径，可以根据需要调整
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);

            // 绘制按钮的文本和其他内容
            super.paintComponent(g2d);
            g2d.dispose();
        }
    }
    // 更新文本域显示
    private void updateTextField() {
        textField.setText(data.isEmpty()? "0" : data);
    }
}
