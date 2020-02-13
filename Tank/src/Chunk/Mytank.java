package Chunk;

import java.awt.Image;
import java.awt.Point;
import java.util.Random;

import javax.swing.ImageIcon;

public class Mytank extends Block{
	// 导入图片
	ImageIcon tankUIcon = new ImageIcon("p1tankU.gif");
	ImageIcon tankRIcon = new ImageIcon("p1tankR.gif");
	ImageIcon tankDIcon = new ImageIcon("p1tankD.gif");
	ImageIcon tankLIcon = new ImageIcon("p1tankL.gif");
	
	ImageIcon enemyUIcon = new ImageIcon("enemy1U.gif");
	ImageIcon enemyRIcon = new ImageIcon("enemy1R.gif");
	ImageIcon enemyDIcon = new ImageIcon("enemy1D.gif");
	ImageIcon enemyLIcon = new ImageIcon("enemy1L.gif");
	
	// 0-敌人  1-自己人
	private int side;
	// 方向
	private String dir;
	// 判断是否还活着
	private Boolean isalive;
	// 控制射击频率
	private Boolean fireable;
	// 控制移动频率
	private Boolean moveable;
	// 无敌状态
	private Boolean unstoppable;
	
	public Mytank(Point p, int s, String d) {
		pos = p;
		side = s;
		dir = d;
		isalive = true;
		
		if(s == 1) {
			fireable = true;
			moveable = true;
			unstoppable = true;
		}
		
		tankUIcon = change(tankUIcon, 0.9);
		tankRIcon = change(tankRIcon, 0.9);
		tankDIcon = change(tankDIcon, 0.9);
		tankLIcon = change(tankLIcon, 0.9);
		
		enemyUIcon = change(enemyUIcon, 0.9);
		enemyRIcon = change(enemyRIcon, 0.9);
		enemyDIcon = change(enemyDIcon, 0.9);
		enemyLIcon = change(enemyLIcon, 0.9);
	}
	
	public int getSide() {
		return side;
	}
	
	public void setDir(String d) {
		dir = d;
	}
	
	public String getDir() {
		return dir;
	}
	
	public Boolean getIsalive() {
		return isalive;
	}
	
	public void setFireable(boolean f) {
		fireable = f;
	}
	
	public boolean getFireable() {
		return fireable;
	}
	
	public void setMoveable(boolean m) {
		moveable = m;
	}
	
	public boolean getMoveable() {
		return moveable;
	}
	
	public void setUnstoppable(boolean s) {
		unstoppable = s;
	}
	
	public boolean getUnstoppable() {
		return unstoppable;
	}
	
	public void move_toward_dir() {
		if(dir == "U")
			moveUp();
		else if(dir == "R")
			moveRight();
		else if(dir == "D")
			moveDown();
		else 
			moveLeft();
	}
	
	public void change_dir() {
		Random random = new Random();
		int temp = random.nextInt(4);
		if(temp == 0)
			dir = "U";
		else if(temp == 1)
			dir = "R";
		else if(temp == 2)
			dir = "D";
		else 
			dir = "L";
	}
	
	public ImageIcon getImage(String d) {
		if(side == 0) {
			if(d == "U")
				return enemyUIcon;
			else if(d == "R")
				return enemyRIcon;
			else if(d == "D")
				return enemyDIcon;
			else
				return enemyLIcon;
		} else {
			if(d == "U")
				return tankUIcon;
			else if(d == "R")
				return tankRIcon;
			else if(d == "D")
				return tankDIcon;
			else
				return tankLIcon;
		}
	}
	
	public void moveUp() {
		pos.setLocation(pos.x - 1, pos.y);
	}
	
	public void moveRight() {
		pos.setLocation(pos.x, pos.y + 1);
	}
	
	public void moveDown() {
		pos.setLocation(pos.x + 1, pos.y);
	}
	
	public void moveLeft() {
		pos.setLocation(pos.x, pos.y - 1);
	}
	
	// 缩放图片， times为缩放倍数
	public ImageIcon change(ImageIcon image, double times) {
		int width = (int)(image.getIconWidth() * times);
		int height = (int)(image.getIconHeight() * times);
		Image img = image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		ImageIcon image2 = new ImageIcon(img);
		
		return image2;
	}

//	public void shot() {
//		
//	}
	
	public void getShot() {
		// TODO Auto-generated method stub
		isalive = false;
	}
}
