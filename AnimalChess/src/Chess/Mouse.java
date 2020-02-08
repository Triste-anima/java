package Chess;

import java.awt.Point;

public class Mouse extends Chess{
	
	public Mouse(Point p, String s) {
		// TODO Auto-generated constructor stub
		if(s == "black")
			attribute = 1;
		else if(s == "red")
			attribute = -1;
		
		pos = p;
		side = s;
		jumpable = false;
		inriver = false;
		ischoosed = false;
	}
	
	@Override
	public boolean moveable(Point p) {
		// TODO Auto-generated method stub
		int x1 = pos.x;
		int y1 = pos.y;
		int x2 = p.x;
		int y2 = p.y;
		// 判断是否越界
		if(x2 < 0 || x2 > 8 || y2 < 0 || y2 > 6)
			return false;
		// 邻位移动
		if(x1 == x2 && (y1 == y2 + 1 || y1 == y2 - 1))
			return true;
		if(y1 == y2 && (x1 == x2 + 1 || x1 == x2 - 1))
			return true;
			
		return false;
	}
	
	@Override
	public boolean confilct(int attr) {
		// 如果是对方的老鼠或大象则可以吃
		if(side == "black" && (attr == -1 || attr == -8 || attr == -10))
			return false;
		if(side == "red" && (attr == 1 || attr == 8 || attr == 10))
			return false;
		if(attr == 0 || attr == 9 || attr == -9 || attr == 11)
			return false;
		
		return true;
	}
}
