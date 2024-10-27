import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BMI_cal extends JFrame {
    private JTextField heightField;
    private JTextField weightField;
    private JLabel resultLabel;

    public BMI_cal() {
        init();
    }

    private void init() {
        setTitle("BMI 计算器");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        JLabel heightLabel = new JLabel("身高（米）：");
        heightField = new JTextField();
        JLabel weightLabel = new JLabel("体重（公斤）：");
        weightField = new JTextField();
        JButton calculateButton = new JButton("计算");
        resultLabel = new JLabel("BMI 指数：");

        panel.add(heightLabel);
        panel.add(heightField);
        panel.add(weightLabel);
        panel.add(weightField);
        panel.add(calculateButton);
        panel.add(resultLabel);

        add(panel, BorderLayout.CENTER);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateBMI();
            }
        });

        setVisible(true);
    }

    private void calculateBMI() {
        try {
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());

            if (height <= 0 || weight <= 0) {
                throw new IllegalArgumentException("身高和体重必须大于0");
            }

            double bmi = weight / (height * height);
            resultLabel.setText("BMI 指数：" + String.format("%.2f", bmi));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字", "错误", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BMI_cal().setVisible(true);
        });
    }
}
