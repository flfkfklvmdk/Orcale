import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BMI_cal extends JFrame {
    private JTextField heightField;
    private JTextField weightField;
    private JLabel resultLabel;
    private Calculator.RoundedCornerButton calculateButton;

    public BMI_cal() {
        init();
    }

    private void init() {
        setTitle("BMI 计算器");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);  // 使用绝对布局
        getContentPane().setBackground(new Color(242, 240, 235));
        setVisible(true);

        // 创建标题标签
        JLabel titleLabel = new JLabel("BMI 计算器", SwingConstants.CENTER);
        titleLabel.setBounds(0, 10, 400, 40);
        titleLabel.setFont(new Font("黑体", Font.BOLD, 24));
        add(titleLabel);

        // 身高输入区域
        JLabel heightLabel = new JLabel("身高（米）：");
        heightLabel.setBounds(50, 60, 100, 30);
        heightLabel.setFont(new Font("黑体", Font.PLAIN, 14));
        add(heightLabel);

        heightField = new JTextField();
        heightField.setBounds(150, 60, 200, 30);
        heightField.setFont(new Font("黑体", Font.PLAIN, 14));
        heightField.setBackground(new Color(230, 230, 250));
        add(heightField);

        // 体重输入区域
        JLabel weightLabel = new JLabel("体重（公斤）：");
        weightLabel.setBounds(50, 100, 100, 30);
        weightLabel.setFont(new Font("黑体", Font.PLAIN, 14));
        add(weightLabel);

        weightField = new JTextField();
        weightField.setBounds(150, 100, 200, 30);
        weightField.setFont(new Font("黑体", Font.PLAIN, 14));
        weightField.setBackground(new Color(230, 230, 250));
        add(weightField);

        // 计算按钮
        calculateButton = new Calculator.RoundedCornerButton("计算");
        calculateButton.setBounds(150, 150, 100, 40);
        calculateButton.setFont(new Font("黑体", Font.CENTER_BASELINE, 16));
        calculateButton.setBackground(new Color(211, 120, 129));
        calculateButton.setForeground(Color.WHITE);

        // 添加鼠标事件
        calculateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                calculateButton.setFont(new Font("黑体", Font.CENTER_BASELINE, 20));
                calculateButton.setBackground(new Color(240, 255, 255));
                calculateButton.setForeground(new Color(255, 99, 71));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                calculateButton.setFont(new Font("黑体", Font.CENTER_BASELINE, 16));
                calculateButton.setBackground(new Color(211, 120, 129));
                calculateButton.setForeground(Color.WHITE);
            }
        });

        calculateButton.addActionListener(e -> calculateBMI());
        add(calculateButton);

        // 结果显示
        resultLabel = new JLabel("BMI 指数：", SwingConstants.CENTER);
        resultLabel.setBounds(50, 200, 300, 40);
        resultLabel.setFont(new Font("黑体", Font.BOLD, 16));
        add(resultLabel);
    }

    private void calculateBMI() {
        try {
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());

            if (height <= 0 || weight <= 0) {
                throw new IllegalArgumentException("身高和体重必须大于0");
            }

            double bmi = weight / (height * height);
            String category = getBMICategory(bmi);
            resultLabel.setText(String.format("BMI: %.2f (%s)", bmi, category));
            if (bmi < 14 || bmi > 40) {
                JOptionPane.showMessageDialog(this, "BMI 值不正常", "警告", JOptionPane.WARNING_MESSAGE);
            } else {
                resultLabel.setText(String.format("BMI: %.2f (%s)", bmi, category));
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字", "错误",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getBMICategory(double bmi) {
        if (bmi < 18.5) return "体重过轻";
        else if (bmi < 24) return "正常范围";
        else if (bmi < 28) return "超重";
        else return "肥胖";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BMI_cal());
    }
}