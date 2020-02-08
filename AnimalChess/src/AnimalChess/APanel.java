package AnimalChess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Chess.*;

public class APanel extends JPanel implements MouseListener{
	// 导入图片
	ImageIcon red_mouse = new ImageIcon("red_mouse.png");
	ImageIcon red_cat = new ImageIcon("red_cat.png");
	ImageIcon red_dog = new ImageIcon("red_dog.png");
	ImageIcon red_wolf = new ImageIcon("red_wolf.png");
	ImageIcon red_leopard = new ImageIcon("red_leopard.png");
	ImageIcon red_tiger = new ImageIcon("red_tiger.png");
	ImageIcon red_lion = new ImageIcon("red_lion.png");
	ImageIcon red_elephant = new ImageIcon("red_elephant.png");
	
	ImageIcon black_mouse = new ImageIcon("black_mouse.png");
	ImageIcon black_cat = new ImageIcon("black_cat.png");
	ImageIcon black_dog = new ImageIcon("black_dog.png");
	ImageIcon black_wolf = new ImageIcon("black_wolf.png");
	ImageIcon black_leopard = new ImageIcon("black_leopard.png");
	ImageIcon black_tiger = new ImageIcon("black_tiger.png");
	ImageIcon black_lion = new ImageIcon("black_lion.png");
	ImageIcon black_elephant = new ImageIcon("black_elephant.png");
	
	ImageIcon map = new ImageIcon("map.png");
	// 当前地图
	int[][] cur_map;
	// 定义棋子
	Mouse r_mouse, b_mouse;
	Cat r_cat, b_cat;
	Dog r_dog, b_dog;
	Wolf r_wolf, b_wolf;
	Leopard r_leopard, b_leopard;
	Tiger r_tiger, b_tiger;
	Lion r_lion, b_lion;
	Elephant r_elephant, b_elephant;
	// 存放所有棋子
	LinkedList<Chess> chesses;
	// 轮流下子：  -1-红  1-黑
	int turn;
	
	public APanel() {
		new My_map();
		cur_map = new int[9][7];
		chesses = new LinkedList<Chess>();
		initPanel();
		this.setFocusable(true);
		this.addMouseListener(this);
	}
	
	private void initPanel() {
		// 红方先走
		turn = -1;
		// 深拷贝
		for(int i = 0; i < 9; ++i)
			cur_map[i] = My_map.map[i].clone();
		
		// 初始化棋子
		chesses.clear();
		b_mouse = new Mouse(new Point(2, 0), "black");
		chesses.add(b_mouse);
		r_mouse = new Mouse(new Point(6, 6), "red");
		chesses.add(r_mouse);
		
		b_cat = new Cat(new Point(1, 5), "black");
		chesses.add(b_cat);
		r_cat = new Cat(new Point(7, 1), "red");
		chesses.add(r_cat);
		
		b_dog = new Dog(new Point(1, 1), "black");
		chesses.add(b_dog);
		r_dog  = new Dog(new Point(7, 5), "red");
		chesses.add(r_dog);
		
		b_wolf = new Wolf(new Point(2, 4), "black");
		chesses.add(b_wolf);
		r_wolf = new Wolf(new Point(6, 2), "red");
		chesses.add(r_wolf);
		
		b_leopard = new Leopard(new Point(2, 2), "black");
		chesses.add(b_leopard);
		r_leopard = new Leopard(new Point(6, 4), "red");
		chesses.add(r_leopard);
		
		b_tiger = new Tiger(new Point(0, 6), "black");
		chesses.add(b_tiger);
		r_tiger = new Tiger(new Point(8, 0), "red");
		chesses.add(r_tiger);
		
		b_lion = new Lion(new Point(0, 0), "black");
		chesses.add(b_lion);
		r_lion = new Lion(new Point(8, 6), "red");
		chesses.add(r_lion);
		
		b_elephant = new Elephant(new Point(2, 6), "black");
		chesses.add(b_elephant);
		r_elephant = new Elephant(new Point(6, 0), "red");
		chesses.add(r_elephant);
	}
	
	// 画上透明框表示选中
	private void draw_red(Graphics g, Chess cur_chess, int x, int y) {
		if(cur_chess.getIschoosed() == true) {
			int j = (y - 16) / 70;	//做一些修正

			g.setColor(new Color(255, 0, 0, 120));
			g.fillRect(x, y - j / 2, 70, 70);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 画出当前地图
		map.paintIcon(this, g, 10, 10);
		for(int i = 0; i < 9; ++i) {
			for(int j = 0; j < 7; ++j) {
				int x = 17 + 70 * j;
				int y = 16 + 70 * i;
				if(cur_map[i][j] == 1) {
					black_mouse.paintIcon(this, g, x, y);
					draw_red(g, b_mouse, x - 2, y);
				} else if(cur_map[i][j] == 2) {
					black_cat.paintIcon(this, g, x, y);
					draw_red(g, b_cat, x - 2, y);
				} else if(cur_map[i][j] == 3) {
					black_dog.paintIcon(this, g, x, y);
					draw_red(g, b_dog, x - 2, y);
				} else if(cur_map[i][j] == 4) {
					black_wolf.paintIcon(this, g, x, y);
					draw_red(g, b_wolf, x - 2, y);
				} else if(cur_map[i][j] == 5) {
					black_leopard.paintIcon(this, g, x, y);
					draw_red(g, b_leopard, x - 2, y);
				} else if(cur_map[i][j] == 6) {
					black_tiger.paintIcon(this, g, x, y);
					draw_red(g, b_tiger, x - 2, y);
				} else if(cur_map[i][j] == 7) {
					black_lion.paintIcon(this, g, x, y);
					draw_red(g, b_lion, x - 2, y);
				} else if(cur_map[i][j] == 8) {
					black_elephant.paintIcon(this, g, x, y);
					draw_red(g, b_elephant, x - 2, y);
				}
				
				else if(cur_map[i][j] == -1) {
					red_mouse.paintIcon(this, g, x, y);
					draw_red(g, r_mouse, x - 2, y);
				} else if(cur_map[i][j] == -2) {
					red_cat.paintIcon(this, g, x, y);
					draw_red(g, r_cat, x - 2, y);
				} else if(cur_map[i][j] == -3) {
					red_dog.paintIcon(this, g, x, y);
					draw_red(g, r_dog, x - 2, y);
				} else if(cur_map[i][j] == -4) {
					red_wolf.paintIcon(this, g, x, y);
					draw_red(g, r_wolf, x - 2, y);
				} else if(cur_map[i][j] == -5) {
					red_leopard.paintIcon(this, g, x, y);
					draw_red(g, r_leopard, x - 2, y);
				} else if(cur_map[i][j] == -6) {
					red_tiger.paintIcon(this, g, x, y);
					draw_red(g, r_tiger, x - 2, y);
				} else if(cur_map[i][j] == -7) {
					red_lion.paintIcon(this, g, x, y);
					draw_red(g, r_lion, x - 2, y);
				} else if(cur_map[i][j] == -8) {
					red_elephant.paintIcon(this, g, x, y);
					draw_red(g, r_elephant, x - 2, y);
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Point pos = e.getPoint();
		if(e.getButton() == MouseEvent.BUTTON1) {
//			System.out.println("clicked");
			int j = (pos.x - 17) / 70;
			int i = (pos.y - 16) / 70;
			Chess chooseChess = find_choosed();
			if(chooseChess == null) {
				if(cur_map[i][j] == 1)
					set_choose(1);
				else if(cur_map[i][j] == 2)
					set_choose(2);
				else if(cur_map[i][j] == 3)
					set_choose(3);
				else if(cur_map[i][j] == 4)
					set_choose(4);
				else if(cur_map[i][j] == 5)
					set_choose(5);
				else if(cur_map[i][j] == 6)
					set_choose(6);
				else if(cur_map[i][j] == 7)
					set_choose(7);
				else if(cur_map[i][j] == 8)
					set_choose(8);
				
				else if(cur_map[i][j] == -1)
					set_choose(-1);
				else if(cur_map[i][j] == -2)
					set_choose(-2);
				else if(cur_map[i][j] == -3)
					set_choose(-3);
				else if(cur_map[i][j] == -4)
					set_choose(-4);
				else if(cur_map[i][j] == -5)
					set_choose(-5);
				else if(cur_map[i][j] == -6)
					set_choose(-6);
				else if(cur_map[i][j] == -7)
					set_choose(-7);
				else if(cur_map[i][j] == -8)
					set_choose(-8);
			}
			else {
				set_choose(0);
				System.out.println(chooseChess.moveable(new Point(i, j)));
				System.out.println(chooseChess.confilct(cur_map[i][j]));
				if(chooseChess.moveable(new Point(i, j)) == true && (chooseChess.confilct(cur_map[i][j]) == false || chooseChess.istraped_enemy(cur_map[i][j], i, j))) {
					// 水中老鼠不能吃岸上的象和鼠, 但是水中的鼠可以互吃
					if(chooseChess.getInriver() == true && ((cur_map[i][j] != 0 && cur_map[i][j] != 11) && !(My_map.map[i][j] == 11 && (cur_map[i][j] == -1 || cur_map[i][j] == 1))))
						return;
					// 岸上的老鼠不能吃掉水中的老鼠
					if(chooseChess.getAttribute() == 1 && chooseChess.getInriver() == false && cur_map[i][j] == -1 && My_map.map[i][j] == 11)
						return;
					if(chooseChess.getAttribute() == -1 && chooseChess.getInriver() == false && cur_map[i][j] == 1 && My_map.map[i][j] == 11)
						return;
					// 老虎和狮子跳跃时判断路线中有无老鼠
					int x = chooseChess.getX();
					int y = chooseChess.getY();
					if(Math.abs(chooseChess.getAttribute()) == 6 || Math.abs(chooseChess.getAttribute()) == 7) {
						if(x == i - 4 && !no_mouse_inway(i, j, 4))
							return;
						else if(x == i + 4 && !no_mouse_inway(x, j, 4))
							return;
						else if(y == j - 3 && !no_mouse_inway(i, j, 3))
							return;
						else if(y == j + 3 && !no_mouse_inway(i, y, 3))
							return;
					}
					
					// 恢复原来的属性
					if(My_map.map[x][y] == 11 || Math.abs(My_map.map[x][y]) == 9 || Math.abs(My_map.map[x][y]) == 10)
						cur_map[x][y] = My_map.map[x][y];
					else
						cur_map[x][y] = 0;
					
					if(cur_map[i][j] != 0 && cur_map[i][j] != 9 && cur_map[i][j] != 10 && cur_map[i][j] != -9 && cur_map[i][j] != -10)
						remove_killed(cur_map[i][j]);
					cur_map[i][j] = chooseChess.getAttribute();
					// 判断动物是否在水中
					if(My_map.map[i][j] == 11)
						chooseChess.setInriver(true);
					else 
						chooseChess.setInriver(false);
					
					chooseChess.setPos(i, j);
					chooseChess.setIschoosed(false);
					
					// 交换棋权
					turn = -turn;
					
					repaint();
					check_win();
				}
			}
			repaint();
		}
	}
	
	private void set_choose(int a) {
		if(turn * a < 0)
			return;
		
		for(int i = 0; i < chesses.size(); ++i) {
			Chess temp = chesses.get(i);
			if(temp.getAttribute() != a)
				temp.setIschoosed(false);
			else
				temp.setIschoosed(true);
		}
	}
	
	private Chess find_choosed() {
		for(int i = 0; i < chesses.size(); ++i) {
			Chess temp = chesses.get(i);
			if(temp.getIschoosed() == true)
				return chesses.get(i);
		}
		return null;
	}
	
	private boolean no_mouse_inway(int x, int y, int times) {
		if(times == 4) {
			for(int i = 1; i < times; ++i)
				if(cur_map[x - i][y] != 11)
					return false;
		} else if(times == 3) {
			for(int i = 1; i < times; ++i)
				if(cur_map[x][y - i] != 11)
					return false;
		}
		
		return true;
	}
	
	private void remove_killed(int attr) {
		for(int i = 0; i < chesses.size(); ++i) {
			Chess temp = chesses.get(i);
			if(temp.getAttribute() == attr) {
				chesses.remove(i);
				return;
			}	
		}
	}
	
	// 赢的条件：1-占领对方巢穴    2-吃掉对方所有棋子
	private void check_win() {
		if(cur_map[0][3] != 10) {
			JOptionPane.showMessageDialog(this, "恭喜红方占领黑方巢穴，红方胜出！", "Bravo", JOptionPane.PLAIN_MESSAGE);
			initPanel();
			return;
		} else if(cur_map[8][3] != -10) {
			JOptionPane.showMessageDialog(this, "恭喜黑方占领红方巢穴，黑方胜出！", "Bravo", JOptionPane.PLAIN_MESSAGE);
			initPanel();
			return;
		}
		
		int r_count = 0, b_count = 0;
		for(int i = 0; i < chesses.size(); ++i) {
			Chess temp = chesses.get(i);
			if(temp.getSide() == "red")
				r_count++;
			else if(temp.getSide() == "black")
				b_count++;
		}

		if(b_count == 0) {
			JOptionPane.showMessageDialog(this, "恭喜红方吃掉所有黑子，红方胜出！", "Bravo", JOptionPane.PLAIN_MESSAGE);
			initPanel();
			return;
		} else if(r_count == 0) {
			JOptionPane.showMessageDialog(this, "恭喜黑方吃掉所有红子，黑方胜出！", "Bravo", JOptionPane.PLAIN_MESSAGE);
			initPanel();
			return;
		}
			
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
