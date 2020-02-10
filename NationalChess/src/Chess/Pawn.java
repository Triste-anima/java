package Chess;

import java.awt.Point;

public class Pawn extends Chess{
	// �Թ�·��
	private boolean last_move_two;
	
	public Pawn(int attr, Point p, String s) {
		attribute = attr;
		pos = p;
		side = s;
	}
	
	public boolean getLast_move_two() {
		return last_move_two;
	}
	
	public void setLast_move_two(boolean b) {
		last_move_two = b;
	}

	@Override
	public boolean moveable(Point p) {
		// TODO Auto-generated method stub
		int x1 = pos.x;
		int y1 = pos.y;
		int x2 = p.x;
		int y2 = p.y;
		// �ж��Ƿ�Խ��
		if(x2 < 0 || x2 > 7 || y2 < 0 || y2 > 7)
			return false;
		// ����ǰ��
		if(attribute == 1) {
			// ��һ��ǰ������
			if(x1 == 1 && x2 == 3 && y1 == y2) {
				last_move_two = true;
				return true;
			}	
			if(x1 == x2 - 1 && y1 == y2)
				return true;
		} else if(attribute == -1) {
			// ��һ��ǰ������
			if(x1 == 6 && x2 == 4 && y1 == y2) {
				last_move_two = true;
				return true;
			}
			if(x1 == x2 + 1 && y1 == y2)
				return true;
		}
		
		return false;
	}
	
	// ���صĳ����߷�
	public boolean pawn_eat_move(Point p) {
		int x1 = pos.x;
		int y1 = pos.y;
		int x2 = p.x;
		int y2 = p.y;
		// �ж��Ƿ�Խ��
		if(x2 < 0 || x2 > 7 || y2 < 0 || y2 > 7)
			return false;
		if(attribute == 1) {
			if(x1 == x2 - 1 && (y1 == y2 - 1 || y1 == y2 + 1))
				return true;
		} else if(attribute == -1) {
			if(x1 == x2 + 1 && (y1 == y2 - 1 || y1 == y2 + 1))
				return true;
		}
		
		return false;
	}
}
