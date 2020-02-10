package Chess;

import java.awt.Point;

public class Rook extends Chess{
	// 判断是否移动过-用来实现王车易位
	private boolean ismoved = false;
	
	public Rook(int attr, Point p, String s) {
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
	
	public boolean change_with_king(int i, int j) {
		if(attribute == 2 && i == 0) {
			if(pos.y == 0 && j == 3)
				return true;
			if(pos.y == 7 && j == 5)
				return true;
		}
		
		if(attribute == -2 && i == 7) {
			if(pos.y == 0 && j == 3)
				return true;
			if(pos.y == 7 && j == 5)
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
		// 直线
		if(x1 == x2 || y1 == y2)
			return true;
		
		return false;
	}
}
