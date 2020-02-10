package Chess;

import java.awt.Point;

public abstract class Chess {
	// 对应地图属性
	protected int attribute;
	// 位置
	protected Point pos;
	// 颜色
	protected String side;
	// 判断是否被选中
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
	
	// 本身可以移动到该位置吗
	public abstract boolean moveable(Point p);
	
	// 与该位置原有棋子冲突吗
	public boolean confilct(int attr) {
		// 不同阵营<0  空地=0  则无冲突
		if(attribute * attr <= 0)
			return false;
		
		return true;
	}
}
