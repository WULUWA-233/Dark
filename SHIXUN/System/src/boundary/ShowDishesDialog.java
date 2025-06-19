package boundary;

import entity.OrderItem;
import dao.OrderItemDAOImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ShowDishesDialog extends JDialog {
    private JTable table;
    private DefaultTableModel model;
    private OrderItemDAOImpl orderItemDAO = new OrderItemDAOImpl();
    private int orderId;

    public ShowDishesDialog(JFrame owner, int orderId) {
        super(owner, "已点菜品清单", true);
        this.orderId = orderId;
        setSize(500, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[]{"菜品编号", "单价", "小计金额"}, 0);
        table = new JTable(model);
        loadTable();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton closeBtn = new JButton("关闭");
        closeBtn.addActionListener(e -> dispose());
        add(closeBtn, BorderLayout.SOUTH);
    }

    private void loadTable() {
        model.setRowCount(0);
        List<OrderItem> items = orderItemDAO.getAll();
        double total = 0;
        for (OrderItem oi : items) {
            if (oi.getOrderId() == orderId) {
                model.addRow(new Object[]{oi.getDishId(), oi.getDishPrice(), oi.getTotalAmount()});
                total += oi.getTotalAmount();
            }
        }
        model.addRow(new Object[]{"合计", "", total});
    }
}