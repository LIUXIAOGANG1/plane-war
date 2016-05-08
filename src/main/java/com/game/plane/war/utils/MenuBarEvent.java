package com.game.plane.war.utils;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.*;

import com.game.plane.war.page.LoginSystem;
import com.game.plane.war.page.QueryRanking;
import com.game.plane.war.part.GameStart;

public class MenuBarEvent implements ActionListener {
	static JDesktopPane JDeskTop = null;
	private String EventName = "";
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void setDeskTop(JDesktopPane deskTop) {
		MenuBarEvent.JDeskTop = deskTop;
	}

	public void setEventName(String eventName) {
		this.EventName = eventName;
	}

	public void actionPerformed(ActionEvent e) {
		/* 退出系统 */
		if (EventName.equals("exit")) {
			int result = JOptionPane.showOptionDialog(null, "是否真的退出系统?", "系统提示", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, new String[] { "是", "否" }, "否");
			if (result == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
			return;
		}

		/* 开始游戏 */
		if (EventName.equals("startGame")) {
			new GameStart();
		}

		/* 查询分数排名 */
		if (EventName.equals("queryRanking")) {
			QueryRanking jfInternalFrame = new QueryRanking();
			jfInternalFrame.setBounds(0, 0, 800, 600);
			JDeskTop.add(jfInternalFrame);
			jfInternalFrame.show();
			jfInternalFrame.setTitle("上机日志");
			JDeskTop.getDesktopManager().activateFrame(jfInternalFrame);
		}
		
		/* 帮助 */
		if (EventName.equals("help")) {
			try {
				Desktop.getDesktop().open(new File(LoginSystem.class.getResource("/properties/help.doc").getFile()));
			} catch (Exception help) {
				help.printStackTrace();
			}
		}
	}
}
