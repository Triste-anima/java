package Chunk;

import java.awt.Point;

public class Bullet extends Block{
	// 哪方射出的子弹 
	// 0-敌方  1-己方
	private int side;
	// 方向
	private String dir;
	// 速度
	private int speed = 1;
	
	public Bullet(Point p, int s, String d) {
		pos = p;
		side = s;
		dir = d;
	}
	
	public int getSide() {
		return side;
	}
	
	public String getDir() {
		return dir;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void move() {
		if(dir == "U")
			setPos(pos.x - speed, pos.y);
		else if(dir == "R")
			setPos(pos.x, pos.y + speed);
		else if(dir == "D")
			setPos(pos.x + speed, pos.y);
		else if(dir == "L")
			setPos(pos.x, pos.y - speed);
	}
	
	public void getShot(int s) {
		// TODO Auto-generated method stub
//		if(side != s)
//			isdestory = true;
	}
}
