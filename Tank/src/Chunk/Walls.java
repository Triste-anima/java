package Chunk;

import java.awt.Point;

public class Walls extends Block{
	// ����ש�������
	// 0-ש��  1-����
	private int unbreakable;
	
	public Walls(Point p, int u) {
		pos = p;
		unbreakable = u;
	}
	
	public int getUnbreakable() {
		return unbreakable;
	}
}
