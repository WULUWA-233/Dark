package boundary;

import javax.swing.*;
import java.awt.*;

public class GiveChangeDialog extends JDialog {
    public GiveChangeDialog(JFrame owner, double total, double paid) {
        super(owner, "找零", true);
        setSize(300, 200);
        setLocationRelativeTo(owner);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("应收:"));
        add(new JLabel(String.format("%.2f", total)));
        add(new JLabel("实收:"));
        add(new JLabel(String.format("%.2f", paid)));
        add(new JLabel("找零:"));
        add(new JLabel(String.format("%.2f", paid - total)));

        JButton closeBtn = new JButton("关闭");
        closeBtn.addActionListener(e -> dispose());
        add(closeBtn);
    }
}