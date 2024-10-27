import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

public class ScientificCalculator {
    private JFrame frame;// 计算器窗口
    private JTextField textField;// 文本框
    private JButton[] buttons;// 按钮
    private JPanel panel;// 面板
    private String currentInput = "";// 输入的当前值
    private double memory = 0;// 内存值

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
        frame.setTitle("科学计算器");
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
        panel.setLayout(new GridLayout(6, 5, 10, 10));
        panel.setBackground(new Color(240, 240, 240));

        String[] buttonLabels = {
                "sin", "cos", "tan", "^", "√",
                "log", "ln", "e^x", "π", "!",
                "MC", "MR", "M+", "M-", "C",
                "7", "8", "9", "/", "(",
                "4", "5", "6", "*", ")",
                "1", "2", "3", "-", "=",
                "0", ".", "+/-", "+", "Ans"
        };

        buttons = new JButton[buttonLabels.length];

        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setFont(new Font("黑体", Font.BOLD, 16));
            buttons[i].setBackground(new Color(242, 240, 235));

            int index = i;
            buttons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    buttonClicked(buttonLabels[index]);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    buttons[index].setFont(new Font("黑体", Font.BOLD, 18));
                    buttons[index].setBackground(new Color(240, 255, 255));
                    buttons[index].setForeground(new Color(255, 99, 71));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    buttons[index].setFont(new Font("黑体", Font.BOLD, 16));
                    buttons[index].setBackground(new Color(242, 240, 235));
                    buttons[index].setForeground(Color.BLACK);
                }
            });

            panel.add(buttons[i]);
        }

        frame.add(panel);
    }

    private void buttonClicked(String label) {// 按钮点击事件处理
        switch (label) {
            case "C":
                currentInput = "";
                textField.setText("0");
                break;
            case "=":
                calculate();
                break;
            case "sin":
            case "cos":
            case "tan":
            case "log":
            case "ln":
            case "√":
                currentInput += label + "(";
                textField.setText(currentInput);
                break;
            case "π":
                currentInput += Math.PI;
                textField.setText(currentInput);
                break;
            case "e^x":
                currentInput += "e^(";
                textField.setText(currentInput);
                break;
            case "!":
                calculate();
                double factorial = factorial(Double.parseDouble(textField.getText()));
                textField.setText(String.valueOf(factorial));
                currentInput = String.valueOf(factorial);
                break;
            case "MC":
                memory = 0;
                break;
            case "MR":
                currentInput += memory;
                textField.setText(currentInput);
                break;
            case "M+":
                calculate();
                memory += Double.parseDouble(textField.getText());
                break;
            case "M-":
                calculate();
                memory -= Double.parseDouble(textField.getText());
                break;
            case "+/-":
                if (currentInput.startsWith("-")) {
                    currentInput = currentInput.substring(1);
                } else {
                    currentInput = "-" + currentInput;
                }
                textField.setText(currentInput);
                break;
            case "Ans":
                // 使用上一次计算的结果
                break;
            default:
                currentInput += label;
                textField.setText(currentInput);
                break;
        }
    }

    private void calculate() {// 计算表达式
        try {
            String expression = currentInput.replaceAll("π", String.valueOf(Math.PI))
                    .replaceAll("e", String.valueOf(Math.E));
            double result = evaluateExpression(expression);
            DecimalFormat df = new DecimalFormat("#.######");
            textField.setText(df.format(result));
            currentInput = String.valueOf(result);
        } catch (Exception e) {
            textField.setText("错误");
            currentInput = "";
        }
    }

    private double evaluateExpression(String expression) {// 计算表达式
        // 这里应该实现一个完整的表达式求值器
        // 为了简化，我们只实现基本的四则运算和一些数学函数
        // 在实际应用中，您可能需要使用更复杂的表达式解析库
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = expression.substring(startPos, this.pos);
                    x = parseFactor();
                    switch (func) {
                        case "sin": x = Math.sin(Math.toRadians(x)); break;
                        case "cos": x = Math.cos(Math.toRadians(x)); break;
                        case "tan": x = Math.tan(Math.toRadians(x)); break;
                        case "log": x = Math.log10(x); break;
                        case "ln": x = Math.log(x); break;
                        case "sqrt": x = Math.sqrt(x); break;
                        default: throw new RuntimeException("Unknown function: " + func);
                    }
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    private double factorial(double n) {// 计算阶乘
        if (n < 0) throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        if (n == 0 || n == 1) return 1;
        double result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}