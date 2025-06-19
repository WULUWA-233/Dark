package boundary;

import entity.Category;
import dao.CategoryDAOImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CategoryManagePane extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private CategoryDAOImpl categoryDAO = new CategoryDAOImpl();

    public CategoryManagePane() {
        setLayout(new BorderLayout(18,18));
        setBackground(new Color(255, 253, 208));

        JLabel title = new JLabel("菜品分类管理", SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 28));
        title.setForeground(new Color(255, 152, 0));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"分类编号", "分类名称", "描述"}, 0);
        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 18));
        table.setSelectionBackground(new Color(255, 236, 179));
        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        ((DefaultTableCellRenderer)table.getDefaultRenderer(Object.class)).setHorizontalAlignment(JLabel.CENTER);

        loadTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 152, 0),2));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 32, 12));
        buttonPanel.setOpaque(false);
        JButton addBtn = createButton("添加", new Color(76,175,80));
        JButton delBtn = createButton("删除", new Color(244,67,54));
        JButton refreshBtn = createButton("刷新", new Color(255, 152, 0));
        JButton editBtn = createButton("修改", new Color(33, 150, 243));

        buttonPanel.add(addBtn); buttonPanel.add(delBtn); buttonPanel.add(refreshBtn);buttonPanel.add(editBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addCategory());
        delBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "确定要删除该分类吗？", "确认删除", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    categoryDAO.delete(id);
                    loadTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "请选择要删除的行！");
            }
        });
        refreshBtn.addActionListener(e -> loadTable());
        // 按钮监听（放在delBtn、addBtn后面）
        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                // 获取原有数据
                int id = (int) model.getValueAt(row, 0);
                String oldName = (String) model.getValueAt(row, 1);
                String oldDesc = (String) model.getValueAt(row, 2);

                JTextField nameField = new JTextField(oldName);
                JTextField descField = new JTextField(oldDesc);
                Object[] message = {
                        "分类编号（不可改）: " + id,
                        "分类名称:", nameField,
                        "描述:", descField
                };
                nameField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
                descField.setFont(new Font("微软雅黑", Font.PLAIN, 16));

                int option = JOptionPane.showConfirmDialog(this, message, "修改分类", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String newName = nameField.getText();
                    String newDesc = descField.getText();
                    categoryDAO.update(new Category(id, newName, newDesc));
                    loadTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "请选择要修改的行！");
            }
        });
    }

    private void addCategory() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField descField = new JTextField();
        Object[] message = {
                "分类编号:", idField,
                "分类名称:", nameField,
                "描述:", descField
        };
        for (Object obj : message) {
            if (obj instanceof JTextField) {
                ((JTextField)obj).setFont(new Font("微软雅黑", Font.PLAIN, 16));
            }
        }
        int option = JOptionPane.showConfirmDialog(this, message, "添加菜品分类", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String desc = descField.getText();
                categoryDAO.add(new Category(id, name, desc));
                loadTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "输入有误！");
            }
        }
    }

    private void loadTable() {
        model.setRowCount(0);
        List<Category> list = categoryDAO.getAll();
        for (Category c : list) {
            // 这里请确保getCategoryName()和getDescription()与实体类一致
            model.addRow(new Object[]{c.getCategoryId(), c.getCategoryName(), c.getDescription()});
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