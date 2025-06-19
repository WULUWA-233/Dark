package boundary;

import entity.Dish;
import entity.Order;
import entity.OrderItem;
import dao.DishDAOImpl;
import dao.OrderDAOImpl;
import dao.OrderItemDAOImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DishesOrderesManagePane extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private DishDAOImpl dishDAO = new DishDAOImpl();
    private OrderDAOImpl orderDAO = new OrderDAOImpl();
    private OrderItemDAOImpl orderItemDAO = new OrderItemDAOImpl();
//    private JTextField orderIdField;
    private JComboBox<Integer> orderIdBox;

    public DishesOrderesManagePane() {
        setLayout(new BorderLayout(20,20));
        setBackground(new Color(255, 253, 208));

        model = new DefaultTableModel(new Object[]{"菜品编号", "菜品名称", "单价", "点菜数量"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 18));
        table.setSelectionBackground(new Color(255, 236, 179));
        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        ((DefaultTableCellRenderer)table.getDefaultRenderer(Object.class)).setHorizontalAlignment(JLabel.CENTER);

        loadTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 193, 7),2));
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 24, 18));
        panel.setOpaque(false);
        panel.add(new JLabel("订单编号:"));
//        orderIdField = new JTextField(10);
//        orderIdField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
//        panel.add(orderIdField);
        orderIdBox = new JComboBox<>();
        orderIdBox.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        panel.add(orderIdBox);

        loadOrderIds();

        JButton refreshOrderBtn = createButton("刷新订单", new Color(33,150,243));
        panel.add(refreshOrderBtn);
        refreshOrderBtn.addActionListener(e -> loadOrderIds());

        JButton orderBtn = createButton("添加到订单", new Color(76,175,80));
        JButton refreshBtn = createButton("刷新菜品", new Color(33,150,243));
        panel.add(orderBtn);
        panel.add(refreshBtn);
        add(panel, BorderLayout.SOUTH);

        orderBtn.addActionListener(e -> addToOrder());
        refreshBtn.addActionListener(e -> loadTable());
    }

    private void loadTable() {
        model.setRowCount(0);
        List<Dish> list = dishDAO.getAll();
        for (Dish d : list) {
            model.addRow(new Object[]{d.getDishId(), d.getDishName(), d.getPrice(), 0});
        }
    }

    private void loadOrderIds() {
        orderIdBox.removeAllItems();
        List<Order> orders = orderDAO.getAll();
        for (Order order : orders) {
            // 仅展示endTime为空（未结账）的订单
            if (order.getEndTime() == null) {
                orderIdBox.addItem(order.getOrderId());
            }
        }
    }



    private void addToOrder() {
        try {
//            int orderId = Integer.parseInt(orderIdField.getText());
            int orderId = (Integer) orderIdBox.getSelectedItem();
            Order order = orderDAO.getById(orderId);
            if (order == null) {
                JOptionPane.showMessageDialog(this, "订单编号不存在，请输入有效订单编号！");
                return;
            }
            double total = 0;
            boolean hasDish = false;
            for (int i = 0; i < model.getRowCount(); i++) {
                int qty = 0;
                Object qtyObj = model.getValueAt(i, 3);
                if (qtyObj != null && !"".equals(qtyObj.toString().trim()))
                    qty = Integer.parseInt(qtyObj.toString());
                if (qty > 0) {
                    hasDish = true;
                    int dishId = (int) model.getValueAt(i, 0);
                    double price = (double) model.getValueAt(i, 2);
                    double itemTotal = price * qty;
                    orderItemDAO.add(new OrderItem(orderId * 1000 + dishId, orderId, dishId, price, itemTotal));
                    total += itemTotal;
                }
            }
            if (!hasDish) {
                JOptionPane.showMessageDialog(this, "请填写菜品数量！");
                return;
            }
            order.setTotalAmount(order.getTotalAmount() + total);
            orderDAO.update(order);
            JOptionPane.showMessageDialog(this, "点菜成功！");
            loadTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "请检查订单编号和点菜数量！");
        }
    }

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
}