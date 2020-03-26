package plus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ZPanel extends JPanel implements KeyListener{
	// 导入图片
	ImageIcon Icon_2 = new ImageIcon("2.png");
	ImageIcon Icon_4 = new ImageIcon("4.png");
	ImageIcon Icon_8 = new ImageIcon("8.png");
	ImageIcon Icon_16 = new ImageIcon("16.png");
	ImageIcon Icon_32 = new ImageIcon("32.png");
	ImageIcon Icon_64 = new ImageIcon("64.png");
	ImageIcon Icon_128 = new ImageIcon("128.png");
	ImageIcon Icon_256 = new ImageIcon("256.png");
	ImageIcon Icon_512 = new ImageIcon("512.png");
	ImageIcon Icon_1024 = new ImageIcon("1024.png");
	ImageIcon Icon_2048 = new ImageIcon("2048.png");
	ImageIcon bestIcon = new ImageIcon("best.png");
	ImageIcon scoreIcon = new ImageIcon("score.png");
	ImageIcon panelIcon = new ImageIcon("panel.png");
	// 游戏数组
	int ROW = 4;
	int COL = 4;
//	int[][] datas = new int[][] {{2, 4, 8, 0}, {0, 8, 16, 8}, {0, 8, 2, 64}, {2, 0, 2, 2}};
	int[][] datas = new int[ROW][COL];
	int[] pos_x = new int[ROW];
	int[] pos_y = new int[COL];
	// 随机数
	Random random = new Random();
	// 当前得分
	int score = 0;
	// The Best
	int best = 0;
	// 判断游戏输赢
	int is_win = 0;
	int is_lose = 0;
	
	public ZPanel() {
		initZPanel();
		initPos();
		this.setFocusable(true);
		this.addKeyListener(this);
	}
	
	// 初始化位置
	private void initPos() {
		pos_x[0] = 30;
		pos_x[1] = 100;
		pos_x[2] = 170;
		pos_x[3] = 240;
		
		pos_y[0] = 170;
		pos_y[1] = 240;
		pos_y[2] = 310;
		pos_y[3] = 380;
	}
	
	private void initZPanel() {
		score = 0;
		best = 0;
		is_win = 0;
		is_lose = 0;
		for(int i = 0; i < ROW; ++i) 
			for(int j = 0; j < COL; ++j)
				datas[i][j] = 0;
		
		// 随机初始化最开始两个数
		provide_rnd();
		provide_rnd();
	}
	
	// 随机产生新的方块
	private void provide_rnd() {
		while(true) {
			int px = random.nextInt(4);
			int py = random.nextInt(4);
			if(datas[px][py] == 0) {
				int pnum = random.nextInt(2) + 1;
				datas[px][py] = pnum * 2;
				break;
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		
		// 绘制游戏框
		panelIcon.paintIcon(this, g, 0, 0);
		
		//绘制长度、得分、等级
		g.setColor(Color.WHITE);
		g.setFont(new Font("normal", Font.BOLD, 18));
		g.drawString(score + "", (209 - (score + "").length() *(score + "").length()), 65);
		g.drawString(best + "", (275 - (best + "").length() * (best + "").length()), 65);
		
		// 绘制游戏区域
		for(int i = 0; i < ROW; ++i) {
			for(int j = 0; j < COL; ++j) {
				if(datas[i][j] == 0)
					continue;
				else if(datas[i][j] == 2)
					Icon_2.paintIcon(this, g, pos_x[j], pos_y[i]);
				else if(datas[i][j] == 4)
					Icon_4.paintIcon(this, g, pos_x[j], pos_y[i]);
				else if(datas[i][j] == 8)
					Icon_8.paintIcon(this, g, pos_x[j], pos_y[i]);
				else if(datas[i][j] == 16)
					Icon_16.paintIcon(this, g, pos_x[j], pos_y[i]);
				else if(datas[i][j] == 32)
					Icon_32.paintIcon(this, g, pos_x[j], pos_y[i]);
				else if(datas[i][j] == 64)
					Icon_64.paintIcon(this, g, pos_x[j], pos_y[i]);
				else if(datas[i][j] == 128)
					Icon_128.paintIcon(this, g, pos_x[j], pos_y[i]);
				else if(datas[i][j] == 256)
					Icon_256.paintIcon(this, g, pos_x[j], pos_y[i]);
				else if(datas[i][j] == 512)
					Icon_512.paintIcon(this, g, pos_x[j], pos_y[i]);
				else if(datas[i][j] == 1024)
					Icon_1024.paintIcon(this, g, pos_x[j], pos_y[i]);
				else if(datas[i][j] == 2048)
					Icon_2048.paintIcon(this, g, pos_x[j], pos_y[i]);
			}
		}
	}
	
	public static boolean equal(int[][] a, int[][] b) {
		for(int i = 0; i < 4; ++i)
			for(int j = 0; j < 4; ++ j)
				if(a[i][j] != b[i][j])
					return false;
		return true;
	}
//---------------------------------------up-------------------------------------
	public void up_cut_zero(int[][] nums) {
		int[][] result = nums;
		
		for(int i = 0; i < ROW - 1; ++i) {
			for(int j = 0; j < COL; ++j) {
				if(result[i][j] == 0) {
					for(int k = i + 1; k < ROW; ++k) {
						if(result[k][j] != 0) {
							result[i][j] = result[k][j];
							result[k][j] = 0;
							break;
						}
					}
				}
			}
		}
	}
	
	public int[][] up_combine() {
		int[][] result = new int[ROW][COL];
		// 深拷贝（值拷贝）
		for(int i = 0; i < datas.length; ++i)
			result[i] = datas[i].clone();
		
		// 先去0再合并
		up_cut_zero(result);
		for(int c = 0; c < COL; ++c) {
			// 1.中间两个数相等
			if(result[1][c] == result[2][c]) {
				result[1][c] = 2 * result[1][c];
				score += result[1][c];
				result[2][c] = 0;
				continue;
			} else {
				if(result[0][c] == result[1][c]) {
					result[0][c] = 2 * result[0][c];
					score += result[0][c];
					result[1][c] = 0;
				}
				
				if(result[2][c] == result[3][c]) {
					result[2][c] = 2 * result[2][c];
					score += result[2][c];
					result[3][c] = 0;
				}
			}
		}
		// 合并后再去0
		up_cut_zero(result);
		
		return result;
	}
//-----------------------------------------down-------------------------------------
	public void down_cut_zero(int[][] nums) {
		int[][] result = nums;
		
		for(int i = ROW - 1; i > 0; --i) {
			for(int j = 0; j < COL; ++j) {
				if(result[i][j] == 0) {
					for(int k = i - 1; k >= 0; --k) {
						if(result[k][j] != 0) {
							result[i][j] = result[k][j];
							result[k][j] = 0;
							break;
						}
					}
				}
			}
		}
	}
	
	public int[][] down_combine() {
		int[][] result = new int[ROW][COL];
		// 深拷贝（值拷贝）
		for(int i = 0; i < datas.length; ++i)
			result[i] = datas[i].clone();
		
		// 先去0再合并
		down_cut_zero(result);
		for(int c = 0; c < COL; ++c) {
			// 1.中间两个数相等
			if(result[1][c] == result[2][c]) {
				result[2][c] = 2 * result[1][c];
				score += result[2][c];
				result[1][c] = 0;
				continue;
			} else {
				if(result[0][c] == result[1][c]) {
					result[1][c] = 2 * result[0][c];
					score += result[1][c];
					result[0][c] = 0;
				}
				
				if(result[2][c] == result[3][c]) {
					result[3][c] = 2 * result[2][c];
					score += result[3][c];
					result[2][c] = 0;
				}
			}
		}
		// 合并后再去0
		down_cut_zero(result);
		
		return result;
	}
//----------------------------------------left-------------------------------------------
	public void left_cut_zero(int[][] nums) {
		int[][] result = nums;
		
		for(int j = 0; j < COL - 1; ++j) {
			for(int i = 0; i < ROW; ++i) {
				if(result[i][j] == 0) {
					for(int k = j + 1; k < COL; ++k) {
						if(result[i][k] != 0) {
							result[i][j] = result[i][k];
							result[i][k] = 0;
							break;
						}
					}
				}
			}
		}
	}
	
	public int[][] left_combine() {
		int[][] result = new int[ROW][COL];
		// 深拷贝（值拷贝）
		for(int i = 0; i < datas.length; ++i)
			result[i] = datas[i].clone();
		
		// 先去0再合并
		left_cut_zero(result);
		for(int r = 0; r < ROW; ++r) {
			// 1.中间两个数相等
			if(result[r][1] == result[r][2]) {
				result[r][1] = 2 * result[r][1];
				score += result[r][1];
				result[r][2] = 0;
				continue;
			} else {
				if(result[r][0] == result[r][1]) {
					result[r][0] = 2 * result[r][0];
					score += result[r][0];
					result[r][1] = 0;
				}
				
				if(result[r][2] == result[r][3]) {
					result[r][2] = 2 * result[r][2];
					score += result[r][2];
					result[r][3] = 0;
				}
			}
		}
		// 合并后再去0
		left_cut_zero(result);
		
		return result;
	}
//----------------------------------------right------------------------------------
	public void right_cut_zero(int[][] nums) {
		int[][] result = nums;
		
		for(int j = COL - 1; j > 0; --j) {
			for(int i = 0; i < ROW; ++i) {
				if(result[i][j] == 0) {
					for(int k = j - 1; k >= 0; --k) {
						if(result[i][k] != 0) {
							result[i][j] = result[i][k];
							result[i][k] = 0;
							break;
						}
					}
				}
			}
		}
	}
	
	public int[][] right_combine() {
		int[][] result = new int[ROW][COL];
		// 深拷贝（值拷贝）
		for(int i = 0; i < datas.length; ++i)
			result[i] = datas[i].clone();
		
		// 先去0再合并
		right_cut_zero(result);
		for(int r = 0; r < ROW; ++r) {
			// 1.中间两个数相等
			if(result[r][1] == result[r][2]) {
				result[r][2] = 2 * result[r][1];
				score += result[r][2];
				result[r][1] = 0;
				continue;
			} else {
				if(result[r][0] == result[r][1]) {
					result[r][1] = 2 * result[r][0];
					score += result[r][1];
					result[r][0] = 0;
				}
				
				if(result[r][2] == result[r][3]) {
					result[r][3] = 2 * result[r][2];
					score += result[r][3];
					result[r][2] = 0;
				}
			}
		}
		// 合并后再去0
		right_cut_zero(result);
		
		return result;
	}
//--------------------------------------------------------------------

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_SPACE) {
			if(is_win == 1 || is_lose == 1) {
				initZPanel();
				repaint();
			}
		}
		
		int[][] result = new int[ROW][COL];
		if(keyCode == KeyEvent.VK_UP) {
			result = up_combine();
		} else if(keyCode == KeyEvent.VK_DOWN) {
			result = down_combine();
		} else if(keyCode == KeyEvent.VK_LEFT) {
			result = left_combine();
		} else if(keyCode == KeyEvent.VK_RIGHT) {
			result = right_combine();
		}
		
		if(!equal(result, datas)) {
			// 深拷贝（值拷贝）
			for(int i = 0; i < datas.length; ++i)
				datas[i] = result[i].clone();
			
			provide_rnd();
			repaint();
			check_win();
		} else {
			check_lose();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void check_win() {
		for(int i = 0; i < ROW; ++i) {
			for(int j = 0; j < COL; ++j) {
				best = Math.max(best, datas[i][j]);
				if(datas[i][j] >= 2048) {
					is_win = 1;
					JOptionPane.showMessageDialog(this, "恭喜，你赢了！\n按空格键重新开始。", "2048!!!", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
	}
	
	private void check_lose() {
		int[][] result = new int[ROW][COL];
		result = up_combine();
		if(!equal(result,  datas))
			return;

		result = down_combine();
		if(!equal(result,  datas))
			return;
		
		result = left_combine();
		if(!equal(result,  datas))
			return;
		
		result = right_combine();
		if(!equal(result,  datas))
			return;
		
		is_lose = 1;
		JOptionPane.showMessageDialog(this, "很遗憾你输了！\n按空格键重新开始。", "AH O~~~~~", JOptionPane.PLAIN_MESSAGE);
	}
}
