/*
 * Created by JFormDesigner on Fri Jan 08 16:16:24 CST 2021
 */

package view;

import dao.UserDao;
import entity.User;
import util.DbUtil;
import util.StringUtil;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import javax.swing.*;
import javax.swing.border.*;
/**
 * @auther 张鸿伟
 * @date 2021/1/6 10:51:09
 * @description
 */
/**
 * 项目名 BookManager
 * <br>包名 view
 * <br>描述 登录界面
 */
public class LoginTest extends JFrame {

    public LoginTest() {
        initComponents();

    }
    private DbUtil dbUtil = new DbUtil();
    private UserDao userDao = new UserDao();

    private void comboBox1ItemStateChanged(ItemEvent e) {
        // TODO add your code here
    }

    private void signUpButtonActionPerformed(ActionEvent e) {
        // TODO add your code here
        this.setVisible(false);
        new SignUp().setVisible(true);
    }

    private void okButtonActionPerformed(ActionEvent e) {
        // TODO add your code here
        String userName = this.textField1.getText();
        String password = this.passwordField1.getText();
        if (StringUtil.isEmpty(userName)) {
            JOptionPane.showMessageDialog(null, "用户名不能为空");
        }
        else if (StringUtil.isEmpty(password)) {
            JOptionPane.showMessageDialog(null, "密码不能为空");
        }
        else{
            User user = new User(userName, password);
            Connection conn = null;
            try {
                conn = dbUtil.getConnection();
                User currentUser = userDao.login(conn, user);
                if (currentUser != null) {
                    int key = comboBox1.getSelectedIndex();
                    switch (key){
                        case 0: new MainFrame().setVisible(true);
                            break;
                        case 1: new SalesmanMainFrame().setVisible(true);
                            break;
                        case 2: new UserMainFrame().setVisible(true);
                            break;
                    }
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "用户名或密码错误");
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                try {
                    dbUtil.closeConnection(conn);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private void comboBox1ActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void cancelButtonActionPerformed(ActionEvent e) {
        // TODO add your code here
        this.textField1.setText("");
        this.passwordField1.setText("");
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        textField1 = new JTextField();
        comboBox1 = new JComboBox<>();
        label2 = new JLabel();
        passwordField1 = new JPasswordField();
        okButton = new JButton();
        signUpButton = new JButton();
        cancelButton = new JButton();
        buttonBar = new JPanel();

        //======== this ========
        setTitle("\u767b\u5f55\u754c\u9762");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setMaximumSize(new Dimension(504, 383));
                contentPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- label1 ----
                label1.setText("\u8d26\u53f7");
                label1.setHorizontalAlignment(SwingConstants.CENTER);
                label1.setIcon(new ImageIcon("D:\\zhw\\\u5927\u4e09\u65b0\u5b66\u671f\\\u8bfe\u8bbe\\zhw\\src\\images\\user1.png"));
                contentPanel.add(label1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textField1 ----
                textField1.setColumns(16);
                contentPanel.add(textField1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- comboBox1 ----
                comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
                    "\u7ba1\u7406\u5458",
                    "\u4e1a\u52a1\u5458",
                    "\u7528\u6237"
                }));
                comboBox1.addActionListener(e -> comboBox1ActionPerformed(e));
                contentPanel.add(comboBox1, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- label2 ----
                label2.setText("\u5bc6\u7801");
                label2.setIcon(new ImageIcon("D:\\zhw\\\u5927\u4e09\u65b0\u5b66\u671f\\\u8bfe\u8bbe\\zhw\\src\\images\\password1.png"));
                label2.setHorizontalAlignment(SwingConstants.CENTER);
                contentPanel.add(label2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                contentPanel.add(passwordField1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- okButton ----
                okButton.setText("\u767b\u5f55");
                okButton.setMaximumSize(new Dimension(126, 52));
                okButton.setMinimumSize(new Dimension(126, 52));
                okButton.addActionListener(e -> okButtonActionPerformed(e));
                contentPanel.add(okButton, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- signUpButton ----
                signUpButton.setText("\u6ce8\u518c");
                signUpButton.setMaximumSize(new Dimension(126, 52));
                signUpButton.setMinimumSize(new Dimension(126, 52));
                signUpButton.addActionListener(e -> signUpButtonActionPerformed(e));
                contentPanel.add(signUpButton, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("\u91cd\u7f6e");
                cancelButton.setMaximumSize(new Dimension(126, 52));
                cancelButton.setMinimumSize(new Dimension(126, 52));
                cancelButton.addActionListener(e -> cancelButtonActionPerformed(e));
                contentPanel.add(cancelButton, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JTextField textField1;
    private JComboBox<String> comboBox1;
    private JLabel label2;
    private JPasswordField passwordField1;
    private JButton okButton;
    private JButton signUpButton;
    private JButton cancelButton;
    private JPanel buttonBar;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    public static void main(String[] args){
        new LoginTest().setVisible(true);

    }
}
