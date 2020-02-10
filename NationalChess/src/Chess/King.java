package Chess;

import java.awt.Point;

public class King extends Chess{
	// 判断是否移动过-用来实现王车易位
	private boolean ismoved = false;
	
	public King(int attr, Point p, String s) {
		attribute = attr;
		pos = p;
		side = s;
	}
	
	public boolean getIsmoved() {
		return ismoved;
	}
	
	public void setIsmoved(boolean b) {
		ismoved = b;
	}
	
	public boolean change_with_rook(int i, int j) {
		if(attribute == 6) {
			if(i == 0 && (pos.y == j - 2 || pos.y == j + 2))
				return true;
		}
		
		if(attribute == -6) {
			if(i == 7 && (pos.y == j - 2 || pos.y == j + 2))
				return true;
		}
		
		return false;
	}

	@Override
	public boolean moveable(Point p) {
		// TODO Auto-generated method stub
		int x1 = pos.x;
		int y1 = pos.y;
		int x2 = p.x;
		int y2 = p.y;
		// 判断是否越界
		if(x2 < 0 || x2 > 7 || y2 < 0 || y2 > 7)
			return false;
		// 九宫格
		if(x1 == x2 - 1 && y1 == y2 - 1)
			return true;
		if(x1 == x2 - 1 && y1 == y2)
			return true;
		if(x1 == x2 - 1 && y1 == y2 + 1)
			return true;
		if(x1 == x2 && y1 == y2 - 1)
			return true;
		if(x1 == x2 && y1 == y2 + 1)
			return true;
		if(x1 == x2 + 1 && y1 == y2 - 1)
			return true;
		if(x1 == x2 + 1 && y1 == y2)
			return true;
		if(x1 == x2 + 1 && y1 == y2 + 1)
			return true;

		return false;
	}
}
