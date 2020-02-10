package Chess;

import java.awt.Point;

public class Knight extends Chess{
	public Knight(int attr, Point p, String s) {
		attribute = attr;
		pos = p;
		side = s;
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
		// 走日字
		if(x1 == x2 + 1 && y1 == y2 + 2)
			return true;
		if(x1 == x2 + 2 && y1 == y2 + 1)
			return true;
		if(x1 == x2 + 2 && y1 == y2 - 1)
			return true;
		if(x1 == x2 - 1 && y1 == y2 + 2)
			return true;
		if(x1 == x2 - 1 && y1 == y2 - 2)
			return true;
		if(x1 == x2 - 2 && y1 == y2 - 1)
			return true;
		if(x1 == x2 - 2 && y1 == y2 + 1)
			return true;
		if(x1 == x2 + 1 && y1 == y2 - 2)
			return true;
		
		return false;
	}
}
