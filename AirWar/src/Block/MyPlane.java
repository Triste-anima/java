package Block;

import javax.swing.ImageIcon;

import AirWar.APanel;

public class MyPlane extends Position{
	public static final double HEIGHT = 88.2;
	public static final double WIDTH = 71.4;
	// 当前图片
	private ImageIcon cur_imageIcon;
	// 判断是否双发子弹
	private boolean isDoubleshot;
	
	public MyPlane(double x, double y, ImageIcon i) {
		this.x = x;
		this.y = y;
		cur_imageIcon = i;
		isDoubleshot = false;
	}
	
	public void setIcon(ImageIcon i) {
		cur_imageIcon = i;
	}
	
	public ImageIcon getIcon() {
		return cur_imageIcon;
	}
	
	public void setDoubleshot(Boolean b) {
		isDoubleshot = b;
	}
	
	public boolean getDoubleshot() {
		return isDoubleshot;
	}
	
	public void checkPos() {
		if(x < 0)
			x = 0;
		else if(x > APanel.WIDTH - MyPlane.WIDTH - 10)
			x = APanel.WIDTH - MyPlane.WIDTH - 10;
		
		if(y < 0)
			y = 0;
		else if(y > APanel.HEIGHT - MyPlane.HEIGHT)
			y = APanel.HEIGHT - MyPlane.HEIGHT;
	}
}
