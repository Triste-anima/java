package Chess;

import java.awt.Point;

import AnimalChess.My_map;

public class Leopard extends Chess{
	
	public Leopard(Point p, String s) {
		// TODO Auto-generated constructor stub
		if(s == "black")
			attribute = 5;
		else if(s == "red")
			attribute = -5;
		
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
		// 判断是否落水
		if(My_map.map[x2][y2] == 11)
			return false;
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
		// TODO Auto-generated method stub
		if(side == "black" && ((-5 <= attr && attr <= -1) || attr == -10))
			return false;
		if(side == "red" && ((1 <= attr && attr <= 5) || attr == 10))
			return false;
		if(attr == 0 || attr == 9 || attr == -9)
			return false;
		
		return true;
	}
}
