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
	 * ���״̬������ʾ�÷֡���һ����״����ʼ����ͣ
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
		// Ҫʹ��setBounds�Զ��岼��ʱ��ҪsetLayout(null)
		this.setLayout(null);
		
		// ��ӵ÷���ʾ��
		score_label = new JLabel("Score: ", JLabel.CENTER);
		score_label.setBounds(0, 0, 120, 100);
		score_label.setFont(new Font("�����п�", Font.BOLD, 18));
		this.add(score_label);
		// ��ӵȼ���ʾ��
		level_label = new JLabel("Level: ", JLabel.CENTER);
		level_label.setBounds(0, 100, 120, 100);
		level_label.setFont(new Font("�����п�", Font.BOLD, 18));
		this.add(level_label);
		
		// �����ʾ��һ��ͼ��
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
		
		// ������¿�ʼ����ͣ��ť
		new_button = new JButton("New Game");
		new_button.setBounds(0, 300, 120, 100);
		new_button.setFont(new Font("�����п�", Font.BOLD, 15));
		this.add(new_button);
		
		pause_button = new JButton("Pause");
		pause_button.setBounds(0, 400, 120, 100);
		pause_button.setFont(new Font("�����п�", Font.BOLD, 15));
		this.add(pause_button);
	}
	
	// ��ʼ��
	public void reset() {
		score = 0;
		score_label.setText("Score: " + score);
		level = 1;
		level_label.setText("Level: " + level);
	}
	
	public int getLevel() {
		return level;
	}
	
	// Ϊ�ؿ�����ͣ��ť����¼�������
	public void addStartListener(MouseListener m, KeyListener k) {
		new_button.addMouseListener(m);
		new_button.addKeyListener(k);
	}
	
	public void addPauseListener(MouseListener m) {
		pause_button.addMouseListener(m);
	}
	
	// ���·�������һ��ͼ����Ϣ
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
		next_panel.updateUI();    // ������ʾ
	}
}
