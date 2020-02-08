package Chess;

import java.awt.Point;

import AnimalChess.My_map;

public abstract class Chess {
	// ��Ӧ��ͼ����
	int attribute;
	// λ��
	protected Point pos;
	// ��ɫ
	protected String side;
	// �ж��Ƿ�߱������ӵ�����
	protected boolean jumpable;
	// �ж��Ƿ���ˮ��
	protected boolean inriver;
	// �ж��Ƿ�ѡ��
	protected boolean ischoosed;
	
	public int getAttribute() {
		return attribute;
	}
	
	public Point getPos() {
		return pos;
	}
	
	public int getX() {
		return pos.x;
	}
	
	public int getY() {
		return pos.y;
	}
	
	public String getSide() {
		return side;
	}
	
	public boolean getJumpable() {
		return jumpable;
	}
	
	public boolean getInriver() {
		return inriver;
	}
	
	public void setInriver(boolean b) {
		inriver = b;
	}
	
	public boolean getIschoosed() {
		return ischoosed;
	}
	
	public void setIschoosed(boolean b) {
		ischoosed = b;
	}
	
	public void setPos(Point p) {
		pos = p;
	}
	
	public void setPos(int x, int y) {
		pos.setLocation(x, y);
	}
	
	// ��������ƶ�����λ���𣨳����ⶼ������ˮ��
	public abstract boolean moveable(Point p);
	// ���λ��ԭ�ж����ͻ��
	public abstract boolean confilct(int attr);
	// �ж��Ƿ�Ϊ��������ĵ���
	public boolean istraped_enemy(int attr, int i, int j) {
		if(attribute > 0 && attr < 0 && My_map.map[i][j] == 9)
			return true;
		if(attribute < 0 && attr > 0 && My_map.map[i][j] == -9)
			return true;
		return false;
	}
}
