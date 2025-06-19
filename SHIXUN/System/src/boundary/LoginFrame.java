package boundary;

import entity.User;
import dao.UserDAOImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("登录界面");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel userLabel = new JLabel("用户名:");
        JLabel passLabel = new JLabel("密码:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginBtn = new JButton("登录");
        JButton exitBtn = new JButton("退出");

        add(userLabel);
        add(usernameField);
        add(passLabel);
        add(passwordField);
        add(loginBtn);
        add(exitBtn);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                UserDAOImpl userDAO = new UserDAOImpl();
                boolean success = false;
                for (User user : userDAO.getAll()) {
                    if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                        success = true;
                        break;
                    }
                }
                if (success) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "登录成功！");
                    new MainFrame().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "用户名或密码错误！");
                }
            }
        });

        exitBtn.addActionListener(e -> System.exit(0));
    }
}