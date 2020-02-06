package Push;

import java.awt.Point;

public class Player {
	private Point pos;
	
	public Player(Point p) {
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
}
