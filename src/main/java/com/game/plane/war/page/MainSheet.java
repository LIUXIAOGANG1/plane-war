package com.game.plane.war.page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.Border;

import com.game.plane.war.utils.MenuBarEvent;


public class MainSheet {
	public static JFrame frame = new JFrame();
	Container contentPane = frame.getContentPane();
	JDesktopPane desktop = new JDesktopPane();
	MenuBarEvent MenuBarEvent = new MenuBarEvent();// 自定义事件类处理
	JMenuBar jmenubar = new JMenuBar();
	JToolBar jtoolbar = new JToolBar();


	/* 日期 */
	JTextArea jtatime = new JTextArea();

	JPanel jpl = new JPanel();
	JPanel jplWEST = new JPanel();
	FlowLayout flowLayout = new FlowLayout();
	Border b1 = BorderFactory.createLineBorder(Color.gray);// 线边库
	Border b2 = BorderFactory.createRaisedBevelBorder();// 凸边框
	Border b3 = BorderFactory.createLoweredBevelBorder();// 凹边框
	Border b4 = BorderFactory.createEtchedBorder(); // 腐蚀边框

	/* 界面总布局 */
	public MainSheet() {
		try {
			frame.setTitle("飞机大战管理界面");
			contentPane.setLayout(new BorderLayout());
			contentPane.add(desktop, BorderLayout.CENTER);
			contentPane.add(jtoolbar, BorderLayout.NORTH);
			jpl.setLayout(new BorderLayout());
			flowLayout.setAlignment(FlowLayout.LEFT);
			contentPane.add(jpl, BorderLayout.SOUTH);
			jpl.setBorder(BorderFactory.createCompoundBorder(b1, b2));

			/* 设置时间 */
			GregorianCalendar calendar = new GregorianCalendar();
			int cyear = calendar.get(Calendar.YEAR);
			int cmonth = calendar.get(Calendar.MONTH) + 1;
			int cday = calendar.get(Calendar.DAY_OF_MONTH);
			int chour = calendar.get(Calendar.HOUR_OF_DAY);
			int cminute = calendar.get(Calendar.MINUTE);

			String year = String.valueOf(cyear);
			String month = String.valueOf(cmonth);
			String day = String.valueOf(cday);
			String hour = String.valueOf(chour);
			String minute = String.valueOf(cminute);
			String time = year + " / " + month + " / " + day;
			String timeday = hour + " : " + minute;

			jtatime.setText("    " + timeday);
			jtatime.append("\n" + time + "    ");
			jtatime.setEditable(false);
			jpl.add(jtatime, BorderLayout.EAST);

			desktop.setBorder(BorderFactory.createCompoundBorder(b1, b3));
			jtoolbar.setBorder(BorderFactory.createCompoundBorder(b1, b2));
			jmenubar.setBorder(BorderFactory.createLineBorder(Color.gray));
			MenuBarEvent.setDeskTop(desktop);
			jtoolbar.setFloatable(false);// 设置jtoolbar不可浮动
			frame.setJMenuBar(jmenubar);
			BuildMenuBar();

			/* 设置当前操作员 */
			jpl.add(jplWEST, BorderLayout.WEST);
			jplWEST.setLayout(flowLayout);

			BuildToolBar();
			loadBackgroundImage();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);// 默认最大化
			frame.setBounds(200, 100, 880, 600);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* 背景图片 */
	protected void loadBackgroundImage() {
		ImageIcon icon = new ImageIcon(LoginSystem.class.getResource("/images/mainsheet.jpg"));
		JLabel jlb = new JLabel(icon);
		jlb.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
		desktop.add(jlb, new Integer(Integer.MIN_VALUE));
	}

	/* 布局菜单栏 */
	private void BuildMenuBar() {
		JMenu[] jMenu = { new JMenu("【游戏管理】"), new JMenu("【分数管理】"), new JMenu("【帮助】") };
		/* 设置菜单相目JMenuItem元素 */
		JMenuItem[] jMenuItem0 = { new JMenuItem("【开始游戏】") };
		String[] jMenuItem0Name = { "startGame" };
		
		JMenuItem[] jMenuItem1 = { new JMenuItem("【查询排名】") };
		String[] jMenuItem1Name = { "queryRanking" };
		
		JMenuItem[] jMenuItem2 = { new JMenuItem("【帮助】") };
		String[] jMenuItem2Name = { "help" };
		
		/* 设置菜单、菜单项字体风格 */
		Font MenuItemFont = new Font("宋体", 0, 15);

		for (int i = 0; i < jMenu.length; i++) {
			jMenu[i].setFont(MenuItemFont);
			jmenubar.add(jMenu[i]);
		}

		for (int j = 0; j < jMenuItem0.length; j++) {
			jMenuItem0[j].setFont(MenuItemFont);
			final String EventName0 = jMenuItem0Name[j];
			jMenuItem0[j].addActionListener(MenuBarEvent);
			jMenuItem0[j].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MenuBarEvent.setEventName(EventName0);
				}
			});
			jMenu[0].add(jMenuItem0[j]);
		}

		for (int j = 0; j < jMenuItem1.length; j++) {
			jMenuItem1[j].setFont(MenuItemFont);
			final String EventName1 = jMenuItem1Name[j];
			jMenuItem1[j].addActionListener(MenuBarEvent);
			jMenuItem1[j].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MenuBarEvent.setEventName(EventName1);
				}
			});
			jMenu[1].add(jMenuItem1[j]);
		}
		
		for (int j = 0; j < jMenuItem2.length; j++) {
			jMenuItem2[j].setFont(MenuItemFont);
			final String EventName2 = jMenuItem2Name[j];
			jMenuItem2[j].addActionListener(MenuBarEvent);
			jMenuItem2[j].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MenuBarEvent.setEventName(EventName2);
				}
			});
			jMenu[2].add(jMenuItem2[j]);
		}
	}

	/* 布局工具栏 */
	private void BuildToolBar() {
		String ImageName[] = { "start.gif", "query.GIF",
				"exit.GIF" };

		String TipString[] = { "开始游戏", "查询排名", "系统退出" };

		final String EventNames[] = { "startGame", "queryRanking", "exit" };
		
		for (int i = 0;i < EventNames.length; i++) {
			JButton jb = new JButton();
			ImageIcon image = new ImageIcon(LoginSystem.class.getResource("/images/" + ImageName[i]));
			jb.setIcon(image);
			jb.setToolTipText(TipString[i]);
			jb.addActionListener(MenuBarEvent);
			final String eventName = EventNames[i];
			jb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MenuBarEvent.setEventName(eventName);
				}
			});
			jtoolbar.add(jb);
		}
	}
	
	public static void main(String[] args) {
		new MainSheet();
	}
}
