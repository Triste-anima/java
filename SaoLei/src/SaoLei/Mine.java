package SaoLei;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Mine implements ActionListener, MouseListener{
	JFrame frame = new JFrame();
	ImageIcon bannerIcon = new ImageIcon("banner.png");
	ImageIcon guessIcon = new ImageIcon("guess.png");
	ImageIcon bombIcon = new ImageIcon("bomb.png");
	ImageIcon failIcon = new ImageIcon("fail.png");
	ImageIcon winIcon = new ImageIcon("win.png");
	ImageIcon win_flagIcon = new ImageIcon("win_flag.png");
	ImageIcon mine_flagIcon = new ImageIcon("mine_flag.png");
	
	// 数据结构
	int ROW = 20;
	int COL = 20;
	// 格子种类： -1-雷； 0-空格； 1； 2； 3
	int[][] datas = new int[20][20];
	JButton[][] btns = new JButton[ROW][COL];
	// 雷的个数
	int LEI_COUNT = 30;
	int unopen_count = ROW * COL;
	int opened_count = 0;
	// 用时计数
	int seconds = 0;
	// 难度设置
	int level = 0;
	// 状态栏
	JButton bannerBtn = new JButton(bannerIcon);
	JLabel opened = new JLabel("已开： " + opened_count);
	JLabel to_open = new JLabel("待开： " + unopen_count);
	JLabel spend_time = new JLabel("用时： " + seconds + "s");
	JLabel mine = new JLabel("地雷： " + LEI_COUNT + "个");
	Timer timer = new Timer(1000, this);
	
	public Mine() {
		frame.setSize(900, 800);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		setHeader();
		addLei();
		setButtons();
		timer.start();
		frame.setVisible(true);
	}
	
	private void setHeader() {
		JPanel panel = new JPanel(new GridBagLayout());
		
		GridBagConstraints c1 = new GridBagConstraints(0,0,4,1,1.0,1.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0);
		panel.add(bannerBtn, c1);
		bannerBtn.addMouseListener(this);
		
		opened.setOpaque(true);
		opened.setBackground(Color.white);
		opened.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		to_open.setOpaque(true);
		to_open.setBackground(Color.white);
		to_open.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		spend_time.setOpaque(true);
		spend_time.setBackground(Color.white);
		spend_time.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		bannerBtn.setOpaque(true);
		bannerBtn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		bannerBtn.setBackground(Color.white);
		
		mine.setOpaque(true);
		mine.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		mine.setBackground(Color.white);
		
		GridBagConstraints c2 = new GridBagConstraints(0,1,1,1,1.0,1.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0);
		panel.add(opened, c2);
		GridBagConstraints c3 = new GridBagConstraints(1,1,1,1,1.0,1.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0);
		panel.add(to_open, c3);
		GridBagConstraints c4 = new GridBagConstraints(2,1,1,1,1.0,1.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0);
		panel.add(spend_time, c4);
		GridBagConstraints c5 = new GridBagConstraints(3,1,1,1,1.0,1.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0);
		panel.add(mine, c5);	
				
		frame.add(panel, BorderLayout.NORTH);
	}
	
	private int count_neighbors(int r, int c) {
		int count = 0;
		if(r > 0 && c > 0 && datas[r - 1][c - 1] == -1)
			count++;
		if(r > 0 && datas[r - 1][c] == -1)
			count++;
		if(r > 0 && c < COL - 1 && datas[r - 1][c + 1] == -1)
			count++;
		if(c > 0 && datas[r][c - 1] == -1)
			count++;
		if(c < COL - 1 && datas[r][c + 1] == -1)
			count++;
		if(r < ROW - 1 && c > 0 && datas[r + 1][c - 1] == -1)
			count++;
		if(r < ROW - 1 && datas[r + 1][c] == -1)
			count++;
		if(r < ROW - 1 && c < COL - 1 && datas[r + 1][c + 1] == -1)
			count++;
		
		return count;
	}
	
	private void addLei() {
		Random rand = new Random();
		for(int i = 0; i < LEI_COUNT; ) {
			int r = rand.nextInt(ROW);
			int c = rand.nextInt(COL);
			if(datas[r][c] != -1) {
				datas[r][c] = -1;
				i++;
			}	
		}
		
		// 填充datas，计算周边雷的数量
		for(int i = 0; i < ROW; ++i) {
			for(int j = 0; j < COL; ++j) {
				if(datas[i][j] == -1)
					continue;
				datas[i][j] = count_neighbors(i, j);
			}
		}
	}
	
	public void setButtons() {
		Container can = new Container();
		can.setLayout(new GridLayout(ROW, COL));
		for(int i = 0; i < ROW; ++i) {
			for(int j = 0; j < COL; ++j) {
				JButton btn = new JButton(guessIcon);
				btn.setBackground(new Color(255, 215, 0));
				btn.addActionListener(this);
				btn.addMouseListener(this);
				can.add(btn);
				btns[i][j] = btn;
			}
		}
		frame.add(can, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof Timer) {
			seconds++;
			spend_time.setText("用时： " + seconds + "s");
			timer.start();
			if(unopen_count == LEI_COUNT)
				win();
			return;
		}
	}
	
	private void lose() {
		timer.stop();
		bannerBtn.setIcon(failIcon);
		for(int i = 0; i < ROW; ++i) {
			for(int j = 0; j < COL; ++j) {
				if(btns[i][j].isEnabled()) {
					btns[i][j].setEnabled(false);
					if(datas[i][j] == -1) {
						btns[i][j].setIcon(bombIcon);
						btns[i][j].setDisabledIcon(bombIcon);
					} else {
						btns[i][j].setIcon(null);
						btns[i][j].setBackground(Color.WHITE);
						btns[i][j].setText(datas[i][j] + "");
					}
				}
			}
		}
		JOptionPane.showMessageDialog(frame, "很遗憾你输了！\n你可以点击上方Banner重新开始。", "Big-Bomb", JOptionPane.PLAIN_MESSAGE);
	}
	
	private void win() {
		timer.stop();
		bannerBtn.setIcon(winIcon);
		for(int i = 0; i < ROW; ++i) {
			for(int j = 0; j < COL; ++j) {
				if(btns[i][j].isEnabled()) {
					btns[i][j].setIcon(win_flagIcon);
				}
			}
		}
		JOptionPane.showMessageDialog(frame, "恭喜你完成扫雷任务！", "Brova", JOptionPane.PLAIN_MESSAGE);
	}
	
	private void restart() {
		// 数据清零
		for(int i = 0; i < ROW; ++i) {
			for(int j = 0; j < COL; ++j) {
				datas[i][j] = 0;
				btns[i][j].setBackground(new Color(255, 215, 0));
				btns[i][j].setEnabled(true);
				btns[i][j].setText("");
				btns[i][j].setIcon(guessIcon);
			}
		}
		
		// 恢复状态栏
		unopen_count = ROW * COL;
		opened_count = 0;
		seconds = 0;
		opened.setText("已开： " + opened_count);
		to_open.setText("待开： " + unopen_count);
		spend_time.setText("用时： " + seconds + "s");
		bannerBtn.setIcon(bannerIcon);
		
		// 重启时钟
		timer.start();
		
		// 重新加雷
		addLei();
	}
	
	private void openCell(int i, int j) {
		JButton btn = btns[i][j];
		if(!btn.isEnabled())
			return;
		
		updateCount();
		btn.setIcon(null);
		btn.setEnabled(false);
		btn.setBackground(new Color(148, 0, 211));
		btn.setText(datas[i][j] + "");
		
		if(datas[i][j] == 0) {
			if(i > 0 && j > 0 && datas[i - 1][j - 1] == 0)
				openCell(i - 1, j - 1);
			if(i > 0 && datas[i - 1][j] == 0)
				openCell(i - 1, j);
			if(i > 0 && j < COL - 1 && datas[i - 1][j + 1] == 0)
				openCell(i - 1, j + 1);
			if(j > 0 && datas[i][j - 1] == 0)
				openCell(i, j - 1);
			if(j < COL - 1 && datas[i][j + 1] == 0)
				openCell(i, j + 1);
			if(i < ROW - 1 && j > 0 && datas[i + 1][j - 1] == 0)
				openCell(i + 1, j - 1);
			if(i < ROW - 1 && datas[i + 1][j] == 0)
				openCell(i + 1, j);
			if(i < ROW - 1 && j < COL - 1 && datas[i + 1][j + 1] == 0)
				openCell(i + 1, j + 1);
		}
	}
	
	private void updateCount() {
		opened_count++;
		unopen_count--;
		opened.setText("已开： " + opened_count);
		to_open.setText("待开： " + unopen_count);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JButton btn = (JButton)e.getSource();
		if(btn.equals(bannerBtn)) {
			if(btn.getIcon() == bannerIcon)
				level = (level + 1) % 4;
			
			if(level == 0)
				LEI_COUNT = 30;
			else if(level == 1)
				LEI_COUNT = 50;
			else if(level == 2)
				LEI_COUNT = 80;
			else if(level == 3)
				LEI_COUNT = 100;
			
			mine.setText("地雷： " + LEI_COUNT + "个");
			restart();
			return;
		}

		if(e.getButton() == MouseEvent.BUTTON1) {
			if(btn.getIcon() != mine_flagIcon) {
				for(int i = 0; i < ROW; ++i) {
					for(int j = 0; j < COL; ++j) {
						if(btn.equals(btns[i][j])) {
							if(datas[i][j] == -1)
								lose();
							else 
								openCell(i, j);
							return;	
						}
					}
				}
			}
		} else if(e.getButton() == MouseEvent.BUTTON3) {
			// 右键给雷做标记
			if(btn.isEnabled()) {
				if(btn.getIcon() != mine_flagIcon)
					btn.setIcon(mine_flagIcon);
				else
					btn.setIcon(guessIcon);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	public static void main(String[] args) {
		new Mine();
	}
}
