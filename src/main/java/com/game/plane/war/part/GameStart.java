package com.game.plane.war.part;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import com.game.plane.war.beans.User;
import com.game.plane.war.page.LoginSystem;
import com.game.plane.war.persistence.OperationDB;

public class GameStart extends Frame {
	int height = 700;
	int width = 600;
	int count = 0;
	int num = 0;
	int score = 0;
	int bossTime = 5;
	public static int level = 1;
	boolean isOver = false;
	Random ran = new Random();
	Background bg = new Background(this);
	Plane plane = new Plane(250, 500, false, this);
	Boss boss = new Boss(30, 50, this, true);
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	List<Enemy> enemies = new ArrayList<Enemy>();
	public List<BulletEm> bulletEms = new ArrayList<BulletEm>();
	List<Explode> explodes = new ArrayList<Explode>();
	List<Food> foods = new ArrayList<Food>();
	Image img, bulletImg, bgImg, planeImg, bulletEmImg, bulletEm1Img, boosImg, ult, continueImg, lifePlane, startImg;
	Image[] bulletImgs, enemyImgs, boomImgs, bulletBossImgs, foodImgs;

	public GameStart() {
		this.setTitle("飞机大战");
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.setFont(new Font("楷体", Font.PLAIN, 16));
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//TODO 关闭窗口时，记录成绩
				User user = new User(LoginSystem.user.getText(), score, level);
				OperationDB.updateMatch(user);
				System.exit(0);
			}
		});
		
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				plane.keyPressed(e);
				if (!plane.alive && e.getKeyCode() == KeyEvent.VK_ENTER) {
					plane.alive = true;
					plane.isFirst = false;
					plane.count = 5;
					plane.x = 250;
					plane.y = 500;
					
					//TODO 配置背景音乐
					MusicPlayer musicPlayer = new MusicPlayer();
					musicPlayer.loadMusic(GameStart.class.getResource("/music/background.mid").getFile());
					musicPlayer.playMusic();
				}
				
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					int n = JOptionPane.showConfirmDialog(null, "退出游戏?", "飞机大战", JOptionPane.YES_NO_OPTION);
					if (n == 0) {
						User user = new User(LoginSystem.user.getText(), score, level);
						OperationDB.updateMatch(user);
						System.exit(0);
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				plane.keyReleased(e);
			}
		});
		new MyThread().start();
	}

	public void initView() {
		//TODO 更换背景图片
		if(level <= 3){
			bgImg = toolkit.getImage(GameStart.class.getResource("/images/a6.png"));
		}else if(level <= 6){
			bgImg = toolkit.getImage(GameStart.class.getResource("/images/bg01.jpg"));
		}else{
			bgImg = toolkit.getImage(GameStart.class.getResource("/images/a6.png"));
		}
		
		//TODO 同样道理。可以设置战机图片
		if (plane.isLeft) {
			planeImg = toolkit.getImage(GameStart.class.getResource("/images/51.png"));
		} else if (plane.isRight) {
			planeImg = toolkit.getImage(GameStart.class.getResource("/images/61.png"));
		} else {
			planeImg = toolkit.getImage(GameStart.class.getResource("/images/7.png"));
		}
		boomImgs = new Image[] { toolkit.getImage(GameStart.class.getResource("/images/b1.gif")),
				toolkit.getImage(GameStart.class.getResource("/images/b2.gif")),
				toolkit.getImage(GameStart.class.getResource("/images/b3.gif")),
				toolkit.getImage(GameStart.class.getResource("/images/b4.gif")),
				toolkit.getImage(GameStart.class.getResource("/images/b5.gif")),
				toolkit.getImage(GameStart.class.getResource("/images/b6.gif")),
				toolkit.getImage(GameStart.class.getResource("/images/b7.gif")),
				toolkit.getImage(GameStart.class.getResource("/images/b8.gif")),
				toolkit.getImage(GameStart.class.getResource("/images/b9.gif")),
				toolkit.getImage(GameStart.class.getResource("/images/b10.gif")),
				toolkit.getImage(GameStart.class.getResource("/images/b11.gif")) };
		enemyImgs = new Image[] { toolkit.getImage(GameStart.class.getResource("/images/5.png")),
				toolkit.getImage(GameStart.class.getResource("/images/21.png")),
				toolkit.getImage(GameStart.class.getResource("/images/15.png")),
				toolkit.getImage(GameStart.class.getResource("/images/敌机2.png")) };
		bulletImgs = new Image[] { toolkit.getImage(GameStart.class.getResource("/images/zidan.png")),
				toolkit.getImage(GameStart.class.getResource("/images/坦克.png")) };
		bulletEmImg = toolkit.getImage(GameStart.class.getResource("/images/敌军子弹.png"));
		bulletEm1Img = toolkit.getImage(GameStart.class.getResource("/images/敌军子弹1.png"));
		boosImg = toolkit.getImage(GameStart.class.getResource("/images/BossA.png"));
		bulletBossImgs = new Image[] { toolkit.getImage(GameStart.class.getResource("/images/BOSS子弹.png")),
				toolkit.getImage(GameStart.class.getResource("/images/子弹2.png")) };
		ult = toolkit.getImage(GameStart.class.getResource("/images/BKILL.png"));
		continueImg = toolkit.getImage(GameStart.class.getResource("/images/continue.png"));
		foodImgs = new Image[] { toolkit.getImage(GameStart.class.getResource("/images/食物1.jpg")),
				toolkit.getImage(GameStart.class.getResource("/images/22.png")) };
		lifePlane = toolkit.getImage(GameStart.class.getResource("/images/飞猪boss子弹.png"));
		startImg = toolkit.getImage(GameStart.class.getResource("/images/gamebegin1.gif"));
	}

	{
		explodes.add(new Explode(100, 100, this, true));
	}

	class MyThread extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				initView();
				repaint();
			}
		}
	}

	@Override
	public void update(Graphics g) {
		if (img == null) {
			img = this.createImage(width, height);
		}
		Graphics graphics = img.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, width, height);
		print(graphics);
		g.drawImage(img, 0, 0, null);
	}

	@Override
	public void paint(Graphics g) {
		if (!plane.isFirst) {
			bg.drawMe(g);
			//TODO 可以根据关卡级别设置敌机生成速度
			if (ran.nextInt(100) > 97) {
				enemies.add(new Enemy(ran.nextInt(500) + 10, 0, true, GameStart.this));
			}
			if (foods.size() < 3 && count == 5) {
				foods.add(new Food(ran.nextInt(500) + 10, 0, GameStart.this, true));
			}
			
			for (int i = 0; i < enemies.size(); i++) {
				Enemy enemy = enemies.get(i);
				if (enemy.alive) {
					enemy.drawMe(g);
				} else {
					enemies.remove(i);
				}
			}
			
			for (int i = 0; i < bulletEms.size(); i++) {
				BulletEm bullet = bulletEms.get(i);
				if (bullet.alive) {
					bullet.drawMe(g);
				} else {
					bulletEms.remove(i);
				}
			}

			for (int i = 0; i < explodes.size(); i++) {
				Explode explode = explodes.get(i);
				if (explode.alive) {
					explode.drawMe(g);
				} else {
					explodes.remove(explode);
				}
			}
			if (foods.size() > 0) {
				Food food = foods.get(0);
				if (food.alive) {
					food.drawMe(g);
				} else {
					foods.remove(food);
				}
			}
			if (count >= bossTime) {
				boss.alive = true;
				boss.drawMe(g);
			} else if (bossTime - count <= 3) {
				g.setColor(Color.RED);
				g.drawString("WARNNING", 250, 100);
			}
			g.drawString("第" + level + "关", 500, 50);
			g.drawString("得分：" + score, 500, 80);
		}
		plane.drawMe(g);
	}

	public static void main(String[] args) {
		new GameStart();
	}
}