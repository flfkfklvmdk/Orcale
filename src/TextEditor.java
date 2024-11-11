import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TextEditor extends JFrame {
    private JTextPane textPane;
    private JFileChooser fileChooser;
    private String currentFile = "";
    private JMenuBar menuBar;
    private StyledDocument doc;
    private Style style;

    public TextEditor() {
        // 设置窗口标题和大小
        setTitle("文本编辑器");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 初始化组件
        initComponents();

        // 创建菜单栏
        createMenuBar();

        // 设置窗口居中显示
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // 初始化文本区域
        textPane = new JTextPane();
        doc = textPane.getStyledDocument();
        style = textPane.addStyle("Style", null);

        // 添加滚动条
        JScrollPane scrollPane = new JScrollPane(textPane);
        add(scrollPane);

        // 初始化文件选择器
        fileChooser = new JFileChooser();
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();

        // 文件菜单
        JMenu fileMenu = new JMenu("文件");
        addMenuItem(fileMenu, "新建", e -> newFile());
        addMenuItem(fileMenu, "打开", e -> openFile());
        addMenuItem(fileMenu, "保存", e -> saveFile());
        addMenuItem(fileMenu, "另存为", e -> saveFileAs());

        // 编辑菜单
        JMenu editMenu = new JMenu("编辑");
        addMenuItem(editMenu, "复制", e -> textPane.copy());
        addMenuItem(editMenu, "粘贴", e -> textPane.paste());
        addMenuItem(editMenu, "剪切", e -> textPane.cut());
        addMenuItem(editMenu, "查找", e -> findText());
        addMenuItem(editMenu, "替换", e -> replaceText());

        // 格式菜单
        JMenu formatMenu = new JMenu("格式");
        JMenu alignMenu = new JMenu("对齐方式");
        addMenuItem(alignMenu, "左对齐", e -> setAlignment(StyleConstants.ALIGN_LEFT));
        addMenuItem(alignMenu, "居中", e -> setAlignment(StyleConstants.ALIGN_CENTER));
        addMenuItem(alignMenu, "右对齐", e -> setAlignment(StyleConstants.ALIGN_RIGHT));
        formatMenu.add(alignMenu);

        // 字体菜单
        JMenu fontMenu = new JMenu("字体");
        addMenuItem(fontMenu, "加粗", e -> toggleBold());
        addMenuItem(fontMenu, "斜体", e -> toggleItalic());
        addMenuItem(fontMenu, "下划线", e -> toggleUnderline());
        addMenuItem(fontMenu, "字体大小", e -> setFontSize());
        addMenuItem(fontMenu, "字体", e -> setFontFamily()); // 添加字体设置菜单项

        // 添加所有菜单到菜单栏
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(fontMenu);

        setJMenuBar(menuBar);
    }

    private void addMenuItem(JMenu menu, String title, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.addActionListener(listener);
        menu.add(menuItem);
    }

    private void newFile() {
        textPane.setText("");
        currentFile = "";
        setTitle("文本编辑器");
    }

    private void openFile() {
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                FileReader reader = new FileReader(file);
                textPane.read(reader, null);
                currentFile = file.getPath();
                setTitle("文本编辑器 - " + file.getName());
                reader.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "打开文件失败");
            }
        }
    }

    private void saveFile() {
        if (currentFile.equals("")) {
            saveFileAs();
        } else {
            try {
                FileWriter writer = new FileWriter(currentFile);
                textPane.write(writer);
                writer.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "保存文件失败");
            }
        }
    }

    private void saveFileAs() {
        int returnVal = fileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                FileWriter writer = new FileWriter(file);
                textPane.write(writer);
                currentFile = file.getPath();
                setTitle("文本编辑器 - " + file.getName());
                writer.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "保存文件失败");
            }
        }
    }

    private void findText() {
        String searchText = JOptionPane.showInputDialog(this, "请输入要查找的文本：");
        if (searchText != null && !searchText.isEmpty()) {
            String content = textPane.getText();
            int index = content.indexOf(searchText);
            if (index != -1) {
                textPane.setSelectionStart(index);
                textPane.setSelectionEnd(index + searchText.length());
                textPane.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, "未找到指定文本");
            }
        }
    }

    private void replaceText() {
        String searchText = JOptionPane.showInputDialog(this, "请输入要替换的文本：");
        if (searchText != null && !searchText.isEmpty()) {
            String replaceText = JOptionPane.showInputDialog(this, "请输入新的文本：");
            if (replaceText != null) {
                String content = textPane.getText();
                content = content.replace(searchText, replaceText);
                textPane.setText(content);
            }
        }
    }

    private void setAlignment(int alignment) {
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attrs, alignment);
        doc.setParagraphAttributes(0, doc.getLength(), attrs, false);
    }

    private void toggleBold() {
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setBold(attrs, !StyleConstants.isBold(textPane.getCharacterAttributes()));
        textPane.setCharacterAttributes(attrs, false);
    }

    private void toggleItalic() {
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setItalic(attrs, !StyleConstants.isItalic(textPane.getCharacterAttributes()));
        textPane.setCharacterAttributes(attrs, false);
    }

    private void toggleUnderline() {
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setUnderline(attrs, !StyleConstants.isUnderline(textPane.getCharacterAttributes()));
        textPane.setCharacterAttributes(attrs, false);
    }

    private void setFontSize() {
        String input = JOptionPane.showInputDialog(this, "请输入字体大小（数字）：");
        try {
            int size = Integer.parseInt(input);
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setFontSize(attrs, size);
            textPane.setCharacterAttributes(attrs, false);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字");
        }
    }
    private void setFontFamily() {
        String[] fonts = {"宋体", "新宋体", "微软雅黑", "黑体", "楷体", "仿宋", "隶书", "幼圆", "Arial", "Times New Roman"};
        String selectedFont = (String) JOptionPane.showInputDialog(this, "请选择字体：", "字体选择", JOptionPane.PLAIN_MESSAGE, null, fonts, fonts[0]);
        if (selectedFont!= null) {
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setFontFamily(attrs, selectedFont);
            textPane.setCharacterAttributes(attrs, false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TextEditor().setVisible(true);
        });
    }
}