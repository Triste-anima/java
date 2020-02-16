package Block;

import javax.swing.ImageIcon;

public class Enemy extends Position {
	// 三种机型的大小
	public static final double HEIGHT1 = 30.1;
	public static final double WIDTH1 = 39.9;
	
	public static final double HEIGHT2 = 69.3;
	public static final double WIDTH2 = 48.3;
	
	public static final double HEIGHT3 = 180.6;
	public static final double WIDTH3 = 118.3;
	// 大小-3种机型
	private int size;
	// 血量
	private int hp;
	// 当前图片
	private ImageIcon cur_imageIcon;
	// 值得的分数
	private int score;

	public Enemy(double x, double y, int s, ImageIcon i) {
		this.x = x;
		this.y = y;
		size = s;
		cur_imageIcon = i;
		
		if(s == 1) {
			hp = 1;
			score = 1000;
		} else if(s == 2) {
			hp = 5;
			score = 5000;
		} else {
			hp = 12;
			score = 10000;
		}
	}
	
	public int getSize() {
		return size;
	}
	
	public int getHp() {
		return hp;
	}
	
	public void setHp(int h) {
		hp = h;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setIcon(ImageIcon i) {
		cur_imageIcon = i;
	}
	
	public ImageIcon getIcon() {
		return cur_imageIcon;
	}
	
	public double getWidth() {
		if(size == 1)
			return WIDTH1;
		else if(size == 2)
			return WIDTH2;
		else
			return WIDTH3;
	}
	
	public double getHeight() {
		if(size == 1)
			return HEIGHT1;
		else if(size == 2)
			return HEIGHT2;
		else
			return HEIGHT3;
	}
	
	public void move() {
		if(size == 1)
			y += 20;
		else if(size == 2)
			y += 15;
		else
			y += 10;
	}
}
