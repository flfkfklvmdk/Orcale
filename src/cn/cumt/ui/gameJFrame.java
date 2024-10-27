package cn.cumt.ui;

import java.awt.event.*;
import java.io.File;
import java.util.Objects;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.BevelBorder;
//绝对路径：盘符:\\路径
//相对路径
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
    //创建条目图像
    JMenu changeMenu=new JMenu("更换图片");
    JMenuItem replayItem=new JMenuItem("重新游戏");
    JMenuItem reloginItem=new JMenuItem("重新登录");
    JMenuItem closeItem=new JMenuItem("关闭游戏");

    //在changeItem下方添加更多选择
    JMenuBar meNvBar=new JMenuBar();
    JMenuBar dongWuBar=new JMenuBar();
    JMenuBar yunDongBar=new JMenuBar();
    JMenuItem MeNv=new JMenuItem("美女");
    JMenuItem dongWu=new JMenuItem("动物");
    JMenuItem yunDong=new JMenuItem("运动");


    JMenuItem accountItem=new JMenuItem("公众号");


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

        //让界面显示出来
        this.setVisible(true);

        //添加点击监听
        addMouseListener(this);

    }

    //初始化菜单
    private void initJMenuBar() {
        //初始化菜单
        //创建整个菜单
        JMenuBar jMenuBar=new JMenuBar();

        //创建菜单上的俩个对象(功能，关于我们)
        JMenu fuctionJMenu=new JMenu("功能");
        JMenu aboutJMenu=new JMenu("关于我");

        //将每一个选项下面条目添加到选项当中
        fuctionJMenu.add(changeMenu);
        fuctionJMenu.add(replayItem);
        fuctionJMenu.add(reloginItem);
        fuctionJMenu.add(closeItem);

        changeMenu.add(meNvBar);
        changeMenu.add(dongWuBar);
        changeMenu.add(yunDongBar);

        meNvBar.add(MeNv);
        dongWuBar.add(dongWu);
        yunDongBar.add(yunDong);

        aboutJMenu.add(accountItem);

        //给条目绑定事件
        replayItem.addActionListener(this);
        reloginItem.addActionListener(this);
        closeItem.addActionListener(this);
        MeNv.addActionListener(this);
        dongWu.addActionListener(this);
        yunDong.addActionListener(this);

        accountItem.addActionListener(this);

        //将菜单中的俩个选项添加到菜单当中
        jMenuBar.add(fuctionJMenu);
        jMenuBar.add(aboutJMenu);

        //给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
    }

    //初始化数据（）打乱
    private void initData(){
        //1.先打乱
        int []temp={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        Random r=new Random();
        //遍历数组得到每一个元素，与随机索引上的数据进行交换
        for(int i=0;i<temp.length;i++){
            //拿遍历得到的每一个数据与随机索引上的数据进行交换
            int index=r.nextInt(temp.length);
            int t=temp[i];
            temp[i]=temp[index];
            temp[index]=t;
        }
        //4.给二维数组添加数据
        //解法一
        for (int i = 0; i < temp.length; i++) {
            data[i/4][i%4]=temp[i];
        }
        //收集移动数据
        for(int i=0;i<temp.length;i++){
            if(temp[i]==0){
                x=i/4;
                y=i%4;
            }
        }
    }

    //初始化图片(根据打乱结果加载图片)
    //添加图片需要按照data[][]来添加图片
    private void initImage() {
        //清空当前界面的所有图片
        this.getContentPane().removeAll();

        if (victory()) {
            //显示胜利的图标
            winImage.setBounds(203,283,197,73);
            this.getContentPane().add(winImage);
        }
        //计步数功能
        JLabel stepCount=new JLabel("步数"+step);
        stepCount.setBounds(100,80,107,73);
        this.getContentPane().add(stepCount);
        //外循环
        //启动计时器
        for(int i=0;i<4;i++){
            //内循环
            for(int j=0;j<4;j++) {
                //获得数组内容
                //创建一个ImageIcon的对象
                //创建一个JLabel的对象(管理容器)
                JLabel imageLabel = new JLabel(new ImageIcon(path+"\\"+data[i][j]+".jpg"));
                //指定图片位置
                imageLabel.setBounds(105 * j+122, 105*i+138, 105, 105);
                //给图片加上边框
                imageLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                // 添加鼠标监听器
                imageLabel.addMouseListener(this);
                //JFrame里面还有个容器承载组件
                //获取隐藏容器
                this.getContentPane().add(imageLabel);
            }
        }
        //添加背景图片
        //把管理容器添加到界面中(先加载的图片会覆盖后加载的图片)
        //背景图片需要在最后添加
        JLabel backgroundLabel=new JLabel(new ImageIcon("src/cn/cumt/ui/image/background.png"));//相对路径(相对于这个项目而言)，从项目第二位开始写
        backgroundLabel.setBounds(40, 40, 588, 560);
        this.getContentPane().add(backgroundLabel);
        //刷新界面
        this.getContentPane().repaint();
    }

    //初始化界面
    private void initJFrame() {
        //设置界面宽，高
        this.setSize(603,680);
        //设置标题
        this.setTitle("game_1.0");
        //设置界面置顶(不会受其他应用的影响)
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //取消默认放置方式
        this.setLayout(null);
        //给整个界面添加事件
        this.addKeyListener(this);
    }


    //安排动作监听
    @Override
    public void keyTyped(KeyEvent e) {
    }
    //按下不松会调用方法
    @Override
    public void keyPressed(KeyEvent e) {
        int code=e.getKeyCode();
        if(code==65){
            //把界面中所有图片全部删除
            this.getContentPane().removeAll();
            //加载第一张完整的图片
            JLabel all=new JLabel(new ImageIcon(path+"\\all.jpg"));
            all.setBounds(80,80,420,420);
            this.getContentPane().add(all);
            //刷新界面
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //判断游戏是否胜利，如果胜利则不能再继续
        if(victory())
            return;
//判断左（37），上（38），右（39），下（48）
        int code=e.getKeyCode();
        if(code==37) {
            System.out.println("向左移动");
            if(y==3)
                return;
            data[x][y]=data[x][y+1];
            data[x][y+1]=0;
            y++;//空格索引变化
            //计步数
            step++;
            //调用方法重新加载图片
            initImage();
        }
        else if(code==38) {
            System.out.println("向上移动");
            if(x==3)
                return;
            data[x][y]=data[x+1][y];
            data[x+1][y]=0;
            x++;
            step++;
            //调用方法重新加载图片
            initImage();
        }
        else if(code==39) {
            System.out.println("向右移动");
            if(y==0)
                return;
            data[x][y]=data[x][y-1];
            data[x][y-1]=0;
            y--;
            step++;
            //调用方法重新加载图片
            initImage();
        }
        else if(code==40) {
            System.out.println("向下移动");
            if(x==0)
                return;
            data[x][y]=data[x-1][y];
            data[x-1][y]=0;
            x--;
            step++;
            //调用方法重新加载图片
            initImage();
        }
        else if(code==65){
            initImage();
        }
        else if(code==87){//一键通关
            data=new int[][]{
                    {1,2,3,4},
                    {5,6,7,8},
                    {9,10,11,12},
                    {13,14,15,0}
            };
            initImage();
        }
        else if(code==77){
            path="src/cn/cumt/ui/image/girl";
            File file=new File(path);//获取当前目录
            int fileCount= Objects.requireNonNull(file.listFiles()).length;//获取当前目录文件个数
            Random r=new Random();
            int random=r.nextInt(fileCount)+1;//随机获取一个图片
            initImage();
            path="src/cn/cumt/ui/image/girl/"+"girl"+random;
            //刷新
            initData();
            initImage();
        }
        else if(code==68){
            path="src/cn/cumt/ui/image/animal";
            File file=new File(path);//获取当前目录
            int fileCount= Objects.requireNonNull(file.listFiles()).length;//获取当前目录文件个数
            Random r=new Random();
            int random=r.nextInt(fileCount)+1;//随机获取一个图片
            path="src/cn/cumt/ui/image/animal/"+"animal"+random;
            //刷新
            initData();
            initImage();
        }
        else if(code==84){
            path="src/cn/cumt/ui/image/sport";
            File file=new File(path);//获取当前目录
            int fileCount= Objects.requireNonNull(file.listFiles()).length;//获取当前目录文件个数
            Random r=new Random();
            int random=r.nextInt(fileCount)+1;//随机获取一个图片
            path="src/cn/cumt/ui/image/sport/"+"sport"+random;
            //刷新
            initData();
            initImage();
        }
        System.out.println(code);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //获取当前被点击的对象
        Object obj=e.getSource();
        if(obj==replayItem){
            //再次打乱数据
            initData();
            //计步器清零
            step=0;
            //重新加载图片(此时会加载上一个计步数的步数所以注意顺序)
            initImage();
        }
        else if(obj==reloginItem){
            //关闭当前界面
            this.setVisible(false);
            //打开登录界面
            new LoginJFrame();
        }
        else if(obj==closeItem){
            System.exit(0);
        }
        else if(obj==accountItem){
           //创建一个弹框对象
            JDialog about=new JDialog();
            //创建一个容器对象
            JLabel jlabel=new JLabel(new ImageIcon("src/cn/cumt/ui/image/about_me.jpg"));
            //设置位置和宽高
            jlabel.setBounds(0,0,1108,1512);//x,y指的是相对于弹框的位置
            //把图片加载到弹框中
            about.getContentPane().add(jlabel);
            //给弹框设置大小
            about.setSize(400,500);
            //让界面置顶
            about.setAlwaysOnTop(true);
            //让弹框居中
            about.setLocationRelativeTo(null);
            //设置弹框不关闭则无法操作下面的界面
            about.setModal(true);
            //让弹框显示
            about.setVisible(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // 获取被点击的标签
        JLabel clickedLabel = (JLabel) e.getSource();

        // 从标签名称中获取位置信息
        String[] position = clickedLabel.getName().split(",");
        int clickedX = Integer.parseInt(position[0]);
        int clickedY = Integer.parseInt(position[1]);

        // 检查点击的位置是否在空白方块附近
        if ((Math.abs(clickedX - x) == 1 && clickedY == y) ||
                (Math.abs(clickedY - y) == 1 && clickedX == x)) {
            // 交换数据
            data[x][y] = data[clickedX][clickedY];
            data[clickedX][clickedY] = 0;

            // 更新空白方块的位置
            x = clickedX;
            y = clickedY;

            // 增加步数
            step++;

            // 重新加载图片
            initImage();

            // 检查是否胜利
            if (victory()) {
                JOptionPane.showMessageDialog(this, "恭喜你，游戏胜利！");
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

