package boundary;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel mainPanel;

    public MainFrame() {
        //设置窗口标题为“餐饮管理系统”，窗口大小为1000x650，关闭时退出应用程序，且将窗口定位于屏幕中心。
        setTitle("餐饮管理系统");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 设置全局字体
        setUIFont(new Font("微软雅黑", Font.PLAIN, 16));

        // 菜单栏和菜单项，并添加图标
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("功能菜单");

        //创建多个菜单项，包括“员工管理”、“客户管理”、“餐台管理”等，每个菜单项添加一个图标。
        JMenuItem empManage = new JMenuItem("员工管理", new ImageIcon("icons/emp.png"));
        JMenuItem cusManage = new JMenuItem("客户管理", new ImageIcon("icons/cus.png"));
        JMenuItem deskManage = new JMenuItem("餐台管理", new ImageIcon("icons/desk.png"));
        JMenuItem catManage = new JMenuItem("菜品分类管理", new ImageIcon("icons/cat.png"));
        JMenuItem dishManage = new JMenuItem("菜品管理", new ImageIcon("icons/dish.png"));
        JMenuItem orderManage = new JMenuItem("开台管理", new ImageIcon("icons/open.png"));
        JMenuItem dishOrderManage = new JMenuItem("点菜管理", new ImageIcon("icons/order.png"));
        JMenuItem settleAccount = new JMenuItem("结账管理", new ImageIcon("icons/money.png"));
        JMenuItem consumptionRecord = new JMenuItem("消费记录", new ImageIcon("icons/record.png"));

        menu.add(empManage);
        menu.add(cusManage);
        menu.add(deskManage);
        menu.add(catManage);
        menu.add(dishManage);
        menu.addSeparator();
        menu.add(orderManage);
        menu.add(dishOrderManage);
        menu.add(settleAccount);
        menu.add(consumptionRecord); // 加入菜单
        menuBar.add(menu);

        setJMenuBar(menuBar);

        // 主面板美化
        mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 背景渐变
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(255, 253, 208);
                Color color2 = new Color(255, 237, 160);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        JLabel banner = new JLabel("<html><h1 style='text-align:center;color:#e57373;'>欢迎使用餐饮管理系统！</h1></html>", SwingConstants.CENTER);
        banner.setFont(new Font("华文行楷", Font.BOLD, 36));
        mainPanel.add(banner, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // ActionListeners
        empManage.addActionListener(e -> switchPanel(new EmployeeManagePane()));
        cusManage.addActionListener(e -> switchPanel(new CustomerManagePane()));
        deskManage.addActionListener(e -> switchPanel(new DeskManagePane()));
        catManage.addActionListener(e -> switchPanel(new CategoryManagePane()));
        dishManage.addActionListener(e -> switchPanel(new DishManagePane()));
        orderManage.addActionListener(e -> switchPanel(new OrderesManagePane()));
        dishOrderManage.addActionListener(e -> switchPanel(new DishesOrderesManagePane()));
        settleAccount.addActionListener(e -> switchPanel(new SettleAccountPane()));
        consumptionRecord.addActionListener(e -> switchPanel(new ConsumptionRecordPane()));
    }

    // 全局字体设置
    public static void setUIFont(Font font) {
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, font);
        }
    }

    private void switchPanel(JPanel panel) {
        getContentPane().remove(mainPanel);
        mainPanel = panel;
        getContentPane().add(mainPanel);
        validate();
        repaint();
    }

    public static void main(String[] args) {
        try {
            // Windows风格
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
            // ignore
        }
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}