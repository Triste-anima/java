package Chunk;

import java.awt.Point;

public class Bullet extends Block{
	// �ķ�������ӵ� 
	// 0-�з�  1-����
	private int side;
	// ����
	private String dir;
	// �ٶ�
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
