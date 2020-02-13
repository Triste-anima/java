package Chunk;

import java.awt.Point;

public class Walls extends Block{
	// Çø·Ö×©¿éºÍÌú¿é
	// 0-×©¿é  1-Ìú¿é
	private int unbreakable;
	
	public Walls(Point p, int u) {
		pos = p;
		unbreakable = u;
	}
	
	public int getUnbreakable() {
		return unbreakable;
	}
}
