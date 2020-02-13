package Chunk;

import java.awt.Point;

public class Block {
	protected Point pos;
	
	public Point getPos() {
		return pos;
	}
	
	public int getX() {
		return pos.x;
	}
	
	public int getY() {
		return pos.y;
	}
	
	public void setPos(int x, int y) {
		pos.setLocation(x, y);
	}
}
