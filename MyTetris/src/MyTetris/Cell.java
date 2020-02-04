package MyTetris;

import java.awt.Point;

public class Cell {
	/**
	 * 对于每个方块所写的类
	 */
	private Point pos;
	private int[] key;	// 记录具体形状的方向
	
	public Cell(Point p) {
		pos = p;
		key = Block.getRandomKey();
	}
	
	public Cell(Point p, int[] k) {
		pos = p;
		key = k;
	}
	
	// 获取具体的形状
	public Point[] getBlock() {
		return Block.getBlockByKey(key);
	}
	
	// 按照指令改变方向
	public Cell getChangeCell(boolean isDirChanged, int x, int y) {
		Point r_pos = new Point(this.pos.x + x, this.pos.y + y);
		int[] r_key;
		if(isDirChanged)
			r_key = Block.getNextKey(this.key);
		else 
			r_key = new int[]{this.key[0],this.key[1]}; //深拷贝
		
		return new Cell(r_pos, r_key);
	}
	
	// 获取当前图形绘出的坐标
	public Point[] getPaintLocation() {
		Point[] origin = Block.getBlockByKey(key);
		Point[] result = new Point[origin.length];
		for(int i = 0; i < result.length; ++i) {
			int new_x = origin[i].x + pos.x;
			int new_y = origin[i].y + pos.y;
			result[i] = new Point(new_x, new_y);
		}
		
		return result;
	}
}
