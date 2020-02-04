package Gobang;

import java.awt.Point;

public class Chess {
	// Æå×ÓÎ»ÖÃ
	private Point pos;
	// °×Æå\ºÚÆå
	private String attr;
	
	public Chess() {}
	
	public Chess(Point p, String a) {
		pos = p;
		attr = a;
	}
	
	public Point getPos() {
		return pos;
	}
	
	public void setPos(Point p) {
		pos = p;
	}
	
	public String getAttr() {
		return attr;
	}
	
	public void setAttr(String a) {
		attr = a;
	}
}
