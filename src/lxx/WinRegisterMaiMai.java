package lxx;

import zhw.view.LoginTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author: 刘晓霞
 * function: 普通买卖方注册界面
 * time: 2021.01.09
 */

public class WinRegisterMaiMai extends JFrame implements ActionListener {

    // 手机号和身份证号的正则表达式
    public static final String REGEX_MOBILE = "^(1[3-9]\\d{9}$)";
    public static final String REGEX_CARD = "[1-9]{2}[0-9]{4}(19|20)[0-9]{2}" + "((0[1-9]{1})|(1[1-2]{1}))((0[1-9]{1})|([1-2]{1}[0-9]{1}|(3[0-1]{1})))" + "[0-9]{3}[0-9x]{1}";
    public static final String REGEX_MAIL = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    JLabel label1, label2, label3, label4, label5, label6, label7;
    JTextField textField1, textField2, textField3, textField4;
    JPanel panel1, panel2, panel3, panel4, panel5, panel6, panel7;
    JRadioButton radioButtonM, radioButtonF;
    JButton button1, button2;
    Connection connection;
    PreparedStatement preparedStatement;
    Long ID;

    public WinRegisterMaiMai(Long id) throws SQLException {
        ID = id;
//        this.setLayout(new GridLayout(6, 1));
        this.setLayout(new GridLayout(7, 1));
        label1 = new JLabel("客户ID:");
        label1.setFont(new Font("宋体", Font.PLAIN, 20));
        label2 = new JLabel(id.toString());
//        label2 = new JLabel(String.valueOf(id));
        label2.setFont(new Font("宋体", Font.PLAIN, 20));
        panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel1.add(label1);
        panel1.add(label2);
        this.add(panel1);

        label3 = new JLabel("  姓名: ");
        label3.setFont(new Font("宋体", Font.PLAIN, 20));
        textField1 = new JTextField(20);
        textField1.setFont(new Font("宋体", Font.PLAIN, 20));
        panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel2.add(label3);
        panel2.add(textField1);
        this.add(panel2);

        label4 = new JLabel("  性别: ");
        label4.setFont(new Font("宋体", Font.PLAIN, 20));
        radioButtonM = new JRadioButton("男");
        radioButtonM.setFont(new Font("宋体", Font.PLAIN, 20));
        radioButtonF = new JRadioButton("女");
        radioButtonF.setFont(new Font("宋体", Font.PLAIN, 20));
        panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel3.add(label4);
        panel3.add(radioButtonF);
        panel3.add(radioButtonM);
        this.add(panel3);

        label5 = new JLabel("  电话: ");
        label5.setFont(new Font("宋体", Font.PLAIN, 20));
        textField2 = new JTextField(20);
        textField2.setFont(new Font("宋体", Font.PLAIN, 20));
        panel4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel4.add(label5);
        panel4.add(textField2);
        this.add(panel4);

        label6 = new JLabel("身份证号:");
        label6.setFont(new Font("宋体", Font.PLAIN, 20));
        textField3 = new JTextField(30);
        textField3.setFont(new Font("宋体", Font.PLAIN, 20));
        panel5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel5.add(label6);
        panel5.add(textField3);
        this.add(panel5);

        label7 = new JLabel("  邮箱: ");
        label7.setFont(new Font("宋体", Font.PLAIN, 20));
        textField4 = new JTextField(30);
        textField4.setFont(new Font("宋体", Font.PLAIN, 20));
        panel6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel6.add(label7);
        panel6.add(textField4);
        this.add(panel6);

        button1 = new JButton("注册");
        button1.setFont(new Font("宋体", Font.PLAIN, 20));
        button2 = new JButton("取消");
        button2.setFont(new Font("宋体", Font.PLAIN, 20));
        panel7 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel7.add(button1);
        panel7.add(button2);
        this.add(panel7);

        awtEvent(); //创建监听
    }

    private void awtEvent() {
        // 监听单选框（互斥），选择性别
        radioButtonM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(radioButtonM.isSelected()){
                    radioButtonF.setSelected(false);
                }
            }
        });
        radioButtonF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(radioButtonF.isSelected()){
                    radioButtonM.setSelected(false);
                }
            }
        });

        // 注册按钮监听
        button1.addActionListener((ActionListener) this);
        // 取消按钮监听
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                WinRegister winRegister = new WinRegister();
                winRegister.setTitle("用户注册");
                winRegister.setBounds(450, 200, 360, 270);
                winRegister.setVisible(true);
                winRegister.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //退出程序
            }
        });
    }

    boolean ok = true;
    String sex = null;
    public void actionPerformed(ActionEvent e) {
        // 获取性别
        try {
            judgeName();
            judgeSex();
            judgePhone();
            judgeCard();
            String mail = textField4.getText();
            if (!mail.isEmpty()) {
                judgeMail();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (e.getSource() == button1 && ok) {
            try {
                this.dispose();
                connection = Link.getConnection();
                String str = "insert into Customer(客户ID,姓名,性别,电话,身份证号,邮箱) values(?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(str, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, ID.toString());
                preparedStatement.setString(2, textField1.getText());
                preparedStatement.setString(3, sex);
                preparedStatement.setString(4, textField2.getText());
                preparedStatement.setString(5, textField3.getText());
                preparedStatement.setString(6, textField4.getText());
                preparedStatement.executeQuery();
                JOptionPane.showMessageDialog(null, "注册成功！");
                LoginTest loginTest = new LoginTest();
                loginTest.setVisible(true);
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                try {
                    Link.result(connection, preparedStatement);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    private void judgeName() throws SQLException {
        String str = textField1.getText();
        if (str.isEmpty()) {
            ok = false;
            this.dispose();
            JOptionPane.showMessageDialog(this, "名字不能为空，请重新填写！", "警告信息", JOptionPane.WARNING_MESSAGE);
            newWin();
        }
    }

    private void judgeSex() throws SQLException {
        if (radioButtonF.isSelected()) {
            sex = "女";
        }
        else if (radioButtonM.isSelected()){
            sex = "男";
        } else {
            ok = false;
            this.dispose();
            JOptionPane.showMessageDialog(this, "性别不能为空，请重新选择！", "警告信息", JOptionPane.WARNING_MESSAGE);
            newWin();
        }
    }

    private void judgePhone() throws SQLException {
        String str = textField2.getText();
        Pattern p = Pattern.compile(REGEX_MOBILE);
        Matcher m = p.matcher(str);
        if (m.matches() == false) {
            ok = false;
            this.dispose();
            JOptionPane.showMessageDialog(this, "非正常手机号，请重新填写！", "警告信息", JOptionPane.WARNING_MESSAGE);
            newWin();
        }
    }
    private void judgeCard() throws SQLException {
        String str = textField3.getText();
        if (str.matches(REGEX_CARD) == false) {
            ok = false;
            this.dispose();
            JOptionPane.showMessageDialog(this, "非正常身份证号，请重新填写！", "警告信息", JOptionPane.WARNING_MESSAGE);
            newWin();
        }
    }

    private void judgeMail() throws SQLException {
        String mail = textField4.getText();
        Pattern regex = Pattern.compile(REGEX_MAIL);
        Matcher matcher = regex.matcher(mail);
        if (matcher.matches() == false) {
            ok = false;
            this.dispose();
            JOptionPane.showMessageDialog(this, "非正常邮箱，请重新填写！", "警告信息", JOptionPane.WARNING_MESSAGE);
            newWin();
        }
    }

    private void newWin() throws SQLException {
        WinRegisterMaiMai winRegisterMaiMai = new WinRegisterMaiMai(ID);
        winRegisterMaiMai.setTitle("注册普通买卖方");
        winRegisterMaiMai.setBounds(400, 200, 450, 350);
        winRegisterMaiMai.setVisible(true);
        winRegisterMaiMai.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //退出程序
    }

//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    WinRegisterMaiMai winRegisterMaiMai = new WinRegisterMaiMai(ID);
//                    winRegisterMaiMai.setTitle("注册普通买卖方");
//                    winRegisterMaiMai.setBounds(400, 200, 450, 350);
//                    winRegisterMaiMai.setVisible(true);
//                    winRegisterMaiMai.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //退出程序
//                } catch (Exception e) {
//                    e. printStackTrace();
//                }
//            }
//        });
//    }
}
