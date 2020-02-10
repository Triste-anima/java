package Chess;

import java.awt.Point;

public abstract class Chess {
	// ��Ӧ��ͼ����
	protected int attribute;
	// λ��
	protected Point pos;
	// ��ɫ
	protected String side;
	// �ж��Ƿ�ѡ��
	protected boolean ischoosed = false;
	
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
	
	// ��������ƶ�����λ����
	public abstract boolean moveable(Point p);
	
	// ���λ��ԭ�����ӳ�ͻ��
	public boolean confilct(int attr) {
		// ��ͬ��Ӫ<0  �յ�=0  ���޳�ͻ
		if(attribute * attr <= 0)
			return false;
		
		return true;
	}
}
