package com.game.plane.war.part;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

public class Enemy {
	int x;
	int y;
	int fx;
	int width = 68;
	int height = 68;
	boolean alive;
	Image enemyImg;
	GameStart gs;
	int k = 1;
	Random random = new Random();

	public Enemy(int x, int y, boolean alive, GameStart gs) {
		super();
		this.x = x;
		this.y = y;
		this.fx = x;
		this.alive = alive;
		this.gs = gs;
		this.enemyImg = gs.enemyImgs[random.nextInt(4)];
	}

	public void drawMe(Graphics g) {
		isHitted();
		if (alive) {
			g.drawImage(enemyImg, x, y, width, height, null);
		} else {
			gs.explodes.add(new Explode(x, y, gs, true));
			gs.count++;
			gs.score += 100;
			gs.enemies.remove(this);
		}

		move();
		
		//TODO 通过关卡等级不同，控制敌机火力
		if(GameStart.level < 3){
			if (random.nextInt(100) > 97){
				fire();
			}
		}else if(GameStart.level < 5){
			if (random.nextInt(100) > 80){
				fire();
			}
		}else{
			if (random.nextInt(100) > 60){
				fire();
			}
		}
	}

	//TODO 敌机发射子弹，可以添加音效（建议不添加，否则声音太乱）
	public void fire() {
		BulletEm bulletEm = new BulletEm(true, gs, this);
		gs.bulletEms.add(bulletEm);
	}

	public void isHitted() {
		for (int j = 0; j < gs.plane.bullets.size(); j++) {
			Bullet pBullet = gs.plane.bullets.get(j);
			if (pBullet.getRectangle().intersects(getRectangle())) {
				alive = false;
				pBullet.alive = false;
			}
		}
		for (int j = 0; j < gs.plane.ults.size(); j++) {
			Ult ult = gs.plane.ults.get(j);
			if (ult.getRectangle().intersects(getRectangle())) {
				alive = false;
			}
		}
		Plane plane = gs.plane;
		if (plane.alive && plane.getRectangle().intersects(getRectangle())) {
			alive = false;
			plane.count--;
			if (plane.count == 0) {
				plane.alive = false;
			}

		}
	}

	public Rectangle getRectangle() {
		return new Rectangle(x, y, width, height);
	}

	//TODO 控制敌机运动轨迹
	public void move() {
//		if (fx >= 500) {
//			k = -k;
//			x -= 5 * k;
////			x -= 3;
//		} else if (fx <= 100) {
////			x += 3;
//			x += 5 * k;
//		}
//		y += 5;
		
		if (x > 500 || x < -200)
			k = -k;
		x += 5 * k;
		y += 5;
		
		if (y > 700){
			alive = false;
		}
	}
}
