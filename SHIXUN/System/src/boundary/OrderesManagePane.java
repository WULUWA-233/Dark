package boundary;

import entity.Desk;
import entity.Customer;
import entity.Employee;
import entity.Order;
import dao.DeskDAOImpl;
import dao.CustomerDAOImpl;
import dao.EmployeeDAOImpl;
import dao.OrderDAOImpl;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class OrderesManagePane extends JPanel {
    private JComboBox<String> deskBox;
    private JComboBox<String> customerBox;
    private JComboBox<String> employeeBox;
    private JButton openBtn, refreshBtn;
    private DeskDAOImpl deskDAO = new DeskDAOImpl();
    private CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    private EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();
    private OrderDAOImpl orderDAO = new OrderDAOImpl();

    public OrderesManagePane() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(255, 253, 208));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(18, 18, 18, 18);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("开台管理");
        title.setFont(new Font("微软雅黑", Font.BOLD, 28));
        title.setForeground(new Color(33, 150, 243));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(title, gbc);

        gbc.gridwidth = 1; gbc.gridy++;
        JLabel deskLabel = new JLabel("选择空闲餐台:");
        deskLabel.setFont(new Font("微软雅黑", Font.PLAIN, 17));
        formPanel.add(deskLabel, gbc);
        deskBox = new JComboBox<>();
        deskBox.setFont(new Font("微软雅黑", Font.PLAIN, 17));
        gbc.gridx = 1;
        formPanel.add(deskBox, gbc);

        gbc.gridx = 0; gbc.gridy++;
        JLabel cusLabel = new JLabel("选择顾客:");
        cusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 17));
        formPanel.add(cusLabel, gbc);
        customerBox = new JComboBox<>();
        customerBox.setFont(new Font("微软雅黑", Font.PLAIN, 17));
        gbc.gridx = 1;
        formPanel.add(customerBox, gbc);

        gbc.gridx = 0; gbc.gridy++;
        JLabel empLabel = new JLabel("选择服务员:");
        empLabel.setFont(new Font("微软雅黑", Font.PLAIN, 17));
        formPanel.add(empLabel, gbc);
        employeeBox = new JComboBox<>();
        employeeBox.setFont(new Font("微软雅黑", Font.PLAIN, 17));
        gbc.gridx = 1;
        formPanel.add(employeeBox, gbc);

        gbc.gridx = 0; gbc.gridy++;
        openBtn = createButton("开台", new Color(76,175,80));
        formPanel.add(openBtn, gbc);
        gbc.gridx = 1;
        refreshBtn = createButton("刷新列表", new Color(33,150,243));
        formPanel.add(refreshBtn, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        JLabel tip = new JLabel("点击“开台”后该桌将变为占用。");
        tip.setFont(new Font("微软雅黑", Font.ITALIC, 16));
        tip.setForeground(new Color(233,30,99));
        formPanel.add(tip, gbc);

        add(formPanel, BorderLayout.CENTER);

        refreshBtn.addActionListener(e -> loadComboBox());
        openBtn.addActionListener(e -> openOrder());
        loadComboBox();
    }
/*
    private void loadComboBox() {
        deskBox.removeAllItems();
        customerBox.removeAllItems();
        employeeBox.removeAllItems();
        for (Desk d : deskDAO.getAll()) {
            if ("空闲".equals(d.getStatus())) deskBox.addItem(d.getDeskId() + "");
        }
        for (Customer c : customerDAO.getAll()) {
            customerBox.addItem(c.getCustomerId() + ":" + c.getName());
        }
        for (Employee e : employeeDAO.getAll()) {
            employeeBox.addItem(e.getEmpId() + ":" + e.getName());
        }
    }
*/
    private void loadComboBox() {
        // 1. 加载只包含“未占用”餐台的下拉框
        deskBox.removeAllItems();
        List<Desk> freeDesks = deskDAO.getByStatus("未占用");
        for (Desk desk : freeDesks) {
            // 下拉项建议用“编号-描述”如“1-大厅A”
            deskBox.addItem(desk.getDeskId() + "-" + desk.getAreaId() + "-" +"座位" + desk.getCapacity());
        }

        // 2. 加载顾客下拉框
        customerBox.removeAllItems();
        List<Customer> customers = customerDAO.getAll();
        for (Customer c : customers) {
            customerBox.addItem(c.getCustomerId() + "-" + c.getName());
        }

        // 3. 加载服务员下拉框
        employeeBox.removeAllItems();
        List<Employee> employees = employeeDAO.getAll();
        for (Employee e : employees) {
            employeeBox.addItem(e.getEmpId() + "-" + e.getName());
        }
    }
/*
    private void openOrder() {
        if (deskBox.getSelectedItem() == null || customerBox.getSelectedItem() == null || employeeBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "请选择完整信息！");
            return;
        }
        int deskId = Integer.parseInt(deskBox.getSelectedItem().toString());
        int custId = Integer.parseInt(customerBox.getSelectedItem().toString().split(":")[0]);
        int empId = Integer.parseInt(employeeBox.getSelectedItem().toString().split(":")[0]);
        Date now = new Date();
        int orderId = (int) (System.currentTimeMillis() / 1000); // 简单生成
        orderDAO.add(new Order(orderId, deskId, custId, empId, now, null, 0));
        Desk desk = deskDAO.getById(deskId);
        desk.setStatus("占用");
        deskDAO.update(desk);
        JOptionPane.showMessageDialog(this, "开台成功，订单编号：" + orderId, "提示", JOptionPane.INFORMATION_MESSAGE);
        loadComboBox();
    }
*/
    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);

        // 关键属性全部设置
        btn.setBackground(bg);                          // 按钮背景色
        btn.setForeground(Color.BLACK);                 // 文字颜色
        btn.setFont(new Font("微软雅黑", Font.BOLD, 17)); // 字体
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(6,24,6,24));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // 关键：保证背景填充和不透明
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(true);

        // Nimbus风格下可加这一句增强兼容性
        btn.putClientProperty("JButton.buttonType", "roundRect");

        return btn;
    }

    private void openOrder() {
        try {
            // 1. 获取选中的餐台编号
            String deskStr = (String) deskBox.getSelectedItem();
            if (deskStr == null) {
                JOptionPane.showMessageDialog(this, "请选择空闲餐台！");
                return;
            }
            int deskId = Integer.parseInt(deskStr.split("-")[0]); // 假设下拉项为 "1-大厅A" 取编号

            // 2. 获取选中的顾客编号
            String customerStr = (String) customerBox.getSelectedItem();
            if (customerStr == null) {
                JOptionPane.showMessageDialog(this, "请选择顾客！");
                return;
            }
            int customerId = Integer.parseInt(customerStr.split("-")[0]);

            // 3. 获取选中的服务员编号
            String employeeStr = (String) employeeBox.getSelectedItem();
            if (employeeStr == null) {
                JOptionPane.showMessageDialog(this, "请选择服务员！");
                return;
            }
            int empId = Integer.parseInt(employeeStr.split("-")[0]);

            // 4. 创建订单对象（订单编号可用时间戳或自增，此例用当前时间毫秒数）
            int orderId = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
            Date startTime = new Date();
            Order order = new Order(orderId, deskId, customerId, empId, startTime, null, 0);

            // 5. 新增订单
            orderDAO.add(order);

            // 6. 更新餐台状态为“占用”
            deskDAO.updateStatus(deskId, "占用");

            JOptionPane.showMessageDialog(this, "开台成功！订单号：" + orderId);

            // 7. 刷新下拉框
            loadComboBox();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "开台失败，请检查选择项或重试！");
        }

    }

}