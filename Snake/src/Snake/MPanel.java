package Snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MPanel extends JPanel implements KeyListener, ActionListener{
	// ����ͼƬ
	ImageIcon title;
	ImageIcon body;
	ImageIcon head_up;
	ImageIcon head_down;
	ImageIcon head_left;
	ImageIcon head_right;
	ImageIcon normal_food;
	ImageIcon super_food;
	ImageIcon poison_food;
	
	int len = 3;
	int[] snakex = new int[750];
	int[] snakey = new int[600];
	
	// ������ͷ���� R, L, U, D;
	String direction = "";
	// ���ƿ�ʼ����ͣ
	boolean isStarted = false;
	// ������Ϸʱ��
	Timer timer = new Timer(200, this);
	// �ж����Ƿ񻹻���
	boolean isalive = true;
	// ʳ������
	int foodx;
	int foody;
	Random random = new Random();
	// ʳ�����Ժͼ�����: 0-normal, 1-super, 2-poison
	int attr = 0;
	int turn = random.nextInt(10) + 5;	//5~14֮��������
	int exist_time = 70;
	// ��ǰ�÷�
	int score = 0;
	// ���õȼ�
	int level = 0;
	// �Ƿ���Դ�ǽ
	boolean crossable = true;
	// ��������
	Clip bgm;
	InputStream is;
	AudioInputStream ais;
	
	public MPanel() {
		loadImages();
		initSnake();
		this.setFocusable(true);
		this.addKeyListener(this);
		loadBGM();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		
		// ���Ʊ���
		title.paintIcon(this, g, 25, 11);
		
		//���Ƴ��ȡ��÷֡��ȼ�
		g.setColor(Color.WHITE);
		g.setFont(new Font("normal", Font.BOLD, 20));
		g.drawString("Len: " + len, 700, 35);
		g.drawString("Score: " + score, 700, 60);
		g.drawString("Level: " + level, 50, 45);
		
		// �����Ϸ��
		g.setColor(Color.BLACK);
		g.fillRect(25, 75, 850, 600);
		
		
		// ����ͷ
		if(direction == "R")
			head_right.paintIcon(this, g, snakex[0], snakey[0]);
		else if(direction == "L")
			head_left.paintIcon(this, g, snakex[0], snakey[0]);
		else if(direction == "U")
			head_up.paintIcon(this, g, snakex[0], snakey[0]);
		else if(direction == "D")
			head_down.paintIcon(this, g, snakex[0], snakey[0]);
		
		// �ж��Ƿ������Ϸ
		if(isalive == false) {
			stopBGM();
			g.setColor(Color.RED);
			g.setFont(new Font("arial", Font.BOLD, 40));
			if(score >= 16300 || len >= 500) {
				g.drawString("You Win!", 350, 330);
				g.drawString("Press Space to Restart", 230, 400);
			}
			else {
				g.drawString("Game Over", 350, 330);
				g.drawString("Press Space to Restart", 230, 400);
			}
			timer.stop();
		}
		
		// ��������
		for(int i = 1; i < len; ++i) {
			body.paintIcon(this, g, snakex[i], snakey[i]);
		}
		
		// ����ʳ��
		if(attr == 0)
			normal_food.paintIcon(this, g, foodx, foody);
		else if(attr == 1 && exist_time > 0)
			super_food.paintIcon(this, g, foodx, foody);
		else if(attr == 2 && exist_time > 0)
			poison_food.paintIcon(this, g, foodx, foody);
		
		if(isStarted == false) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 40));
			g.drawString("Press Space to Start", 250, 350);
		}
	}
	
	// �����ʾ3��ʳ��
	public void show_food() {
		attr = 0;
		if(turn == 0) {
			turn = random.nextInt(10) + 5;	//5~14֮��������;
			attr = random.nextInt(2) + 1;
			exist_time = 100;
		}
		turn--;
		foodx = 25 + 25 * random.nextInt(34); 
		foody = 75 + 25 * random.nextInt(24);
	}
	
	// �ߵĳ�ʼ��
	public void initSnake() {
		len = 3;
		snakex[0] = 100;
		snakey[0] = 100;
		snakex[1] = 75;
		snakey[1] = 100;
		snakex[2] = 50;
		snakey[2] = 100;
		
		show_food();
		isalive = true;
		direction = "R";
		isStarted = false;
		score = 0;
		level = 0;
		crossable = true;
		attr = 0;
		timer.start();
	}
	
	private void loadBGM() {
		try {
			bgm = AudioSystem.getClip();
			is = this.getClass().getClassLoader().getResourceAsStream("sound/bgm.wav");
			ais = AudioSystem.getAudioInputStream(is);
			bgm.open(ais);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void playBGM() {
		bgm.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	private void stopBGM() {
		bgm.stop();
	}
	
	private void loadImages() {
		InputStream is;
		try {
			is = this.getClass().getClassLoader().getResourceAsStream("images/title.jpg");
			title = new ImageIcon(ImageIO.read(is));
			
			is = this.getClass().getClassLoader().getResourceAsStream("images/body.png");
			body = new ImageIcon(ImageIO.read(is));
			
			is = this.getClass().getClassLoader().getResourceAsStream("images/up.png");
			head_up = new ImageIcon(ImageIO.read(is));
			
			is = this.getClass().getClassLoader().getResourceAsStream("images/down.png");
			head_down = new ImageIcon(ImageIO.read(is));
			
			is = this.getClass().getClassLoader().getResourceAsStream("images/left.png");
			head_left = new ImageIcon(ImageIO.read(is));
			
			is = this.getClass().getClassLoader().getResourceAsStream("images/right.png");
			head_right = new ImageIcon(ImageIO.read(is));
			
			is = this.getClass().getClassLoader().getResourceAsStream("images/yellow_food.png");
			normal_food = new ImageIcon(ImageIO.read(is));
			
			is = this.getClass().getClassLoader().getResourceAsStream("images/red_food.png");
			super_food = new ImageIcon(ImageIO.read(is));
			
			is = this.getClass().getClassLoader().getResourceAsStream("images/green_food.png");
			poison_food = new ImageIcon(ImageIO.read(is));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_SPACE) {
			if(isalive == false) {
				loadBGM();
				initSnake();
			}
			isStarted = !isStarted;
			if(isStarted == true)
				playBGM();
			else
				stopBGM();
		} else if(keyCode == KeyEvent.VK_UP && isStarted == true && isalive == true) {
			if(snakey[1] == snakey[0] - 25)
				isalive = false;
			direction = "U";
		} else if(keyCode == KeyEvent.VK_DOWN && isStarted == true && isalive == true) {
			if(snakey[1] == snakey[0] + 25)
				isalive = false;
			direction = "D";
		} else if(keyCode == KeyEvent.VK_LEFT && isStarted == true && isalive == true) {
			if(snakex[1] == snakex[0] - 25)
				isalive = false;
			direction = "L";
		} else if(keyCode == KeyEvent.VK_RIGHT && isStarted == true && isalive == true) {
			if(snakex[1] == snakex[0] + 25)
				isalive = false;
			direction = "R";
		}
		
		repaint();
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(isStarted == true) {
			// �ƶ��������һ�����嵹����
			// �ƶ�����
			for(int i = len - 1; i > 0; --i) {
				snakex[i] = snakex[i - 1];
				snakey[i] = snakey[i - 1];
			}
			// �ƶ�ͷ(�ɴ�ǽ VS ���ɴ�ǽ)
			if(direction == "R") {
				if(snakex[0] >= 850 && crossable)
					snakex[0] = 25;
				else if(snakex[0] >= 850 && crossable == false)
					isalive = false;
				else
					snakex[0] += 25;
			} else if(direction == "L") {
				if(snakex[0] <= 25 && crossable)
					snakex[0] = 850;
				else if(snakex[0] <= 25 && crossable == false)
					isalive = false;
				else
					snakex[0] -= 25;
			} else if(direction == "U") {
				if(snakey[0] <= 75 && crossable)
					snakey[0] = 650;
				else if(snakey[0] <= 75 && crossable == false)
					isalive = false;
				else
					snakey[0] -= 25;
			} else if(direction == "D") {
				if(snakey[0] >= 650 && crossable)
					snakey[0] = 75;
				else if(snakey[0] >= 650 && crossable == false)
					isalive = false;
				else
					snakey[0] += 25;
			}
			
			// �ж��Ƿ�Ե�ʳ��
			for(int i = 0; i < len; ++i) {
				if(snakex[i] == foodx && snakey[i] == foody) {
					// �Ե���ͬ��ʳ��ı��������
					if(attr == 0)
						len++;
					else if(attr == 1) {
						score += 50;
						len += 3;
					} else if(attr == 2) {
						score -= 30;
						len--;
					}
						
					
					// ��������ʳ��λ��
					show_food();
					
					// ���ӵ÷ֲ��ж��Ƿ�����
					if(level == 0) {			// �ȼ�Ϊ0ʱ��ÿ��ʳ��+5�֣��ɴ�ǽ���ٶ�Ϊ200ms 
						score += 5;
						if(score >= 100) {
							level = 1;
							crossable = false;
						}
					} else if(level == 1) {		// �ȼ�Ϊ1ʱ��ÿ��ʳ��+5�֣����ɴ�ǽ���ٶ�Ϊ200ms 
						score += 10;
						if(score >= 500) {
							level = 2;
							timer.setDelay(100);
						}
					} else if(level == 2) {		// �ȼ�Ϊ2ʱ��ÿ��ʳ��+10�֣����ɴ�ǽ���ٶ�Ϊ100ms 
						score += 20;
						if(score >= 1300) {
							level = 3;
							timer.setDelay(70);
						}
					} else if(level == 3) {		// �ȼ�Ϊ3ʱ��ÿ��ʳ��+20�֣����ɴ�ǽ���ٶ�Ϊ70ms 
						score += 30;
					}
					
					break;
				}
			}
			
			// �ж����Ƿ�ҧ������
			for(int i = 1; i < len; ++i) {
				if(snakex[0] == snakex[i] && snakey[0] == snakey[i]) 
					isalive = false;
			}

			repaint();
		}
		timer.start();
		if(attr != 0) {
			exist_time--;
			if(exist_time < 0) {
				show_food();
				exist_time = 70;
			}
		}
	}
}
