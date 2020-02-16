package Block;

import javax.swing.ImageIcon;

public class Bullet extends Position {
	// ��ǰͼƬ
	private ImageIcon cur_imageIcon;
	
	public Bullet(double x, double y, ImageIcon i) {
		this.x = x;
		this.y = y;
		cur_imageIcon = i;
	}
	
	public ImageIcon getIcon() {
		return cur_imageIcon;
	}
	
	public void move() {
		y -= 20;
	}
}
