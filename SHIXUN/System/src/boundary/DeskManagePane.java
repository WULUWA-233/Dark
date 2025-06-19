package boundary;

import entity.Desk;
import dao.DeskDAOImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DeskManagePane extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private DeskDAOImpl deskDAO = new DeskDAOImpl();

    public DeskManagePane() {
        setLayout(new BorderLayout(18,18));
        setBackground(new Color(255, 253, 208));

        JLabel title = new JLabel("餐台管理", SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 28));
        title.setForeground(new Color(233, 30, 99));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"餐台编号", "区域编号", "容量", "状态"}, 0);
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

        addBtn.addActionListener(e -> addDesk());
        delBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "确定要删除该餐台吗？", "确认删除", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    deskDAO.delete(id);
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
                int oldAreaId = (int) model.getValueAt(row, 1);
                int oldCap = (int) model.getValueAt(row, 2);
                String oldStatus = (String) model.getValueAt(row, 3);

                JTextField areaField = new JTextField(String.valueOf(oldAreaId));
                JTextField capField = new JTextField(String.valueOf(oldCap));
                JComboBox<String> statusBox = new JComboBox<>(new String[]{"占用", "未占用"});
                statusBox.setSelectedItem(oldStatus);
                //JTextField statusField = new JTextField(oldStatus);
                Object[] message = {
                        "餐台编号（不可改）: " + id,
                        "区域编号:", areaField,
                        "容量:", capField,
                        "状态:", statusBox //statusField
                };
                areaField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
                capField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
                statusBox.setFont(new Font("微软雅黑", Font.PLAIN, 16));
                //statusField.setFont(new Font("微软雅黑", Font.PLAIN, 16));

                int option = JOptionPane.showConfirmDialog(this, message, "修改餐台信息", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        int newAreaId = Integer.parseInt(areaField.getText());
                        int newCap = Integer.parseInt(capField.getText());
                        String newStatus = (String) statusBox.getSelectedItem();
                        //String newStatus = statusField.getText();
                        deskDAO.update(new Desk(id, newAreaId, newCap, newStatus));
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

    private void addDesk() {
        JTextField idField = new JTextField();
        JTextField areaField = new JTextField();
        JTextField capField = new JTextField();
//        JTextField statusField = new JTextField();
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"占用", "未占用"});
        Object[] message = {
                "餐台编号:", idField,
                "区域编号:", areaField,
                "容量:", capField,
                "状态:", statusBox //statusField
        };
        for (Object obj : message) {
            if (obj instanceof JTextField) {
                ((JTextField)obj).setFont(new Font("微软雅黑", Font.PLAIN, 16));
            }
        }
        int option = JOptionPane.showConfirmDialog(this, message, "添加餐台", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                int areaId = Integer.parseInt(areaField.getText());
                int cap = Integer.parseInt(capField.getText());
                String status = (String) statusBox.getSelectedItem();//statusField.getText();
                deskDAO.add(new Desk(id, areaId, cap, status));
                loadTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "输入有误！");
            }
        }
    }

    private void loadTable() {
        model.setRowCount(0);
        List<Desk> list = deskDAO.getAll();
        for (Desk d : list) {
            model.addRow(new Object[]{d.getDeskId(), d.getAreaId(), d.getCapacity(), d.getStatus()});
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