package Gobang;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener{
	/**
	 * 棋盘
	 * 五子棋一共有169个格子,在棋盘上,横纵线交叉形成了225个交叉点为对弈时的落子点。
	 */
	// 划线位置
	private int[][] line_begin_pos;
	private int[][] line_end_pos;
	// 交点位置, 用来判断鼠标点击到哪个交点
	private Point[][] inter;
	// 该位置是否有棋子   0-无； 1-白； -1-黑
	private int[][] isChess;
	// 存放棋子
	private LinkedList<Chess> chesses;
	// 轮到谁下   0-白子； 1-黑子
	private int turn;
	// 重新开始、悔棋按钮
	JButton start;
	JButton regret;
	
	public Board() {
		// 初始化棋盘
		line_begin_pos = new int[][] {
			{12, 12}, {37, 12}, {62, 12}, {87, 12}, {112, 12}, 
			{137, 12}, {162, 12}, {187, 12}, {212, 12}, {237, 12}, 
			{262, 12}, {287, 12}, {312, 12}, {337, 12}, {362, 12},
		};
		line_end_pos = new int[][] {
			{12, 362}, {37, 362}, {62, 362}, {87, 362}, {112, 362},
			{137, 362}, {162, 362}, {187, 362}, {212, 362}, {237, 362},
			{262, 362}, {287, 362}, {312, 362}, {337, 362}, {362, 362}
		};
		// 初始化交点位置
		inter = new Point[15][15];
		for(int i = 0; i < 15; ++i) {
			for(int j = 0; j < 15; ++j) {
				int x = 12 + 25 * i;
				int y = 12 + 25 * j;
				inter[i][j] = new Point(x, y);
			}
		}
		// 初始化按钮
		start = new JButton("开始");
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				initBoard();
				repaint();
			}
		});
		regret = new JButton("悔棋");
		regret.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!chesses.isEmpty()) {
					Chess to_remove = chesses.getLast();
					Point to_delete = to_remove.getCor();
					isChess[to_delete.x][to_delete.y] = 0;
					chesses.remove(chesses.size() - 1);
					turn = 1 - turn;
					repaint();
				}
			}
		});
		
		isChess = new int[15][15];
		chesses = new LinkedList<Chess>();
		initBoard();
		this.addMouseListener(this);
	}
	
	private void initBoard() {
		chesses.clear();
		for(int i = 0; i < 15; ++i)
			for(int j = 0; j < 15; ++j)
				isChess[i][j] = 0;
		turn = 0;
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 画出棋盘
		g.setColor(Color.BLACK);
		for(int i = 0; i < 15; ++i) {
			// 竖线
			g.drawLine(line_begin_pos[i][0], line_begin_pos[i][1], line_end_pos[i][0], line_end_pos[i][1]);
			// 横线
			g.drawLine(line_begin_pos[i][1], line_begin_pos[i][0], line_end_pos[i][1], line_end_pos[i][0]);
		}
		
		// 画棋子
		for(int i = 0; i < chesses.size(); ++i) {
			Chess temp = chesses.get(i);
			Point p = temp.getPos();
			if(temp.getAttr() == "white")
				g.setColor(Color.WHITE);
			else if(temp.getAttr() == "black")
				g.setColor(Color.BLACK);
			
			g.fillOval(p.x - 12, p.y - 12, 24, 24);
		}
		// 画两个按钮
		start.setFont(new Font("微软雅黑", Font.BOLD, 20));
		start.setBounds(380, 150, 80, 40);
		this.add(start);
		start.addMouseListener(this);
		
		regret.setFont(new Font("微软雅黑", Font.BOLD, 20));
		regret.setBounds(380, 200, 80, 40);
		this.add(regret);
		regret.addMouseListener(this);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		Point pos = e.getPoint();
		// 判断鼠标点击位置是否在某个交点附近
		for(int i = 0; i < 15; ++i) {
			for(int j = 0; j < 15; ++j) {
				if(Math.abs(pos.x - inter[i][j].x) <= 10 && Math.abs(pos.y - inter[i][j].y) <= 10) {
					if(isChess[i][j] == 0) {
						if(turn == 0)
							isChess[i][j] = 1;
						else if(turn == 1)
							isChess[i][j] = -1;
						
						putChess(inter[i][j]);
						repaint();
						judge(isChess[i][j], i, j);	//只用检查刚刚下的棋子
					}
					break;
				}
			}
		}
	}
	// 落子
	private void putChess(Point p) {
		if(turn == 0) {
			Chess add_chess = new Chess(p, "white");
			chesses.add(add_chess);
			turn = 1;
		} else {
			Chess add_chess = new Chess(p, "black");
			chesses.add(add_chess);
			turn = 0;
		}
	}
	private void show_win(int attr) {
		if(attr == 1)
			JOptionPane.showMessageDialog(this, "恭喜白棋获胜！", "Brova", JOptionPane.PLAIN_MESSAGE);
		else 
			JOptionPane.showMessageDialog(this, "恭喜黑棋获胜！", "Brova", JOptionPane.PLAIN_MESSAGE);
		initBoard();
	}
	
	// 判断输赢
	private void judge(int attr, int r, int c) {
		// 搜索以落子点为中心，边长为5的方阵
		for(int i = r - 2; i <= r + 2; ++i) {
			if(i >= 0 && i <= 14) {
				for(int j = c - 2; j <= c + 2; ++j) {
					if(j >= 0 && j <= 14) {
						if(isChess[i][j] == attr && search_win(isChess[i][j], i, j)) {
							show_win(isChess[i][j]);
							return;
						}		
					}
				}
			}
		}
		
	}
	
	private boolean search_win(int attr, int r, int c) {
		// 做八个方向的搜索
		if(left(attr, r, c, 0))
			return true;
		if(up_left(attr, r, c, 0))
			return true;
		if(up(attr, r, c, 0))
			return true;
		if(up_right(attr, r, c, 0))
			return true;
		if(right(attr, r, c, 0))
			return true;
		if(down_right(attr, r, c, 0))
			return true;
		if(down(attr, r, c, 0))
			return true;
		if(down_left(attr, r, c, 0))
			return true;

		return false;
	}
	
	private boolean left(int attr, int r, int c, int cur_count) {
		if(cur_count == 5)
			return true;
		
		if(c > 0 && isChess[r][c] == attr)
			return left(attr, r, c - 1, cur_count + 1);
		else 
			return false;
	}
	
	private boolean up_left(int attr, int r, int c, int cur_count) {
		if(cur_count == 5)
			return true;
		
		if(r > 0 && c > 0 && isChess[r][c] == attr)
			return up_left(attr, r - 1, c - 1, cur_count + 1);
		else 
			return false;
	}
	
	private boolean up(int attr, int r, int c, int cur_count) {
		if(cur_count == 5)
			return true;
		
		if(r > 0 && isChess[r][c] == attr)
			return up(attr, r - 1, c, cur_count + 1);
		else 
			return false;
	}
	
	private boolean up_right(int attr, int r, int c, int cur_count) {
		if(cur_count == 5)
			return true;
		
		if(r > 0 && c < 14 && isChess[r][c] == attr)
			return up_right(attr, r - 1, c + 1, cur_count + 1);
		else 
			return false;
	}
	
	private boolean right(int attr, int r, int c, int cur_count) {
		if(cur_count == 5)
			return true;
		
		if(c < 14 && isChess[r][c] == attr)
			return right(attr, r, c + 1, cur_count + 1);
		else 
			return false;
	}
	
	private boolean down_right(int attr, int r, int c, int cur_count) {
		if(cur_count == 5)
			return true;
		
		if(r < 14 && c < 14 && isChess[r][c] == attr)
			return down_right(attr, r + 1, c + 1, cur_count + 1);
		else 
			return false;
	}
	
	private boolean down_left(int attr, int r, int c, int cur_count) {
		if(cur_count == 5)
			return true;
		
		if(r < 14 && c > 0 && isChess[r][c] == attr)
			return down_left(attr, r + 1, c - 1, cur_count + 1);
		else 
			return false;
	}
	
	private boolean down(int attr, int r, int c, int cur_count) {
		if(cur_count == 5)
			return true;
		
		if(r < 14 && isChess[r][c] == attr)
			return down(attr, r + 1, c, cur_count + 1);
		else 
			return false;
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
