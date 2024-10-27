import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

public class FinanceCalculator implements KeyListener {// 计算器类
    private JFrame frame;
    private JTextField textField;
    private JButton[] buttons;
    private JPanel panel;
    private String currentCalculation = "";

    public void init() {// 初始化计算器窗口
        setMyFrame();
        setMyTextField();
        setMyButton();
        frame.setVisible(true);
    }

    private void setMyFrame() {// 初始化计算器窗口
        frame = new JFrame();
        frame.setLocation(700, 150);
        frame.setSize(550, 540);
        frame.setTitle("金融计算器");
        frame.setResizable(false);
        frame.setLayout(null);
    }

    private void setMyTextField() {// 初始化文本框
        textField = new JTextField("0");
        textField.setBounds(20, 15, 500, 60);
        textField.setFont(new Font("黑体", Font.BOLD, 35));
        textField.setBackground(new Color(230, 230, 250));
        frame.add(textField);
    }

    private void setMyButton() {// 初始化按钮
        panel = new JPanel();
        panel.setBounds(20, 90, 500, 400);
        panel.setLayout(new GridLayout(5, 4, 10, 10));
        panel.setBackground(new Color(240, 240, 240));

        String[] buttonLabels = {
                "复利", "贷款", "回报率", "C",
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        buttons = new JButton[buttonLabels.length];

        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setFont(new Font("黑体", Font.BOLD, 20));
            buttons[i].setBackground(new Color(242, 240, 235));

            int index = i;
            buttons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    buttonClicked(buttonLabels[index]);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    buttons[index].setFont(new Font("黑体", Font.BOLD, 25));
                    buttons[index].setBackground(new Color(240, 255, 255));
                    buttons[index].setForeground(new Color(255, 99, 71));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    buttons[index].setFont(new Font("黑体", Font.BOLD, 20));
                    buttons[index].setBackground(new Color(242, 240, 235));
                    buttons[index].setForeground(Color.BLACK);
                }
            });

            panel.add(buttons[i]);
        }

        frame.add(panel);
    }

    private void buttonClicked(String label) {// 按钮点击事件
        switch (label) {
            case "复利":
                currentCalculation = "复利";
                textField.setText("输入: 本金,年利率,年限,复利次数/年");
                break;
            case "贷款":
                currentCalculation = "贷款";
                textField.setText("输入: 贷款金额,年利率,期限(月),还款方式(1/2)");
                break;
            case "回报率":
                currentCalculation = "回报率";
                textField.setText("输入: 初始投资,最终价值,投资年限");
                break;
            case "C":
                currentCalculation = "";
                textField.setText("0");
                break;
            case "=":
                calculate();
                break;
            default:
                if (textField.getText().equals("0") || textField.getText().startsWith("输入:")) {
                    textField.setText(label);
                } else {
                    textField.setText(textField.getText() + label);
                }
                break;
        }
    }

    private void calculate() {// 计算结果
        String input = textField.getText();
        String[] values = input.split(",");
        DecimalFormat df = new DecimalFormat("#.##");

        try {
            switch (currentCalculation) {
                case "复利":
                    if (values.length != 4) {
                        textField.setText("输入错误，请重新输入");
                        return;
                    }
                    double principal = Double.parseDouble(values[0]);
                    double rate = Double.parseDouble(values[1]) / 100;
                    int years = Integer.parseInt(values[2]);
                    int compoundsPerYear = Integer.parseInt(values[3]);
                    double amount = principal * Math.pow(1 + (rate / compoundsPerYear), compoundsPerYear * years);
                    double interest = amount - principal;
                    textField.setText("最终金额: " + df.format(amount) + ", 利息: " + df.format(interest));
                    break;
                case "贷款":
                    if (values.length != 4) {
                        textField.setText("输入错误，请重新输入");
                        return;
                    }
                    double loanAmount = Double.parseDouble(values[0]);
                    double annualRate = Double.parseDouble(values[1]) / 100;
                    int months = Integer.parseInt(values[2]);
                    int repaymentType = Integer.parseInt(values[3]);
                    double monthlyRate = annualRate / 12;
                    if (repaymentType == 1) { // 等额本息
                        double monthlyPayment = loanAmount * monthlyRate * Math.pow(1 + monthlyRate, months)
                                / (Math.pow(1 + monthlyRate, months) - 1);
                        double totalPayment = monthlyPayment * months;
                        textField.setText("月还款: " + df.format(monthlyPayment) + ", 总还款: " + df.format(totalPayment));
                    } else if (repaymentType == 2) { // 等额本金
                        double monthlyPrincipal = loanAmount / months;
                        double firstMonthPayment = monthlyPrincipal + loanAmount * monthlyRate;
                        double lastMonthPayment = monthlyPrincipal + monthlyPrincipal * monthlyRate;
                        textField.setText("首月还款: " + df.format(firstMonthPayment) + ", 末月还款: " + df.format(lastMonthPayment));
                    } else {
                        textField.setText("还款方式输入错误，请输入1或2");
                    }
                    break;
                case "回报率":
                    if (values.length != 3) {
                        textField.setText("输入错误，请重新输入");
                        return;
                    }
                    double initialInvestment = Double.parseDouble(values[0]);
                    double finalValue = Double.parseDouble(values[1]);
                    int investmentYears = Integer.parseInt(values[2]);
                    double rateOfReturn = Math.pow(finalValue / initialInvestment, 1.0 / investmentYears) - 1;
                    textField.setText("年化收益率: " + df.format(rateOfReturn * 100) + "%");
                    break;
                default:
                    textField.setText("请先选择计算类型");
                    break;
            }
        } catch (NumberFormatException e) {
            textField.setText("输入错误，请检查后重新输入");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}