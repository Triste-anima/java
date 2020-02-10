package Chess;

import java.awt.Point;

public class Bishop extends Chess{
	public Bishop(int attr, Point p, String s) {
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
		// 斜线
		if(Math.abs(x1 - x2) == Math.abs(y1 - y2))
			return true;
		
		return false;
	}
}
