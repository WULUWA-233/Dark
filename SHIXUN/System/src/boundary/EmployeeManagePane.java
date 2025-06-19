package boundary;

import entity.Employee;
import dao.EmployeeDAOImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeManagePane extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();

    public EmployeeManagePane() {
        setLayout(new BorderLayout(18,18));
        setBackground(new Color(255, 253, 208));

        JLabel title = new JLabel("员工管理", SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 28));
        title.setForeground(new Color(33, 150, 243));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"编号", "姓名", "性别", "职位", "住址", "工资"}, 0);
        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 18));
        table.setSelectionBackground(new Color(255, 236, 179));
        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        ((DefaultTableCellRenderer)table.getDefaultRenderer(Object.class)).setHorizontalAlignment(JLabel.CENTER);

        loadTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(33, 150, 243),2));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 32, 12));
        buttonPanel.setOpaque(false);
        JButton addBtn = createButton("添加", new Color(76,175,80));
        JButton delBtn = createButton("删除", new Color(244,67,54));
        JButton refreshBtn = createButton("刷新", new Color(33,150,243));
        JButton editBtn = createButton("修改", new Color(33, 150, 243));
        buttonPanel.add(addBtn); buttonPanel.add(delBtn); buttonPanel.add(refreshBtn);buttonPanel.add(editBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addEmployee());
        delBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "确定要删除该员工吗？", "确认删除", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    employeeDAO.delete(id);
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
                String oldGender = (String) model.getValueAt(row, 2);
                String oldPos = (String) model.getValueAt(row, 3);
                String oldAddr = (String) model.getValueAt(row, 4);
                double oldSalary = Double.parseDouble(model.getValueAt(row, 5).toString());

                JTextField nameField = new JTextField(oldName);
                JTextField genderField = new JTextField(oldGender);
                JTextField posField = new JTextField(oldPos);
                JTextField addrField = new JTextField(oldAddr);
                JTextField salaryField = new JTextField(String.valueOf(oldSalary));
                Object[] message = {
                        "编号（不可改）: " + id,
                        "姓名:", nameField,
                        "性别:", genderField,
                        "职位:", posField,
                        "地址:", addrField,
                        "工资:", salaryField
                };
                nameField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
                genderField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
                posField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
                addrField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
                salaryField.setFont(new Font("微软雅黑", Font.PLAIN, 16));

                int option = JOptionPane.showConfirmDialog(this, message, "修改员工信息", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        String newName = nameField.getText();
                        String newGender = genderField.getText();
                        String newPos = posField.getText();
                        String newAddr = addrField.getText();
                        double newSalary = Double.parseDouble(salaryField.getText());
                        employeeDAO.update(new Employee(id, newName, newGender, newPos, newAddr, newSalary));
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

    private void addEmployee() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField genderField = new JTextField();
        JTextField posField = new JTextField();
        JTextField addrField = new JTextField();
        JTextField salaryField = new JTextField();
        Object[] message = {
                "编号:", idField,
                "姓名:", nameField,
                "性别:", genderField,
                "职位:", posField,
                "住址:", addrField,
                "工资:", salaryField
        };
        for (Object obj : message) {
            if (obj instanceof JTextField) {
                ((JTextField)obj).setFont(new Font("微软雅黑", Font.PLAIN, 16));
            }
        }
        int option = JOptionPane.showConfirmDialog(this, message, "添加员工", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String gender = genderField.getText();
                String pos = posField.getText();
                String addr = addrField.getText();
                double salary = Double.parseDouble(salaryField.getText());
                employeeDAO.add(new Employee(id, name, gender, pos, addr, salary));
                loadTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "输入有误！");
            }
        }
    }

    private void loadTable() {
        model.setRowCount(0);
        List<Employee> list = employeeDAO.getAll();
        for (Employee e : list) {
            model.addRow(new Object[]{e.getEmpId(), e.getName(), e.getGender(), e.getPosition(), e.getAddress(), e.getSalary()});
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