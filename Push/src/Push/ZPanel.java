package Push;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ZPanel extends JPanel implements KeyListener{
	
	// 玩家
	Player player;
	// 玩家的移动前的位置、被占领位置原本的属性
	Point last_pos;
	// 箱子
	LinkedList<Box> boxs;
	// 地图
	int[][] cur_map;
	// 当前关卡
	int level;
	// 存放所有5的位置
	LinkedList<Point> holes;
	// 提示按空格重开
	JLabel restart;
	
	public ZPanel() {
		level = 0;
		cur_map = new int[12][12];
		boxs = new LinkedList<Box>();
		holes = new LinkedList<Point>();
		new My_map();	// 调用My_map的构造函数
		initPanel();
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(this);
		
		restart = new JLabel("按空格键重新开始当前关卡");
		restart.setForeground(Color.WHITE);
		restart.setFont(new Font("华文行楷", Font.BOLD, 20));
		this.add(restart);
	}
	
	private void initPanel() {
		// 深拷贝,初始化当前关卡地图
		for(int i = 0; i < My_map.maps[level].length; ++i)
			cur_map[i] = My_map.maps[level][i].clone();

		// 初始化玩家和箱子及箱子个数
		boxs.clear();
		holes.clear();
		for(int i = 0; i < cur_map.length; ++i) {
			for(int j = 0; j < cur_map[0].length; ++j) {
				if(cur_map[i][j] == 3) {
					player = new Player(new Point(i, j));
					last_pos = new Point(player.getPos());
				} else if(cur_map[i][j] == 4) {
					Box new_add = new Box(new Point(i, j));
					boxs.add(new_add);
				} else if(cur_map[i][j] == 5) {
					holes.add(new Point(i, j));
				}
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 画出当前状态图
		for(int i = 0; i < cur_map.length; ++i) {
			for(int j = 0; j < cur_map[0].length; ++j) {
				int cor_x = 20 + 30 * j;
				int cor_y = 20 + 30 * i;
				if(cur_map[i][j] == 1) {
					g.setColor(Color.WHITE);
					g.fillRect(cor_x, cor_y, 30, 30);
				} else if(cur_map[i][j] == 2) {
					g.setColor(Color.BLUE);
					g.fillRect(cor_x, cor_y, 30, 30);
				} else if(cur_map[i][j] == 3) {
					g.setColor(Color.BLUE);
					g.fillRect(cor_x, cor_y, 30, 30);
					g.setColor(Color.RED);
					g.setFont(new Font("华文行楷", Font.BOLD, 30));
					g.drawString("🐟", cor_x, cor_y + 30);
				} else if(cur_map[i][j] == 4) {
					g.setColor(Color.YELLOW);
					g.fillRect(cor_x, cor_y, 30, 30);
				} else if(cur_map[i][j] == 5) {
					g.setColor(Color.BLACK);
					g.fillRect(cor_x, cor_y, 30, 30);
				}
			}
		}
	}
	
	private void change_map() {
		Point cur_player_pos = player.getPos();	
		int x = cur_player_pos.x;
		int y = cur_player_pos.y;
		
		cur_map[last_pos.x][last_pos.y] = 2;
		
		if(cur_map[x][y] == 1) {
			player.setPos(last_pos.x, last_pos.y);
		} else if(cur_map[x][y] == 2) {
			cur_map[x][y] = 3;
			last_pos.setLocation(x, y);
		} else if(cur_map[x][y] == 4) {
			if(!move_box(x, y)) {
				player.setPos(last_pos.x, last_pos.y);
				cur_map[last_pos.x][last_pos.y] = 3;
			} else {
				last_pos.setLocation(x, y);
			}
		} else if(cur_map[x][y] == 5) {
			cur_map[x][y] = 3;
			last_pos.setLocation(x, y);
		}
		
		reshow_hole();
		repaint();
		check_win();
	}
	
	private boolean move_box(int x, int y) {
		String dir = get_dir();
//		System.out.println(dir);
		if(dir == "L" && (cur_map[x][y - 1] == 2 || cur_map[x][y - 1] == 5)) {
			cur_map[x][y - 1] = 4;
			cur_map[x][y] = 3;
			return true;
		} else if(dir == "D" && (cur_map[x + 1][y] == 2 || cur_map[x + 1][y] == 5)) {
			cur_map[x + 1][y] = 4;
			cur_map[x][y] = 3;
			return true;
		} else if(dir == "R" && (cur_map[x][y + 1] == 2 || cur_map[x][y + 1] == 5)) {
			cur_map[x][y + 1] = 4;
			cur_map[x][y] = 3;
			return true;
		} else if(dir == "U" && (cur_map[x - 1][y] == 2 || cur_map[x - 1][y] == 5)) {
			cur_map[x - 1][y] = 4;
			cur_map[x][y] = 3;
			return true;
		}
		return false;
	}

	private String get_dir() {
		int x1 = last_pos.x;
		int y1 = last_pos.y;
		int x2 = player.getX();
		int y2 = player.getY();
		if(x1 == x2) {
			if(y1 == y2 - 1)
				return "R";
			else
				return "L";
		} else {
			if(x1 == x2 - 1)
				return "D";
			else
				return "U";
		}
	}
	
	private void reshow_hole() {
		for(int i = 0; i < holes.size(); ++i) {
			Point pos = holes.get(i);
			if(cur_map[pos.x][pos.y] != 4 && cur_map[pos.x][pos.y] != 3)
				cur_map[pos.x][pos.y] = 5;
		}
	}
	
	private void check_win() {
		for(int i = 0; i < holes.size(); ++i) {
			Point pos = holes.get(i);
			if(cur_map[pos.x][pos.y] == 5 || cur_map[pos.x][pos.y] == 3)
				return;
		}
		win();
	}
	
	private void win() {
		if(level == 4)
			JOptionPane.showMessageDialog(this, "恭喜你完成推箱子任务！", "Bravo", JOptionPane.PLAIN_MESSAGE);
		
		level = (level + 1) % 5;
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
