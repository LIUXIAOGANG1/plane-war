package com.game.plane.war.page;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.game.plane.war.beans.User;
import com.game.plane.war.persistence.OperationDB;

public class LoginSystem implements ActionListener{
	JFrame frame;
	Container contentPane;
	JPanel jpl;
	JLabel jlb1, jlb2, jlb3, jlb4, jlb5;
	JButton login, register;
	public static JTextField user = new JTextField(10);
	public static JPasswordField password = new JPasswordField(10);

	/* 构造函数进行布局 */
	public LoginSystem() {
		frame = new JFrame();
		frame.setTitle("用户登录");
		frame.setUndecorated(true); // 去掉标题栏
		frame.setResizable(false);// 不可以设置大小
		frame.getRootPane().setWindowDecorationStyle(
				JRootPane.INFORMATION_DIALOG);// 采用指定的窗口装饰风格
		contentPane = frame.getContentPane();
		jpl = new JPanel();
		jlb1 = new JLabel();
		jlb1.setBorder(BorderFactory.createRaisedBevelBorder());// 设置边框样式
		jlb1.setFont(new Font("Serif", Font.PLAIN, 30));
		jlb2 = new JLabel("账   号：", SwingConstants.CENTER);
		jlb2.setFont(new Font("Serif", Font.PLAIN, 20));
		jlb3 = new JLabel("密   码：", SwingConstants.CENTER);
		jlb3.setFont(new Font("Serif", Font.PLAIN, 20));
		
		// 添加背景图片LoginSystem.class.getResource("/images/login.gif")
		ImageIcon i = new ImageIcon(LoginSystem.class.getResource("/images/login.jpg"));
		jlb4 = new JLabel(i);
		jlb4.setBounds(0, 0, 580, 350);
		frame.getLayeredPane().add(jlb4, new Integer(Integer.MIN_VALUE));// JFrame的第二次添加图片;
		/*
		 * jlb4是这个desktopPane的背景吧desktopPane.add(jlb4,new
		 * Integer(Integer.MIN_VALUE));表示jlb4永远是desktopPane这个容器的最后一个组件，
		 * 也就是说，backLabel永远在desktopPane的最底层
		 */
		JPanel panel = (JPanel) frame.getContentPane();// 把顶层容器转换为面板，因为容器没有setOpaque（）方法
		panel.setOpaque(false);// 设置顶层容器为透明
		jpl.setOpaque(false);// 设置顶层容器中添加的主面板为透明
		// 图片添加完毕

		jlb5 = new JLabel("技术支持 | 南京信息工程大学滨江学院 ", JLabel.RIGHT);
		jlb5.setBorder(BorderFactory.createRaisedBevelBorder());// 设置边框样式
		jlb5.setFont(new Font("Serif", Font.PLAIN, 20));
		login = new JButton("登录");
		register = new JButton("注册");
		user.setFont(new Font("Serif", Font.PLAIN, 18));
		password.setFont(new Font("Serif", Font.PLAIN, 18));
		Border bd1 = BorderFactory.createRaisedBevelBorder();
		Border bd2 = BorderFactory.createLoweredBevelBorder();
		Border bd3 = BorderFactory.createEtchedBorder();
		Border bd4 = BorderFactory.createLineBorder(Color.DARK_GRAY);
		jpl.add(jlb1);
		jlb1.setBounds(0, 0, 580, 80);
		jlb1.setBorder(BorderFactory.createCompoundBorder(bd4, bd1));
		jpl.add(jlb2);
		jlb2.setBounds(170, 120, 100, 50);
		jpl.add(jlb3);
		jlb3.setBounds(170, 160, 100, 50);
		jpl.add(jlb5);
		jlb5.setBounds(0, 270, 570, 50);
		jlb5.setBorder(BorderFactory.createCompoundBorder(bd4, bd1));
		jpl.add(user);
		user.setBounds(270, 130, 120, 30);
		user.setBorder(BorderFactory.createCompoundBorder(bd3, bd2));
		jpl.add(password);
		password.setBounds(270, 167, 120, 30);
		password.setBorder(BorderFactory.createCompoundBorder(bd3, bd2));
		jpl.add(login);
		login.setBounds(205, 220, 65, 30);
		jpl.add(register);
		register.setBounds(310, 220, 65, 30);
		login.addActionListener(this);

		register.addActionListener(this);
		contentPane.add(jpl);
		jpl.setLayout(null);
		frame.setBounds(400, 200, 580, 350);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new LoginSystem();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/* 登录注册按钮响应 */
	public void actionPerformed(ActionEvent e1) {
		String cmd = e1.getActionCommand();
		String name = user.getText();
		@SuppressWarnings("deprecation")
		String passwd = password.getText().trim();
		
		if(name.length() == 0 || passwd.length() == 0){
			JOptionPane.showMessageDialog(null, "请输入账号和密码", "警告",
					JOptionPane.WARNING_MESSAGE);
		}else{
			User user = new User(name, passwd);
			
			if(cmd.equals("登录")){
				if(OperationDB.login(user)){
					new MainSheet();
					frame.dispose();
				}else{
					JOptionPane.showMessageDialog(null, "账号不存在或者密码有误！", "警告",
							JOptionPane.WARNING_MESSAGE);
				}
			}else{
				if(OperationDB.isExist(name)){
					JOptionPane.showMessageDialog(null, "账号已存在！", "警告",
							JOptionPane.WARNING_MESSAGE);
				}else{
					if(OperationDB.register(user)){
						JOptionPane.showMessageDialog(null, "账号注册成功！", "OK",
								JOptionPane.WARNING_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null, "注册失败！", "警告",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		}
	}
}
