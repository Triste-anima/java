package Chess;

import java.awt.Point;

import AnimalChess.My_map;

public class Elephant extends Chess{
	
	public Elephant(Point p, String s) {
		// TODO Auto-generated constructor stub
		if(s == "black")
			attribute = 8;
		else if(s == "red")
			attribute = -8;
		
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
		// �ж��Ƿ���ˮ
		if(My_map.map[x2][y2] == 11)
			return false;
		// �ж��Ƿ�Խ��
		if(x2 < 0 || x2 > 8 || y2 < 0 || y2 > 6)
			return false;
		// ��λ�ƶ�
		if(x1 == x2 && (y1 == y2 + 1 || y1 == y2 - 1))
			return true;
		if(y1 == y2 && (x1 == x2 + 1 || x1 == x2 - 1))
			return true;
			
		return false;
	}

	@Override
	public boolean confilct(int attr) {
		// TODO Auto-generated method stub
		if(side == "black" && ((-8 <= attr && attr <= -2) || attr == -10))
			return false;
		if(side == "red" && ((2 <= attr && attr <= 8) || attr == 10))
			return false;
		if(attr == 0 || attr == 9 || attr == -9)
			return false;
		
		return true;
	}
}
