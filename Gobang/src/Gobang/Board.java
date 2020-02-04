package Gobang;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener{
	/**
	 * ����
	 * ������һ����169������,��������,�����߽����γ���225�������Ϊ����ʱ�����ӵ㡣
	 */
	// ����λ��
	private int[][] line_begin_pos;
	private int[][] line_end_pos;
	// ����λ��, �����ж���������ĸ�����
	private Point[][] inter;
	// ��λ���Ƿ�������   0-�ޣ� 1-�ף� -1-��
	private int[][] isChess;
	// �������
	private LinkedList<Chess> chesses;
	// �ֵ�˭��   0-���ӣ� 1-����
	private int turn;
	// ���¿�ʼ�����尴ť
	JButton start;
	JButton regret;
	
	public Board() {
		// ��ʼ������
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
		// ��ʼ������λ��
		inter = new Point[15][15];
		for(int i = 0; i < 15; ++i) {
			for(int j = 0; j < 15; ++j) {
				int x = 12 + 25 * i;
				int y = 12 + 25 * j;
				inter[i][j] = new Point(x, y);
			}
		}
		// ��ʼ����ť
		start = new JButton("��ʼ");
		regret = new JButton("����");
		
		chesses = new LinkedList<Chess>();
		initBoard();
		this.addMouseListener(this);
	}
	
	private void initBoard() {
		chesses.clear();
		isChess = new int[15][15];
		turn = 0;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// ��������
		g.setColor(Color.BLACK);
		for(int i = 0; i < 15; ++i) {
			// ����
			g.drawLine(line_begin_pos[i][0], line_begin_pos[i][1], line_end_pos[i][0], line_end_pos[i][1]);
			// ����
			g.drawLine(line_begin_pos[i][1], line_begin_pos[i][0], line_end_pos[i][1], line_end_pos[i][0]);
		}
		
		// ������
		for(int i = 0; i < chesses.size(); ++i) {
			Chess temp = chesses.get(i);
			Point p = temp.getPos();
			if(temp.getAttr() == "white")
				g.setColor(Color.WHITE);
			else if(temp.getAttr() == "black")
				g.setColor(Color.BLACK);
			
			g.fillOval(p.x - 12, p.y - 12, 24, 24);
		}
		// ��������ť
		start.setFont(new Font("΢���ź�", Font.BOLD, 20));
		start.setBounds(380, 150, 80, 40);
		this.add(start);
		start.addMouseListener(this);
		
		regret.setFont(new Font("΢���ź�", Font.BOLD, 20));
		regret.setBounds(380, 200, 80, 40);
		this.add(regret);
		regret.addMouseListener(this);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("click");
		if(start.equals(e.getSource())) {
			initBoard();
			repaint();
		}
		else if(regret.equals(e.getSource())) {
			if(!chesses.isEmpty()) {
				chesses.remove(chesses.size() - 1);
				repaint();
			}	
		} else {
			Point pos = e.getPoint();
			// �ж������λ���Ƿ���ĳ�����㸽��
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
							judge(i, j);	//ֻ�ü��ո��µ�����
						}
						break;
					}
				}
			}
		}
	}
	// ����
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
			JOptionPane.showMessageDialog(this, "��ϲ�����ʤ��", "Brova", JOptionPane.PLAIN_MESSAGE);
		else 
			JOptionPane.showMessageDialog(this, "��ϲ�����ʤ��", "Brova", JOptionPane.PLAIN_MESSAGE);
		initBoard();
	}
	
	// �ж���Ӯ
	private void judge(int r, int c) {
		if(search_win(isChess[r][c], r, c))
			show_win(isChess[r][c]);
	}
	
	private boolean search_win(int attr, int r, int c) {
		// ���˸����������
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
		if(cur_count >= 5)
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
