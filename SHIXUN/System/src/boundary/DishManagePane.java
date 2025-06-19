package boundary;

import entity.Dish;
import dao.DishDAOImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DishManagePane extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private DishDAOImpl dishDAO = new DishDAOImpl();

    public DishManagePane() {
        setLayout(new BorderLayout(20,20));
        setBackground(new Color(255, 253, 208));

        JLabel title = new JLabel("菜品管理", SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 28));
        title.setForeground(new Color(255, 87, 34));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"菜品编号", "菜品名称", "单价", "菜系编号"}, 0);
        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 18));
        table.setSelectionBackground(new Color(255, 236, 179));
        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        ((DefaultTableCellRenderer)table.getDefaultRenderer(Object.class)).setHorizontalAlignment(JLabel.CENTER);

        loadTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 87, 34),2));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        buttonPanel.setOpaque(false);

        JButton addBtn = createButton("添加", new Color(76,175,80));
        JButton delBtn = createButton("删除", new Color(244,67,54));
        JButton saveBtn = createButton("刷新", new Color(255,87,34));
        JButton editBtn = createButton("修改", new Color(33, 150, 243));
        buttonPanel.add(addBtn); buttonPanel.add(delBtn); buttonPanel.add(saveBtn);buttonPanel.add(editBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            JTextField idField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField priceField = new JTextField();
            JTextField catField = new JTextField();
            Object[] message = {
                    "菜品编号:", idField,
                    "菜品名称:", nameField,
                    "单价:", priceField,
                    "菜系编号:", catField
            };
            for (Object obj : message) {
                if (obj instanceof JTextField) {
                    ((JTextField)obj).setFont(new Font("微软雅黑", Font.PLAIN, 16));
                }
            }
            int option = JOptionPane.showConfirmDialog(this, message, "添加菜品", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int catId = Integer.parseInt(catField.getText());
                    dishDAO.add(new Dish(id, name, price, catId));
                    loadTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "输入有误！");
                }
            }
        });

        delBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "确定要删除该菜品吗？", "确认删除", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dishDAO.delete(id);
                    loadTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "请选择要删除的行！");
            }
        });

        saveBtn.addActionListener(e -> loadTable());

        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                String oldName = (String) model.getValueAt(row, 1);
                double oldPrice = Double.parseDouble(model.getValueAt(row, 2).toString());
                int oldCatId = (int) model.getValueAt(row, 3);

                JTextField nameField = new JTextField(oldName);
                JTextField priceField = new JTextField(String.valueOf(oldPrice));
                JTextField catField = new JTextField(String.valueOf(oldCatId));
                Object[] message = {
                        "菜品编号（不可改）: " + id,
                        "菜品名称:", nameField,
                        "单价:", priceField,
                        "菜系编号:", catField
                };
                nameField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
                priceField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
                catField.setFont(new Font("微软雅黑", Font.PLAIN, 16));

                int option = JOptionPane.showConfirmDialog(this, message, "修改菜品", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        String newName = nameField.getText();
                        double newPrice = Double.parseDouble(priceField.getText());
                        int newCatId = Integer.parseInt(catField.getText());
                        dishDAO.update(new Dish(id, newName, newPrice, newCatId));
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

    private void loadTable() {
        model.setRowCount(0);
        List<Dish> list = dishDAO.getAll();
        for (Dish d : list) {
            model.addRow(new Object[]{d.getDishId(), d.getDishName(), d.getPrice(), d.getCategoryId()});
        }
    }

    // 通用美化按钮
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