package MyTetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WatchView extends JPanel{
	/**
	 * 侧边状态栏，显示得分、下一个形状、开始、暂停
	 */
	private static final long serialVersionUID = 3L;
	JLabel score_label;
	JLabel level_label;
	JPanel next_panel;
	JButton new_button;
	JButton pause_button;
	
	public int score;
	public int level;
	private JLabel[][] nextBlock;
	
	public WatchView() {
		super();
		// 要使用setBounds自定义布局时先要setLayout(null)
		this.setLayout(null);
		
		// 添加得分显示栏
		score_label = new JLabel("Score: ", JLabel.CENTER);
		score_label.setBounds(0, 0, 120, 100);
		score_label.setFont(new Font("华文行楷", Font.BOLD, 18));
		this.add(score_label);
		// 添加等级显示栏
		level_label = new JLabel("Level: ", JLabel.CENTER);
		level_label.setBounds(0, 100, 120, 100);
		level_label.setFont(new Font("华文行楷", Font.BOLD, 18));
		this.add(level_label);
		
		// 添加显示下一个图形
		next_panel = new JPanel(new GridLayout(4, 4));
		nextBlock = new JLabel[4][4];
		for(int i = 0; i < 4; ++i) {
			for(int j = 0; j < 4; ++j) {
				nextBlock[i][j] = new JLabel();
				nextBlock[i][j].setPreferredSize(new Dimension(25, 25));
				nextBlock[i][j].setBackground(Color.MAGENTA);
				next_panel.add(nextBlock[i][j]);
			}
		}
		next_panel.setBounds(15, 200, 100, 100);
		this.add(next_panel);
		
		// 添加重新开始和暂停按钮
		new_button = new JButton("New Game");
		new_button.setBounds(0, 300, 120, 100);
		new_button.setFont(new Font("华文行楷", Font.BOLD, 15));
		this.add(new_button);
		
		pause_button = new JButton("Pause");
		pause_button.setBounds(0, 400, 120, 100);
		pause_button.setFont(new Font("华文行楷", Font.BOLD, 15));
		this.add(pause_button);
	}
	
	// 初始化
	public void reset() {
		score = 0;
		score_label.setText("Score: " + score);
		level = 1;
		level_label.setText("Level: " + level);
	}
	
	public int getLevel() {
		return level;
	}
	
	// 为重开、暂停按钮添加事件监听器
	public void addStartListener(MouseListener m, KeyListener k) {
		new_button.addMouseListener(m);
		new_button.addKeyListener(k);
	}
	
	public void addPauseListener(MouseListener m) {
		pause_button.addMouseListener(m);
	}
	
	// 更新分数、下一个图形信息
	public void updateScore(int s, Cell next) {
		score += s * 100;
		score_label.setText("Score: " + score);
		level = score / 1000 + 1;
		level_label.setText("Level: " + level);
		
		for(int i = 0; i < 4; ++i)
			for(int j = 0; j < 4; ++j)
				nextBlock[i][j].setOpaque(false);
		
		for(Point p:next.getBlock())
			nextBlock[p.x][p.y].setOpaque(true);
		next_panel.updateUI();    // 更新显示
	}
}
