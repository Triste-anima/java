package Chess;

import java.awt.Point;

import AnimalChess.My_map;

public abstract class Chess {
	// 对应地图属性
	int attribute;
	// 位置
	protected Point pos;
	// 颜色
	protected String side;
	// 判断是否具备跳过河的能力
	protected boolean jumpable;
	// 判断是否在水里
	protected boolean inriver;
	// 判断是否被选中
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
	
	// 本身可以移动到该位置吗（除鼠外都不能下水）
	public abstract boolean moveable(Point p);
	// 与该位置原有动物冲突吗
	public abstract boolean confilct(int attr);
	// 判断是否为调入陷阱的敌人
	public boolean istraped_enemy(int attr, int i, int j) {
		if(attribute > 0 && attr < 0 && My_map.map[i][j] == 9)
			return true;
		if(attribute < 0 && attr > 0 && My_map.map[i][j] == -9)
			return true;
		return false;
	}
}
