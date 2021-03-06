package com.game.plane.war.part;

import java.awt.Graphics;
import java.awt.Rectangle;

//控制敌机子弹轨迹
public class BulletEm {
	int x;
	int y;
	int width = 3;
	int height = 4;
	boolean alive;
	GameStart gs;
	Enemy enemy;
	Graphics g;

	public BulletEm(boolean alive, GameStart gs, Enemy enemy) {
		super();
		this.y = enemy.y + 80;
		this.x = enemy.x + 34;
		this.alive = alive;
		this.gs = gs;
		this.enemy = enemy;
	}

	public void drawMe(Graphics g) {
		isHitted();
		if (alive) {
			g.drawImage(gs.bulletEm1Img, x, y, width, height, null);
		}
		move();
	}

	public void isHitted() {
		Plane plane = gs.plane;
		if (plane.alive && plane.getRectangle().intersects(getRectangle())) {
			alive = false;
			plane.count--;
			plane.canK = false;
			plane.canL = false;
			
			//TODO 停止播放背景音乐
			if (plane.count == 0) {
				gs.explodes.add(new Explode(plane.x, plane.y, gs, true));
				plane.alive = false;
				
				GameStart.musicPlayer.stopPlaying();
			}

		}
	}

	public void move() {
		y += 8;
		if (y > 700) {
			alive = false;
		}
	}

	public void ult() {

	}

	public Rectangle getRectangle() {
		return new Rectangle(x, y, width, height);
	}
}
