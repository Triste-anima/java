package Pac;

import java.awt.Point;
import java.util.Random;

public class Devil {
	Point pos;
	
	public Devil(Point p) {
		pos = p;
	}

	public int getX() {
		return pos.x;
	}
	
	public int getY() {
		return pos.y;
	}
	
	public Point getPos() {
		return pos;
	}
	
	public void setPos(int x, int y) {
		pos.setLocation(x, y);
	}
	
	public void move_up() {
		setPos(pos.x - 1, pos.y);
	}
	
	public void move_right() {
		setPos(pos.x, pos.y + 1);
	}
	
	public void move_down() {
		setPos(pos.x + 1, pos.y);
	}
	
	public void move_left() {
		setPos(pos.x, pos.y - 1);
	}
	
	public void random_move() {
		Random random = new Random();
		int next = random.nextInt(4);
		switch(next) {
		case 0:
			if(pos.x - 1 > 0)
				move_up();
			break;
		case 1:
			if(pos.y - 1 > 0)
				move_left();
			break;
		case 2:
			if(pos.x + 1 < 27)
				move_down();
			break;
		case 3:
			if(pos.y + 1 < 21)
				move_right();
			break;
		}
	}
	
	public int distance(Point p) {
		int dx = pos.x - p.x;
		int dy = pos.y - p.y;
		int result = (int)Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		return result;
	}
	
	public void chess(Point p) {
		Devil temp;
		int dis = distance(p);
		
		temp = new Devil(new Point(pos.x - 1, pos.y));
		if(temp.distance(p) < dis) {
			setPos(temp.getX(), temp.getY());
			return;
		}
		
		temp = new Devil(new Point(pos.x + 1, pos.y));
		if(temp.distance(p) < dis) {
			setPos(temp.getX(), temp.getY());
			return;
		}
			
		temp = new Devil(new Point(pos.x, pos.y + 1));
		if(temp.distance(p) < dis) {
			setPos(temp.getX(), temp.getY());
			return;
		}
		
		temp = new Devil(new Point(pos.x, pos.y - 1));
		if(temp.distance(p) < dis) {
			setPos(temp.getX(), temp.getY());
			return;
		}
	}
}
