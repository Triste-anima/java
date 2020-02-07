package Pac;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.sun.jdi.Value;

public class PacPanel extends JPanel implements KeyListener{
	
	// 玩家
	Player player;
	// 玩家的移动前的位置
	Point last_pos;
	// 当前地图
	int[][] cur_map;
	// 恶魔四人组
	Devil[] devils;
	// 时钟，控制恶魔移动
	Timer timer;
	// 恶魔移动时间间隔、追击距离
	int time_gap;
	int dis;
	// 游戏难度选项
	Object[] options = {"简单", "中等", "困难"};
	
	public PacPanel() {
		new My_map();
		cur_map = new int[27][21];
		devils = new Devil[4];
		initPanel();
		this.setFocusable(true);
		this.addKeyListener(this);
	}
	
	private void initPanel() {
		// 深拷贝,初始化当前关卡地图
		for(int i = 0; i < My_map.map1.length; ++i)
			cur_map[i] = My_map.map1[i].clone();
		
		player = new Player(new Point(1, 1));
		last_pos = new Point(1, 1);
		
		// 初始化恶魔位置
		devils[0] = new Devil(new Point(3, 3));
		devils[1] = new Devil(new Point(3, 13));
		devils[2] = new Devil(new Point(19, 3));
		devils[3] = new Devil(new Point(19, 14));
		// 选择游戏难度
		int value = JOptionPane.showOptionDialog(this, "请选择游戏难度", "下战书", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if(value != JOptionPane.CLOSED_OPTION) {
			switch(value) {
			case 0:
				time_gap = 1000;
				dis = 5;
				break;
			case 1:
				time_gap = 500;
				dis = 8;
				break;
			case 2:
				time_gap = 300;
				dis = 10;
				break;
			}
		} else {
			time_gap = 1000;
			dis = 5;
		}
		
		// 初始化恶魔移动时钟
		timer = new Timer(time_gap, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				for(int i = 0; i < devils.length; ++i) {
					if(devils[i].distance(player.getPos()) < dis)
						devils[i].chess(player.getPos());
					else
						devils[i].random_move();
					repaint();
					check_die();
				}
			}
		});
		timer.start();
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.BLACK);
		// 绘制当前地图
		for(int i = 0; i < 27; ++i) {
			for(int j = 0; j < 21; ++j) {
				int x = 20 + 20 * j;
				int y = 40 + 20 * i;
				if(cur_map[i][j] == 0) {
					g.setColor(Color.YELLOW);
					g.setFont(new Font("微软雅黑", Font.BOLD, 20));
					g.drawString("·", x + 5, y + 15);
				} else if(cur_map[i][j] == 1) {
					g.setColor(Color.GRAY);
					g.fillRect(x, y, 20, 20);
				} else if(cur_map[i][j] == 2) {
					g.setColor(Color.BLACK);
					g.fillRect(x, y, 20, 20);
				} else if(cur_map[i][j] == 3) {
					g.setColor(Color.YELLOW);
					g.setFont(new Font("华文行楷", Font.BOLD, 20));
					g.drawString("🐟", x + 2, y + 18);
				}
			}
		}
		
		// 绘制恶魔
		for(int i = 0; i < devils.length; ++i) {
			int x = 20 + 20 * devils[i].getY();
			int y = 40 + 20 * devils[i].getX();
			g.setColor(Color.RED);
			g.setFont(new Font("华文行楷", Font.BOLD, 20));
			g.drawString("🐖", x + 2, y + 18);
		}
	}
	
	private void change_map() {
		Point cur_player_pos = player.getPos();	
		int x = cur_player_pos.x;
		int y = cur_player_pos.y;
		
		cur_map[last_pos.x][last_pos.y] = 2;
		
		if(cur_map[x][y] == 0) {
			cur_map[x][y] = 3;
			last_pos.setLocation(x, y);
		} else if(cur_map[x][y] == 2) {
			cur_map[x][y] = 3;
			last_pos.setLocation(x, y);
		}
		
		repaint();
		check_die();
		check_win();
	}
	
	private void check_die() {
		for(int i = 0; i < devils.length; ++i) {
			// Point 没有重载 ==
			if(player.getX() == devils[i].getX() && player.getY() == devils[i].getY()) {
				JOptionPane.showMessageDialog(this, "被我追到了，我就把你嘿嘿嘿~", "Bravo", JOptionPane.PLAIN_MESSAGE);
				timer.stop();
				initPanel();
				return;
			}
		}
	}
	
	private void check_win() {
		for(int i = 0; i < 27; ++i) {
			for(int j = 0; j < 21; ++j) {
				if(cur_map[i][j] == 0)
					return;
			}
		}
		
		// 如果赢了
		JOptionPane.showMessageDialog(this, "恭喜🐟吃完了所有痘痘，体重破百！！", "Bravo", JOptionPane.PLAIN_MESSAGE);
		initPanel();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key_code = e.getKeyCode();
		if(key_code == KeyEvent.VK_UP) {
			Point temp = player.getPos();
			if(cur_map[temp.x - 1][temp.y] != 1)
				player.move_up();
		} else if(key_code == KeyEvent.VK_RIGHT) {
			Point temp = player.getPos();
			if(cur_map[temp.x][temp.y + 1] != 1)
				player.move_right();
		} else if(key_code == KeyEvent.VK_DOWN) {
			Point temp = player.getPos();
			if(cur_map[temp.x + 1][temp.y] != 1)
				player.move_down();
		} else if(key_code == KeyEvent.VK_LEFT) {
			Point temp = player.getPos();
			if(cur_map[temp.x][temp.y - 1] != 1)
				player.move_left();
		} else if(key_code == KeyEvent.VK_SPACE) {
			initPanel();
		}
		change_map();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}
