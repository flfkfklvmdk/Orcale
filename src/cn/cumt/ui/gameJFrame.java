package cn.cumt.ui;

import java.awt.event.*;
import java.io.File;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class gameJFrame extends JFrame implements KeyListener, ActionListener, MouseListener {
    //创建二维数组管理数据
    int [][]data=new int[4][4];
    //记录空白方块在二维数组中的位置
    int x,y=0;
    //定义变量记录展现图片的路径
    String path="src/cn/cumt/ui/image/animal/animal3";
    //正确的二维数组
    int [][]win={
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}
    };
    //胜利图片
    JLabel winImage=new JLabel(new ImageIcon("src/cn/cumt/ui/image/win.png"));
    //判断是否一致
    public boolean victory(){
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(data[i][j]!=win[i][j])
                    return false;
            }
        }
        return true;
    }
    //计步数成员
    int step=0;
    //计时工具
    int second=0;
    Timer timer;
    //创建新的菜单项
    JMenuItem showCompleteItem = new JMenuItem("查看完整图片");
    //用于显示完整图片的对话框
    JDialog completeImageDialog;
    //创建条目对象
    JMenu changeMenu = new JMenu("更换图片");
    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem reloginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");

    //创建更换图片的子选项
    JMenuItem Beauty = new JMenuItem("美女");
    JMenuItem Animal = new JMenuItem("动物");
    JMenuItem Sport = new JMenuItem("运动");

    JMenuItem accountItem = new JMenuItem("公众号");

    //Frame 界面窗口
    public gameJFrame(){
        //初始化界面
        initJFrame();
        //初始化菜单
        initJMenuBar();
        //初始化数据
        initData();
        //初始化图片
        initImage();
        //初始化时间
        initTime();
        //让界面显示出来
        this.setVisible(true);
        //添加点击监听
        addMouseListener(this);
    }

    private void initJMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我");

        changeMenu = new JMenu("更换图片");
        Beauty = new JMenuItem("美女");
        Animal = new JMenuItem("动物");
        Sport = new JMenuItem("运动");

        changeMenu.add(Beauty);
        changeMenu.add(Animal);
        changeMenu.add(Sport);

        functionJMenu.add(changeMenu);
        functionJMenu.add(showCompleteItem);  // 添加查看完整图片选项
        functionJMenu.add(replayItem);
        functionJMenu.add(reloginItem);
        functionJMenu.add(closeItem);

        aboutJMenu.add(accountItem);

        //给条目绑定事件
        replayItem.addActionListener(this);
        reloginItem.addActionListener(this);
        closeItem.addActionListener(this);
        Beauty.addActionListener(this);
        Animal.addActionListener(this);
        Sport.addActionListener(this);
        accountItem.addActionListener(this);
        showCompleteItem.addActionListener(this);  // 添加事件监听

        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);

        this.setJMenuBar(jMenuBar);
    }
    //初始化数据（）打乱
    private void initData(){
        //1.先打乱
        int []temp={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        Random r=new Random();
        for(int i=0;i<temp.length;i++){
            int index=r.nextInt(temp.length);
            int t=temp[i];
            temp[i]=temp[index];
            temp[index]=t;
        }

        for (int i = 0; i < temp.length; i++) {
            data[i/4][i%4]=temp[i];
            if(temp[i]==0){
                x=i/4;
                y=i%4;
            }
        }
    }

    private void initImage() {
        //清空当前界面的所有图片
        this.getContentPane().removeAll();

        if (victory()) {
            //显示胜利的图标
            winImage.setBounds(203,283,197,73);
            this.getContentPane().add(winImage);
            //停止计时器
            if(timer != null) {
                timer.cancel();
            }
        }

        //计步数功能
        JLabel stepCount=new JLabel("步数: "+step);
        stepCount.setBounds(100,100,100,20);
        this.getContentPane().add(stepCount);

        //计时显示
        JLabel timeCount=new JLabel("时间: "+second+"秒");
        timeCount.setBounds(200,100,100,20);
        this.getContentPane().add(timeCount);

        //加载拼图图片
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++) {
                JLabel imageLabel = new JLabel(new ImageIcon(path+"\\"+data[i][j]+".jpg"));
                imageLabel.setBounds(105 * j+122, 105*i+138, 105, 105);
                imageLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                imageLabel.setName(i+","+j);
                imageLabel.addMouseListener(this);
                this.getContentPane().add(imageLabel);
            }
        }

        //添加背景图片
        JLabel backgroundLabel=new JLabel(new ImageIcon("src/cn/cumt/ui/image/background.png"));
        backgroundLabel.setBounds(40, 40, 588, 560);
        this.getContentPane().add(backgroundLabel);

        //刷新界面
        this.getContentPane().repaint();
    }

    private void initJFrame() {
        this.setSize(603,680);
        this.setTitle("拼图游戏");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.addKeyListener(this);
    }

    private void initTime(){
        if(timer != null) {
            timer.cancel();
        }
        second = 0;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!victory()) {
                    second++;
                    SwingUtilities.invokeLater(() -> {
                        initImage();
                    });
                }
            }
        }, 0, 1000);
    }
    private void showCompleteImage() {
        // 如果对话框不存在或已被释放，创建新的对话框
        if (completeImageDialog == null || !completeImageDialog.isDisplayable()) {
            completeImageDialog = new JDialog(this, "完整图片", false);  // false表示非模态
            completeImageDialog.setLayout(null);

            // 创建完整图片标签
            JLabel completeImageLabel = new JLabel(new ImageIcon(path + "\\all.jpg"));
            completeImageLabel.setBounds(0, 0, 300, 300);  // 设置合适的大小
            completeImageDialog.add(completeImageLabel);

            // 设置对话框大小和位置
            completeImageDialog.setSize(320, 340);

            // 将对话框显示在游戏窗口的右侧
            int x = this.getX() + this.getWidth();
            int y = this.getY();
            completeImageDialog.setLocation(x, y);

            // 添加窗口移动监听器，使完整图片窗口跟随游戏窗口移动
            this.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentMoved(ComponentEvent e) {
                    if (completeImageDialog.isVisible()) {
                        int newX = gameJFrame.this.getX() + gameJFrame.this.getWidth();
                        int newY = gameJFrame.this.getY();
                        completeImageDialog.setLocation(newX, newY);
                    }
                }
            });
        }

        // 显示对话框
        completeImageDialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if(obj == replayItem){
            initData();
            step = 0;
            initTime();
            initImage();
            // 如果有显示完整图片，更新完整图片
            if (completeImageDialog != null && completeImageDialog.isVisible()) {
                completeImageDialog.dispose();
                showCompleteImage();
            }
        }
        else if(obj == reloginItem){
            if(timer != null) {
                timer.cancel();
            }
            // 关闭完整图片窗口
            if (completeImageDialog != null) {
                completeImageDialog.dispose();
            }
            this.setVisible(false);
            new LoginJFrame();
        }
        else if(obj == closeItem){
            System.exit(0);
        }
        else if(obj == accountItem){
            // ... 现有的代码 ...
        }
        else if(obj == Beauty || obj == Animal || obj == Sport){
            String category = "";
            if(obj == Beauty) category = "girl";
            else if(obj == Animal) category = "animal";
            else if(obj == Sport) category = "sport";

            String basePath = "src/cn/cumt/ui/image/" + category;
            File file = new File(basePath);
            int fileCount = Objects.requireNonNull(file.listFiles()).length;
            Random r = new Random();
            int random = r.nextInt(fileCount) + 1;
            path = basePath + "/" + category + random;

            initData();
            initTime();
            initImage();
            // 更新完整图片
            if (completeImageDialog != null && completeImageDialog.isVisible()) {
                completeImageDialog.dispose();
                showCompleteImage();
            }
        }
        else if(obj == showCompleteItem){
            showCompleteImage();
        }
    }
    // KeyListener methods
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code=e.getKeyCode();
        if(code==65){
            this.getContentPane().removeAll();
            JLabel all=new JLabel(new ImageIcon(path+"\\all.jpg"));
            all.setBounds(80,80,420,420);
            this.getContentPane().add(all);
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (victory()) return;

        int code = e.getKeyCode();
        int originalX = x;
        int originalY = y;
        boolean moved = false;

        if (code == 37 && y < 3) { // 左
            data[x][y] = data[x][y + 1];
            data[x][y + 1] = 0;
            y++;
            moved = true;
        } else if (code == 38 && x < 3) { // 上
            data[x][y] = data[x + 1][y];
            data[x + 1][y] = 0;
            x++;
            moved = true;
        } else if (code == 39 && y > 0) { // 右
            data[x][y] = data[x][y - 1];
            data[x][y - 1] = 0;
            y--;
            moved = true;
        } else if (code == 40 && x > 0) { // 下
            data[x][y] = data[x - 1][y];
            data[x - 1][y] = 0;
            x--;
            moved = true;
        } else if(code == 87){ // 一键通关
            data = new int[][]{
                    {1,2,3,4},
                    {5,6,7,8},
                    {9,10,11,12},
                    {13,14,15,0}
            };
            initImage();
            // 如果有显示完整图片，保持显示
            if (completeImageDialog != null && completeImageDialog.isVisible()) {
                completeImageDialog.dispose();
                showCompleteImage();
            }
        }
    }
    // MouseListener methods
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        JLabel clickedLabel = (JLabel) e.getSource();
        if (clickedLabel.getName() == null) {
            return;
        }

        String[] position = clickedLabel.getName().split(",");
        int clickedX = Integer.parseInt(position[0]);
        int clickedY = Integer.parseInt(position[1]);

        if ((Math.abs(clickedX - x) == 1 && clickedY == y) ||
                (Math.abs(clickedY - y) == 1 && clickedX == x)) {
            data[x][y] = data[clickedX][clickedY];
            data[clickedX][clickedY] = 0;
            x = clickedX;
            y = clickedY;
            step++;
            initImage();

            if (victory()) {
                JOptionPane.showMessageDialog(this, "恭喜你，游戏胜利！");
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}

