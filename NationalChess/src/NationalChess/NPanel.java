package NationalChess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Chess.*;

public class NPanel extends JPanel implements MouseListener{
	// ����ͼƬ
	ImageIcon black_PAWN = new ImageIcon("BLACK_PAWN.png");
	ImageIcon black_ROOK = new ImageIcon("BLACK_ROOK.png");
	ImageIcon black_KNIGHT = new ImageIcon("BLACK_KNIGHT.png");
	ImageIcon black_BISHOP = new ImageIcon("BLACK_BISHOP.png");
	ImageIcon black_QUEEN = new ImageIcon("BLACK_QUEEN.png");
	ImageIcon black_KING = new ImageIcon("BLACK_KING.png");
	
	ImageIcon white_PAWN = new ImageIcon("WHITE_PAWN.png");
	ImageIcon white_ROOK = new ImageIcon("WHITE_ROOK.png");
	ImageIcon white_KNIGHT = new ImageIcon("WHITE_KNIGHT.png");
	ImageIcon white_BISHOP = new ImageIcon("WHITE_BISHOP.png");
	ImageIcon white_QUEEN = new ImageIcon("WHITE_QUEEN.png");
	ImageIcon white_KING = new ImageIcon("WHITE_KING.png");
	
	ImageIcon board = new ImageIcon("board.jpg");
	// ��ǰ��ͼ
	int[][] cur_map = new int[8][8];
	// ��������
	Pawn[] b_pawn, w_pawn;
	Rook b_rook, w_rook;
	Knight b_knight, w_knight;
	Bishop b_bishop, w_bishop;
	Queen b_queen, w_queen;
	King b_king, w_king;
	// �����������
	LinkedList<Chess> chesses = new LinkedList<Chess>();
	// �������ӣ�  -1-��  1-��
	int turn;
	// ��������ѡ��
	Object[] options = {"Rook", "Knight", "Bishop", "Queen"};
	
	public NPanel() {
		new My_map();
		// ����ͼƬ
		black_PAWN = change(black_PAWN, 0.2);
		black_ROOK = change(black_ROOK, 0.2);
		black_KNIGHT = change(black_KNIGHT, 0.2);
		black_BISHOP = change(black_BISHOP, 0.2);
		black_QUEEN = change(black_QUEEN, 0.2);
		black_KING = change(black_KING, 0.2);
		
		white_PAWN = change(white_PAWN, 0.2);
		white_ROOK = change(white_ROOK, 0.2);
		white_KNIGHT = change(white_KNIGHT, 0.2);
		white_BISHOP = change(white_BISHOP, 0.2);
		white_QUEEN = change(white_QUEEN, 0.2);
		white_KING = change(white_KING, 0.2);
		
		board = change(board, 1.2);
		
		initPanel();
		this.setFocusable(true);
		this.addMouseListener(this);
	}
	
	private void initPanel() {
		// ��������
		turn = -1;
		// ���
		for(int i = 0; i < 8; ++i)
			cur_map[i] = My_map.map[i].clone();
		
		// ��ʼ������
		chesses.clear();
		b_pawn = new Pawn[8];
		w_pawn = new Pawn[8];
		for(int i = 0; i < 8; ++i) {
			b_pawn[i] = new Pawn(1, new Point(1, i), "black");
			chesses.add(b_pawn[i]);
			w_pawn[i] = new Pawn(-1, new Point(6, i), "white");
			chesses.add(w_pawn[i]);
		}
		
		b_rook = new Rook(2, new Point(0, 0), "black");
		chesses.add(b_rook);
		b_rook = new Rook(2, new Point(0, 7), "black");
		chesses.add(b_rook);
		w_rook = new Rook(-2, new Point(7, 0), "white");
		chesses.add(w_rook);
		w_rook = new Rook(-2, new Point(7, 7), "white");
		chesses.add(w_rook);
		
		b_knight = new Knight(3, new Point(0, 1), "black");
		chesses.add(b_knight);
		b_knight = new Knight(3, new Point(0, 6), "black");
		chesses.add(b_knight);
		w_knight = new Knight(-3, new Point(7, 1), "white");
		chesses.add(w_knight);
		w_knight = new Knight(-3, new Point(7, 6), "white");
		chesses.add(w_knight);
		
		b_bishop = new Bishop(4, new Point(0, 2), "black");
		chesses.add(b_bishop);
		b_bishop = new Bishop(4, new Point(0, 5), "black");
		chesses.add(b_bishop);
		w_bishop = new Bishop(-4, new Point(7, 2), "white");
		chesses.add(w_bishop);
		w_bishop = new Bishop(-4, new Point(7, 5), "white");
		chesses.add(w_bishop);
		
		b_queen = new Queen(5, new Point(0, 3), "black");
		chesses.add(b_queen);
		w_queen = new Queen(-5, new Point(7, 3), "white");
		chesses.add(w_queen);
		
		b_king = new King(6, new Point(0, 4), "black");
		chesses.add(b_king);
		w_king = new King(-6, new Point(7, 4), "white");
		chesses.add(w_king);
	}
	
	// ����͸�����ʾѡ��
	private void draw_red(Graphics g, int r, int c) {
		for(int i = 0; i < chesses.size(); ++i) {
			Chess temp = chesses.get(i);
			int ox = temp.getX();
			int oy = temp.getY();
			int x = 56 + 63 * c;
			int y = 56 + 63 * r;
			if(ox == r && oy == c && temp.getIschoosed() == true) {
				g.setColor(new Color(255, 0, 0, 120));
				g.fillRect(x, y , 66, 66);
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		board.paintIcon(this, g, 10, 10);
		
		for(int i = 0; i < 8; ++i) {
			for(int j = 0; j < 8; ++j) {
				int x = 56 + 63 * j + j / 2;
				int y = 56 + 63 * i + i;
				if(cur_map[i][j] == 1) {
					black_PAWN.paintIcon(this, g, x, y);
				} else if(cur_map[i][j] == 2) {
					black_ROOK.paintIcon(this, g, x, y);
				} else if(cur_map[i][j] == 3) {
					black_KNIGHT.paintIcon(this, g, x, y);
				} else if(cur_map[i][j] == 4) {
					black_BISHOP.paintIcon(this, g, x, y);
				} else if(cur_map[i][j] == 5) {
					black_QUEEN.paintIcon(this, g, x, y);
				} else if(cur_map[i][j] == 6) {
					black_KING.paintIcon(this, g, x, y);
				}
				
				else if(cur_map[i][j] == -1) {
					white_PAWN.paintIcon(this, g, x, y);
				} else if(cur_map[i][j] == -2) {
					white_ROOK.paintIcon(this, g, x, y);
				} else if(cur_map[i][j] == -3) {
					white_KNIGHT.paintIcon(this, g, x, y);
				} else if(cur_map[i][j] == -4) {
					white_BISHOP.paintIcon(this, g, x, y);
				} else if(cur_map[i][j] == -5) {
					white_QUEEN.paintIcon(this, g, x, y);
				} else if(cur_map[i][j] == -6) {
					white_KING.paintIcon(this, g, x, y);
				}
				draw_red(g, i, j);
			}
		}
	}
	
	// ����ͼƬ�� timesΪ���ű���
	public ImageIcon change(ImageIcon image, double times) {
		int width = (int)(image.getIconWidth() * times);
		int height = (int)(image.getIconHeight() * times);
		Image img = image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		ImageIcon image2 = new ImageIcon(img);
		
		return image2;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
//		System.out.println("clicked");
		// TODO Auto-generated method stub
		Point pos = e.getPoint();
		if(e.getButton() == MouseEvent.BUTTON1) {
			int j = (pos.x - 56) / 63;
			int i = (pos.y - 56) / 63;
//			System.out.println(i + "  " + j);
			Chess chooseChess = find_choosed();
			if(chooseChess == null) {
				set_choose(i, j);
			} else {
				// ȫ��ѡ����Ϊfalse
				set_choose(-1,  -1);
				// ����������λ
				if(Math.abs(chooseChess.getAttribute()) == 6) {
					King temp = (King)chooseChess;
					if(change_king_with_rook(temp, i, j))
						return;
				}
				
				// �жϱ���������߷�(б�� + ��·��)
				if(Math.abs(chooseChess.getAttribute()) == 1) {
					Pawn temp = (Pawn)chooseChess;
					if(temp.pawn_eat_move(new Point(i, j)) && temp.confilct(cur_map[i][j]) == false) {
						if(pawn_can_eat(temp, i, j)) {
							// �жϱ�������
							if(i == 0 || i == 7) {
								imporve_pawn(chooseChess, i, j);
							}
						}
						return;
					}
				}
				
				//	����ǰ��������
				if((chooseChess.moveable(new Point(i, j)) == true && chooseChess.confilct(cur_map[i][j]) == false)) {
					// �ж�·����������������
					if(in_way(chooseChess, chooseChess.getX(), chooseChess.getY(), i, j))
						return;

					int x = chooseChess.getX();
					int y = chooseChess.getY();
					// �ָ����յ�����
					cur_map[x][y] = 0;
					// �ƶ�����λ����������Ե�
					if(cur_map[i][j] != 0)
						remove_killed(i, j);
					
					cur_map[i][j] = chooseChess.getAttribute();
					// �ı�����λ��
					chooseChess.setPos(i, j);
					
					// �����������Ƿ��ƶ���
					if(Math.abs(chooseChess.getAttribute()) == 2) {
						Rook temp = (Rook)chooseChess;
						temp.setIsmoved(true);
					}
					if(Math.abs(chooseChess.getAttribute()) == 6) {
						King temp = (King)chooseChess;
						temp.setIsmoved(true);
					}
					
					// ȥ����·������
					set_pawn_move_two_false(i, j);
					
					// ����ǰ���ػ�
					repaint();
					// �жϱ�������
					if(Math.abs(chooseChess.getAttribute()) == 1 && (i == 0 || i == 7)) {
						imporve_pawn(chooseChess, i, j);
					}
					
					// ������Ȩ
					turn = -turn;
					check_win();
				}
			}
			repaint();
		}
	}
	
	private void set_choose(int x, int y) {
		for(int i = 0; i < chesses.size(); ++i) {
			Chess temp = chesses.get(i);
			// ��������
			if(turn * temp.getAttribute() < 0)
				continue;
			
			int ox = temp.getX();
			int oy = temp.getY();
			if(ox == x && oy == y)
				temp.setIschoosed(true);
			else
				temp.setIschoosed(false);
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
	
	private Chess find_chess_by_pos(int r, int c) {
		for(int i = 0; i < chesses.size(); ++i) {
			Chess temp = chesses.get(i);
			int x = temp.getX();
			int y = temp.getY();
			if(x == r && y == c)
				return chesses.get(i);
		}
		return null;
	}
	
	private void set_pawn_move_two_false(int r, int c) {
		for(int i = 0; i < chesses.size(); ++i) {
			Chess temp = chesses.get(i);
			if(Math.abs(temp.getAttribute()) == 1 && !(temp.getX() == r && temp.getY() == c)) {
				Pawn temp2 = (Pawn)temp;
				temp2.setLast_move_two(false);
			}
		}
	}
	
	// �ж�·����������������
	private boolean in_way(Chess chooseChess, int x1, int y1, int x2, int y2) {
		if(Math.abs(chooseChess.getAttribute()) == 3)
			return false;
		
		if(Math.abs(chooseChess.getAttribute()) == 1 && Math.abs(x1 - x2) == 1 && cur_map[x2][y2] != 0)
			return true;
		
		for(int i = 0; i < chesses.size(); ++i) {
			Chess temp = chesses.get(i);
			int x3 = temp.getX();
			int y3 = temp.getY();
			// ȥ�������˵���ж��Ƿ�����һ����3���м�
			if(!(x1 == x3 && y1 == y3) && !(x2 == x3 && y2 == y3))
				if(((y3 - y1) * (x2 - x3)) == ((y2 - y3) * (x3 - x1)))
					if(x3 <= Math.max(x1, x2) && x3 >= Math.min(x1, x2) && y3 <= Math.max(y1, y2) && y3 >= Math.min(y1, y2))
						return true;
		}
		return false;
	}
	
	private void remove_killed(int x, int y) {
		for(int i = 0; i < chesses.size(); ++i) {
			Chess temp = chesses.get(i);
			int ox = temp.getX();
			int oy = temp.getY();
			if(ox == x && oy == y) {
				chesses.remove(i);
				return;
			}	
		}
	}
	
	// Ӯ��������ɱ���Է�����
	private void check_win() {
		int is_b_king_find = 0;
		int is_w_king_find = 0;
		for(int i = 0; i < chesses.size(); ++i) {
			Chess temp = chesses.get(i);
			if(temp.getAttribute() == 6)
				is_b_king_find = 1;
			if(temp.getAttribute() == -6)
				is_w_king_find = 1;
		}
		
		if(is_b_king_find == 0) {
			JOptionPane.showMessageDialog(this, "��ϲ�׷�ʤ����", "Bravo", JOptionPane.PLAIN_MESSAGE);
			initPanel();
			return;
		} else if(is_w_king_find == 0) {
			JOptionPane.showMessageDialog(this, "��ϲ�ڷ�ʤ����", "Bravo", JOptionPane.PLAIN_MESSAGE);
			initPanel();
			return;
		}
	}
	
	// ������λ
	private boolean change_king_with_rook(King temp, int i, int j) {
		int ky = temp.getY();
		if(temp.confilct(cur_map[i][j]) == false && temp.getIsmoved() == false && temp.change_with_rook(i, j)) {
			Rook temp2;
			if(j < ky) {
				temp2 = (Rook)find_chess_by_pos(i, 0);
				if(Math.abs(temp2.getAttribute()) == 2 && temp2.confilct(cur_map[i][j + 1]) == false && temp2.getIsmoved() == false && temp2.change_with_king(i, j + 1)) {
					if(cur_map[i][j - 1] == 0) {
						cur_map[temp.getX()][temp.getY()] = 0;
						cur_map[temp2.getX()][temp2.getY()] = 0;
						
						cur_map[i][j] = temp.getAttribute();
						temp.setPos(i, j);
						cur_map[i][j + 1] = temp2.getAttribute();
						temp2.setPos(i, j + 1);
						
						// ������Ȩ
						turn = -turn;
						
						repaint();
						check_win();
						return true;
					}
				}
			} else {
				temp2 = (Rook)find_chess_by_pos(i, 7);
				if(Math.abs(temp2.getAttribute()) == 2 && temp2.confilct(cur_map[i][j - 1]) == false && temp2.getIsmoved() == false && temp2.change_with_king(i, j - 1)) {
					cur_map[temp.getX()][temp.getY()] = 0;
					cur_map[temp2.getX()][temp2.getY()] = 0;
					
					cur_map[i][j] = temp.getAttribute();
					temp.setPos(i, j);
					cur_map[i][j - 1] = temp2.getAttribute();
					temp2.setPos(i, j - 1);
					
					// ������Ȩ
					turn = -turn;
					
					repaint();
					check_win();
					return true;
				}
			}
		}
		return false;
	}
	
	// �жϱ���б��
	private boolean pawn_can_eat(Pawn temp, int i, int j) {
		// ���б�Խǲ�Ϊ�գ� ��ֱ�ӳ�
		if(cur_map[i][j] != 0) {
			// �ָ����յ�����
			cur_map[temp.getX()][temp.getY()] = 0;
			// �ƶ�����λ����������Ե�
			remove_killed(i, j);
			// �ı��Ӧ����
			cur_map[i][j] = temp.getAttribute();
			temp.setPos(i, j);
			
			turn = -turn;
			
			repaint();
			check_win();
			return true;
			
		} else if(cur_map[i][j] == 0 && temp.getAttribute() == -1 && cur_map[i + 1][j] == 1) {
			//б�Խ����ӣ��жϹ�·��
			Pawn temp2;
			temp2 = (Pawn)find_chess_by_pos(i + 1, j);
//			System.out.println(temp2.getAttribute());
//			System.out.println(temp2.getLast_move_two());
			if(temp2.getLast_move_two() == true) {
				change_passed_pawn(temp, temp2, i, j, 1);
				return true;
			}
		} else if(cur_map[i][j] == 0 && temp.getAttribute() == 1 && cur_map[i - 1][j] == -1) {
			//б�Խ����ӣ��жϹ�·��
			Pawn temp2;
			temp2 = (Pawn)find_chess_by_pos(i - 1, j);
			if(temp2.getLast_move_two() == true) {
				change_passed_pawn(temp, temp2, i, j, -1);
				return true;
			}
		}
			
		return false;
	}
	
	private void change_passed_pawn(Pawn temp, Pawn temp2, int i, int j, int add) {
		// �ָ����յ�����
		cur_map[temp.getX()][temp.getY()] = 0;
		cur_map[temp2.getX()][temp2.getY()] = 0;
		// �ƶ�����λ����������Ե�
		remove_killed(i + add, j);
		// �ı��Ӧ����
		cur_map[i][j] = temp.getAttribute();
		temp.setPos(i, j);
		
		turn = -turn;
		
		repaint();
		check_win();
	}
	
	
	// ��������
	private void imporve_pawn(Chess chooseChess, int i, int j) {
		chesses.remove(chooseChess);
		int value = JOptionPane.showOptionDialog(this, "��ѡ�񽫱�����ɣ�", "��������", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		switch(value) {
			case 0: {
				if(i == 0)
					chooseChess = new Rook(-2, new Point(i, j), "white");
				else if(i == 7)
					chooseChess = new Rook(2, new Point(i, j), "black");
				break;
			}
			case 1: {
				if(i == 0)
					chooseChess = new Knight(-3, new Point(i, j), "white");
				else if(i == 7)
					chooseChess = new Knight(3, new Point(i, j), "black");
				break;
			}	
			case 2: {
				if(i == 0)
					chooseChess = new Bishop(-4, new Point(i, j), "white");
				else if(i == 7)
					chooseChess = new Bishop(4, new Point(i, j), "black");
				break;
			}
			case 3: {
				if(i == 0)
					chooseChess = new Queen(-5, new Point(i, j), "white");
				else if(i == 7)
					chooseChess = new Queen(5, new Point(i, j), "black");
				break;
			}
			
		}
		chesses.add(chooseChess);
		cur_map[i][j] = chooseChess.getAttribute();
		repaint();
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
