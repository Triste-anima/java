package Push;

import java.awt.Point;

public class Box {
	Point pos;
	
	public Box(Point p) {
		pos = p;
	}
	
	public Point getPos() {
		return pos;
	}
	
	public void setPos(int x, int y) {
		pos.setLocation(x, y);
	}
}
