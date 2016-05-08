package com.game.plane.war.page;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.DefaultDesktopManager;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.game.plane.war.utils.RetrieveObject;

public class QueryRanking extends JInternalFrame {
	/* 声明并初始化主界面面板 */
	JSplitPane jsplitPane = new JSplitPane();
	JScrollPane jscrollPane = new JScrollPane();
	JPanel jpl1 = new JPanel();
	JPanel jpl2 = new JPanel();

	/* 声明并初始化数据表 */
	static JTable jtb = new JTable();

	/* 声明并初始化选中表时读写的文本框 */
	static JTextField jtf1 = new JTextField();
	static JTextField jtf2 = new JTextField();
	static JTextField jtf3 = new JTextField();

	/* 声明并初始化按钮 */
	JButton jbtPrint = new JButton("打印");
	JButton jbtExit = new JButton("退出");

	static String ID[] = null;// buildJcombox()中用到

	static boolean f = true;// 防止多次打开

	public QueryRanking() {
		/* 声明并初始化JInternalFrame */
		super("游戏排名", true, true, true, true);// 可以最大最小化
		/* 按钮添加打击时间 */
		jbtExit.addActionListener(new jbtExit_actionAdapter(this));
		jtb.addMouseListener(new jtb_mouseAdapter(this));

		try {
			LayOut();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/* 设置布局 */
	private void LayOut() throws Exception {
		String sql = "SELECT name, score, level FROM user ORDER BY level DESC LIMIT 0, 3;";
		buildTable(sql);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(jpl1, BorderLayout.NORTH);
		FlowLayout flowLayout = new FlowLayout();
		jpl1.setLayout(flowLayout);
		flowLayout.setAlignment(FlowLayout.RIGHT);// 设置布局的对其方式
		jpl1.add(jbtPrint);
		jpl1.add(jbtExit);
		jpl2.setLayout(new GridLayout(1, 7));
		this.getContentPane().add(jsplitPane, BorderLayout.CENTER);
		jsplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jsplitPane.add(jscrollPane, JSplitPane.TOP);
		jscrollPane.getViewport().add(jtb);// 设置为视图中心区
		jsplitPane.add(jpl2, JSplitPane.BOTTOM);
		jpl2.add(jtf1);
		jpl2.add(jtf2);
		jpl2.add(jtf3);

		jtf1.setEditable(false);
		jtf2.setEditable(false);
		jtf3.setEditable(false);

		jsplitPane.setDividerLocation(520);// 设置分隔条位置

		setSize(420, 340);
		this.setClosable(true);
		setVisible(true);
	}

	/* 创建数据表函数 */
	public static void buildTable(String str) {
		DefaultTableModel tablemodel = null;
		String[] name = { "用户", "分数", "关卡"};
		RetrieveObject bdt = new RetrieveObject();
		tablemodel = bdt.getTableModel(name, str);
		jtb.setModel(tablemodel);
		jtb.setRowHeight(24);
	}

	/* 退出按钮 */

	class jbtExit_actionAdapter implements ActionListener {
		private QueryRanking adaptee;

		jbtExit_actionAdapter(QueryRanking adaptee) {
			this.adaptee = adaptee;
		}

		public void actionPerformed(ActionEvent e) {
			adaptee.jbtExit_actionPerformed(e);
		}
	}

	public void jbtExit_actionPerformed(ActionEvent e) {
		DefaultDesktopManager manger = new DefaultDesktopManager();
		int result = JOptionPane.showOptionDialog(null, "是否退出?", "系统提示",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				new String[] { "是", "否" }, "否");
		if (result == JOptionPane.YES_OPTION) {
			manger.closeFrame(this);
		}
	}

	/* 表侦听 */

	class jtb_mouseAdapter extends MouseAdapter {
		private QueryRanking adaptee;

		jtb_mouseAdapter(QueryRanking adaptee) {
			this.adaptee = adaptee;
		}

		public void mouseClicked(MouseEvent e) {
//			adaptee.jtb_mouseClicked(e);
		}
	}

	public void jtb_mouseClicked(MouseEvent e) {
		String ID = null;
		String sqlStr = null;
		int selectrow = 0;
		selectrow = jtb.getSelectedRow();// 返回第一个 选定行 的索引；如果没有选定的行，则返回 -1。
		if (selectrow < 0)
			return;

		ID = jtb.getValueAt(selectrow, 0).toString();// getValueAt(int row,int
														// column):返回 row 和
														// column 位置的单元格值。
		/*
		 * 注：列是以表视图的显示顺序，而不是以 TableModel 的列顺序指定的。这是一项重要的区别，
		 * 因为在用户重新安排表中的列时，视图中给定索引处的列将更改。同时用户的操作不会影响模型的列顺序。
		 */

		sqlStr = "SELECT * from LoginRecord" + " where 流水号 = '" + ID + "'";

		Vector vdata = null;
		RetrieveObject retrive = new RetrieveObject();
		vdata = retrive.getObjectRow(sqlStr);

		jtf1.setText(vdata.get(0).toString());
		jtf1.setEditable(false);
		jtf2.setText(vdata.get(1).toString());
		jtf3.setText(vdata.get(2).toString());

	}
}
