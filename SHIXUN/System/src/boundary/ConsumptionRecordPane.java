package boundary;

import entity.ConsumptionRecord;
import dao.ConsumptionRecordDAOImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class ConsumptionRecordPane extends JPanel {
    private DefaultTableModel model;
    private ConsumptionRecordDAOImpl recordDAO = new ConsumptionRecordDAOImpl();

    public ConsumptionRecordPane() {
        setLayout(new BorderLayout(10,10));
        setBackground(new Color(240, 255, 255));

        JLabel title = new JLabel("消费记录");
        title.setFont(new Font("微软雅黑", Font.BOLD, 24));
        title.setForeground(new Color(33, 150, 243));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        topPanel.setOpaque(false);
        topPanel.add(title);

        JButton refreshBtn = new JButton("刷新记录");
        refreshBtn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        refreshBtn.setBackground(new Color(33, 150, 243));
        refreshBtn.setForeground(Color.WHITE);
        topPanel.add(refreshBtn);

        model = new DefaultTableModel(new Object[]{"顾客编号", "顾客姓名", "订单编号", "消费金额", "消费时间"}, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        table.setRowHeight(26);
        JScrollPane scrollPane = new JScrollPane(table);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        refreshBtn.addActionListener(e -> loadRecords());
        loadRecords();
    }

    private void loadRecords() {
        model.setRowCount(0);
        List<ConsumptionRecord> list = recordDAO.getAll();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (ConsumptionRecord record : list) {
            model.addRow(new Object[]{
                    record.getCustomerId(),
                    record.getCustomerName(),
                    record.getOrderId(),
                    record.getAmount(),
                    sdf.format(record.getTime())
            });
        }
    }
}