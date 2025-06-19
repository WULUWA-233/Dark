package boundary;

import entity.Customer;
import dao.CustomerDAOImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerManagePane extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private CustomerDAOImpl customerDAO = new CustomerDAOImpl();

    public CustomerManagePane() {
        setLayout(new BorderLayout(18,18));
        setBackground(new Color(255, 253, 208));

        JLabel title = new JLabel("客户管理", SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 28));
        title.setForeground(new Color(76,175,80));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"编号", "姓名", "电话", "性别"}, 0);
        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 18));
        table.setSelectionBackground(new Color(255, 236, 179));
        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        ((DefaultTableCellRenderer)table.getDefaultRenderer(Object.class)).setHorizontalAlignment(JLabel.CENTER);

        loadTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(233, 30, 99),2));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 32, 12));
        buttonPanel.setOpaque(false);
        JButton addBtn = createButton("添加", new Color(76,175,80));
        JButton delBtn = createButton("删除", new Color(244,67,54));
        JButton refreshBtn = createButton("刷新", new Color(33,150,243));
        JButton editBtn = createButton("修改", new Color(33, 150, 243));
        buttonPanel.add(addBtn); buttonPanel.add(delBtn); buttonPanel.add(refreshBtn);buttonPanel.add(editBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addCustomer());
        delBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "确定要删除该客户吗？", "确认删除", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    customerDAO.delete(id);
                    loadTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "请选择要删除的行！");
            }
        });
        refreshBtn.addActionListener(e -> loadTable());
        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                String oldName = (String) model.getValueAt(row, 1);
                String oldPhone = (String) model.getValueAt(row, 2);
                String oldGender = (String) model.getValueAt(row, 3);

                JTextField nameField = new JTextField(oldName);
                JTextField phoneField = new JTextField(oldPhone);
                JTextField genderField = new JTextField(oldGender);
                Object[] message = {
                        "编号（不可改）: " + id,
                        "姓名:", nameField,
                        "电话:", phoneField,
                        "性别:", genderField
                };
                nameField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
                phoneField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
                genderField.setFont(new Font("微软雅黑", Font.PLAIN, 16));

                int option = JOptionPane.showConfirmDialog(this, message, "修改客户信息", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        String newName = nameField.getText();
                        String newPhone = phoneField.getText();
                        String newGender = genderField.getText();
                        customerDAO.update(new Customer(id, newName, newPhone, newGender));
                        loadTable();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "输入有误！");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "请选择要修改的行！");
            }
        });
    }

    private void addCustomer() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField genderField = new JTextField();
        Object[] message = {
                "编号:", idField,
                "姓名:", nameField,
                "电话:", phoneField,
                "性别:", genderField
        };
        for (Object obj : message) {
            if (obj instanceof JTextField) {
                ((JTextField)obj).setFont(new Font("微软雅黑", Font.PLAIN, 16));
            }
        }
        int option = JOptionPane.showConfirmDialog(this, message, "添加客户", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String phone = phoneField.getText();
                String gender = genderField.getText();
                customerDAO.add(new Customer(id, name, phone, gender));
                loadTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "输入有误！");
            }
        }
    }

    private void loadTable() {
        model.setRowCount(0);
        List<Customer> list = customerDAO.getAll();
        for (Customer c : list) {
            model.addRow(new Object[]{c.getCustomerId(), c.getName(), c.getPhone(), c.getGender()});
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