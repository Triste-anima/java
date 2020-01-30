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
	// 导入图片
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
	
	// 控制蛇头方向 R, L, U, D;
	String direction = "";
	// 控制开始或暂停
	boolean isStarted = false;
	// 设置游戏时钟
	Timer timer = new Timer(200, this);
	// 判断蛇是否还活着
	boolean isalive = true;
	// 食物坐标
	int foodx;
	int foody;
	Random random = new Random();
	// 食物属性和计数器: 0-normal, 1-super, 2-poison
	int attr = 0;
	int turn = random.nextInt(10) + 5;	//5~14之间的随机数
	int exist_time = 70;
	// 当前得分
	int score = 0;
	// 设置等级
	int level = 0;
	// 是否可以穿墙
	boolean crossable = true;
	// 背景音乐
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
		
		// 绘制标题
		title.paintIcon(this, g, 25, 11);
		
		//绘制长度、得分、等级
		g.setColor(Color.WHITE);
		g.setFont(new Font("normal", Font.BOLD, 20));
		g.drawString("Len: " + len, 700, 35);
		g.drawString("Score: " + score, 700, 60);
		g.drawString("Level: " + level, 50, 45);
		
		// 填充游戏框
		g.setColor(Color.BLACK);
		g.fillRect(25, 75, 850, 600);
		
		
		// 绘制头
		if(direction == "R")
			head_right.paintIcon(this, g, snakex[0], snakey[0]);
		else if(direction == "L")
			head_left.paintIcon(this, g, snakex[0], snakey[0]);
		else if(direction == "U")
			head_up.paintIcon(this, g, snakex[0], snakey[0]);
		else if(direction == "D")
			head_down.paintIcon(this, g, snakex[0], snakey[0]);
		
		// 判断是否结束游戏
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
		
		// 绘制身体
		for(int i = 1; i < len; ++i) {
			body.paintIcon(this, g, snakex[i], snakey[i]);
		}
		
		// 绘制食物
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
	
	// 随机显示3种食物
	public void show_food() {
		attr = 0;
		if(turn == 0) {
			turn = random.nextInt(10) + 5;	//5~14之间的随机数;
			attr = random.nextInt(2) + 1;
			exist_time = 100;
		}
		turn--;
		foodx = 25 + 25 * random.nextInt(34); 
		foody = 75 + 25 * random.nextInt(24);
	}
	
	// 蛇的初始化
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
			// 移动，从最后一个身体倒着移
			// 移动身体
			for(int i = len - 1; i > 0; --i) {
				snakex[i] = snakex[i - 1];
				snakey[i] = snakey[i - 1];
			}
			// 移动头(可穿墙 VS 不可穿墙)
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
			
			// 判断是否吃到食物
			for(int i = 0; i < len; ++i) {
				if(snakex[i] == foodx && snakey[i] == foody) {
					// 吃到不同的食物改变相对属性
					if(attr == 0)
						len++;
					else if(attr == 1) {
						score += 50;
						len += 3;
					} else if(attr == 2) {
						score -= 30;
						len--;
					}
						
					
					// 重新生成食物位置
					show_food();
					
					// 增加得分并判断是否升级
					if(level == 0) {			// 等级为0时，每个食物+5分，可穿墙，速度为200ms 
						score += 5;
						if(score >= 100) {
							level = 1;
							crossable = false;
						}
					} else if(level == 1) {		// 等级为1时，每个食物+5分，不可穿墙，速度为200ms 
						score += 10;
						if(score >= 500) {
							level = 2;
							timer.setDelay(100);
						}
					} else if(level == 2) {		// 等级为2时，每个食物+10分，不可穿墙，速度为100ms 
						score += 20;
						if(score >= 1300) {
							level = 3;
							timer.setDelay(70);
						}
					} else if(level == 3) {		// 等级为3时，每个食物+20分，不可穿墙，速度为70ms 
						score += 30;
					}
					
					break;
				}
			}
			
			// 判断蛇是否咬到自身
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
