package AirWar;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import Block.*;

public class APanel extends JPanel implements KeyListener{
	// 画布大小
	public static final double HEIGHT = 765;
	public static final double WIDTH = 500;
	// 导入图片
	ImageIcon backgroundIcon = new ImageIcon("background.png");
	ImageIcon gameoverIcon = new ImageIcon("gameover.png");
	ImageIcon againIcon = new ImageIcon("again.png");
	ImageIcon pauseIcon = new ImageIcon("pause_nor.png");
	ImageIcon pause_pre_Icon = new ImageIcon("pause_pressed.png");
	ImageIcon resumeIcon = new ImageIcon("pause_nor.png");
	ImageIcon resume_pre_Icon = new ImageIcon("pause_pressed.png");
	ImageIcon bombIcon = new ImageIcon("bomb.png");
	
	ImageIcon me1Icon = new ImageIcon("me1.png");
	ImageIcon me2Icon = new ImageIcon("me2.png");
	ImageIcon me_d1Icon = new ImageIcon("me_destroy_1.png");
	ImageIcon me_d2Icon = new ImageIcon("me_destroy_2.png");
	ImageIcon me_d3Icon = new ImageIcon("me_destroy_3.png");
	ImageIcon me_d4Icon = new ImageIcon("me_destroy_4.png");
	
	ImageIcon e1Icon = new ImageIcon("enemy1.png");
	ImageIcon e1_d1Icon = new ImageIcon("enemy1_down1.png");
	ImageIcon e1_d2Icon = new ImageIcon("enemy1_down2.png");
	ImageIcon e1_d3Icon = new ImageIcon("enemy1_down3.png");
	ImageIcon e1_d4Icon = new ImageIcon("enemy1_down4.png");
	ImageIcon e2Icon = new ImageIcon("enemy2.png");
	ImageIcon e2_d1Icon = new ImageIcon("enemy2_down1.png");
	ImageIcon e2_d2Icon = new ImageIcon("enemy2_down2.png");
	ImageIcon e2_d3Icon = new ImageIcon("enemy2_down3.png");
	ImageIcon e2_d4Icon = new ImageIcon("enemy2_down4.png");
	ImageIcon e3Icon = new ImageIcon("enemy3.png");
	ImageIcon e3_d1Icon = new ImageIcon("enemy3_down1.png");
	ImageIcon e3_d2Icon = new ImageIcon("enemy3_down2.png");
	ImageIcon e3_d3Icon = new ImageIcon("enemy3_down3.png");
	ImageIcon e3_d4Icon = new ImageIcon("enemy3_down4.png");
	ImageIcon e3_d5Icon = new ImageIcon("enemy3_down5.png");
	ImageIcon e3_d6Icon = new ImageIcon("enemy3_down6.png");
	
	ImageIcon bomb_supplyIcon = new ImageIcon("bomb_supply.png");
	ImageIcon bullet_supplyIcon = new ImageIcon("bullet_supply.png");
	ImageIcon bullet1Icon = new ImageIcon("bullet1.png");
	ImageIcon bullet2Icon = new ImageIcon("bullet2.png");
	// 我方飞机
	MyPlane myPlane;
	// 图片列表, 用于图片切换
	ImageIcon[] me_icons;
	ImageIcon[] me_down_icons;
	ImageIcon[] e1_down_icons;
	ImageIcon[] e2_down_icons;
	ImageIcon[] e3_icons;
	ImageIcon[] e3_down_icons;
	// 子弹
	LinkedList<Bullet> bullets = new LinkedList<Bullet>();
	// 敌方坦克
	LinkedList<Enemy> enemies = new LinkedList<Enemy>();
	// 补给
	LinkedList<Supply> supplies = new LinkedList<Supply>();
	// 飞机随鼠标移动
	Timer timer1 = new Timer();
	int count5 = 0;
	// 我方射击并移动子弹
	Timer timer2 = new Timer();
	int count1 = 0;
	// 间接计算双发子弹维持时间
	int count4 = 0;
	// 随机生成敌方坦克并移动敌方坦克
	Timer timer3 = new Timer();
	Random random = new Random();
	int[] choice = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 3};
	int count2 = 0;
	// 刷新补给
	Timer timer4 = new Timer();
	int count3 = 0;
	// 刷新图片播放死亡动画
	Timer timer5 = new Timer();
	Timer timer6 = new Timer();
	Timer timer7 = new Timer();
	Timer timer8 = new Timer();
	// 当前得分、等级、全屏炸弹数量
	int score;
	int level;
	int create_enemy_speed;
	int bomb_count;
	
	public APanel() {
		me1Icon = change(me1Icon, 0.7);
		me2Icon = change(me2Icon, 0.7);
		me_d1Icon = change(me_d1Icon, 0.7);
		me_d2Icon = change(me_d2Icon, 0.7);
		me_d3Icon = change(me_d3Icon, 0.7);
		me_d4Icon = change(me_d4Icon, 0.7);
		
		e1Icon = change(e1Icon, 0.7);
		e1_d1Icon = change(e1_d1Icon, 0.7);
		e1_d2Icon = change(e1_d2Icon, 0.7);
		e1_d3Icon = change(e1_d3Icon, 0.7);
		e1_d4Icon = change(e1_d4Icon, 0.7);
		e2Icon = change(e2Icon, 0.7);
		e2_d1Icon = change(e2_d1Icon, 0.7);
		e2_d2Icon = change(e2_d2Icon, 0.7);
		e2_d3Icon = change(e2_d3Icon, 0.7);
		e2_d4Icon = change(e2_d4Icon, 0.7);
		e3Icon = change(e3Icon, 0.7);
		e3_d1Icon = change(e3_d1Icon, 0.7);
		e3_d2Icon = change(e3_d2Icon, 0.7);
		e3_d3Icon = change(e3_d3Icon, 0.7);
		e3_d4Icon = change(e3_d4Icon, 0.7);
		e3_d5Icon = change(e3_d5Icon, 0.7);
		e3_d6Icon = change(e3_d6Icon, 0.7);
		
		bomb_supplyIcon = change(bomb_supplyIcon, 0.7);
		bullet_supplyIcon = change(bullet_supplyIcon, 0.7);
		backgroundIcon = change(backgroundIcon, 1.2);
		
		me_icons = new ImageIcon[] {me1Icon, me2Icon};
		me_down_icons = new ImageIcon[] {me_d1Icon, me_d2Icon, me_d3Icon, me_d4Icon};
		e1_down_icons = new ImageIcon[] {e1_d1Icon, e1_d2Icon, e1_d3Icon, e1_d4Icon};
		e2_down_icons = new ImageIcon[] {e2_d1Icon, e2_d2Icon, e2_d3Icon, e2_d4Icon};
		e3_down_icons = new ImageIcon[] {e3_d1Icon, e3_d2Icon, e3_d3Icon, e3_d4Icon, e3_d5Icon, e3_d6Icon};
		initPanel();
		this.setFocusable(true);
		this.addKeyListener(this);
	}
	
	private void initPanel() {
		score = 0;
		bomb_count = 0;
		level = 1;
		setCreate_enemy_speed();
		
		bullets.clear();
		enemies.clear();
		supplies.clear();
		myPlane = new MyPlane(APanel.WIDTH/2 - MyPlane.WIDTH/2, APanel.HEIGHT - MyPlane.HEIGHT, me1Icon);
		// 飞机跟随鼠标移动
		try {
			timer1.schedule(new TimerTask() {
				@Override
				public void run() {
					// 我方飞机的喷气动画
					count5 = (count5 + 1) % 4;
					if(count5 == 3) {
						if(myPlane.getIcon() == me1Icon)
							myPlane.setIcon(me2Icon);
						else 
							myPlane.setIcon(me1Icon);
					}
					
					Point point = MouseInfo.getPointerInfo().getLocation();
					myPlane.setPos(point.getX() - 550, point.getY() - 50);
					myPlane.checkPos();
					if(collision_with_enemy(myPlane.getX(), myPlane.getY())) {
						playWav("me_down.wav");
						gameover();
					}
					repaint();
				}
			}, 50, 100);
		} catch(Exception e) {
			e.printStackTrace();
		}
		// 飞机射击
		try {
			timer2.schedule(new TimerTask() {
				@Override
				public void run() {
					// 生成子弹
					count1 = (count1 + 1) % 5;
					if(count1 == 4) {
						if(myPlane.getDoubleshot() == true) {
							// 间接计算双发时间
							count4++;
							if(count4 == 20) {
								myPlane.setDoubleshot(false);
								count4 = 0;
							}
								
							Bullet tempBullet1 = new Bullet(myPlane.getX() + 10, myPlane.getY(), bullet2Icon);
							bullets.add(tempBullet1);
							Bullet tempBullet2 = new Bullet(myPlane.getX() + 60, myPlane.getY(), bullet2Icon);
							bullets.add(tempBullet2);
						} else {
							Bullet tempBullet = new Bullet(myPlane.getX() + 35, myPlane.getY(), bullet1Icon);
							bullets.add(tempBullet);
						}
					}
					// 子弹移动
					for(int i = 0; i < bullets.size(); ++i) {
						Bullet temp = bullets.get(i);
						temp.move();
						if(collision_with_enemy(temp.getX(), temp.getY()) || temp.getY() > 800)
							bullets.remove(temp);
					}
					repaint();
				}
			}, 100, 50);
		} catch(Exception e) {
			e.printStackTrace();
		}
		// 随机生成敌方飞机
		try {
			timer3.schedule(new TimerTask() {
				@Override
				public void run() {
					// 生成飞机
					count2 = (count2 + 1) % create_enemy_speed + 1;
					if(count2 == create_enemy_speed) {
						int tempChoice = random.nextInt(11);
						ImageIcon tempIcon;
						double tempX;
						if(choice[tempChoice] == 1) {
							tempX = random.nextDouble() * 450;
							tempIcon = e1Icon;
						} else if(choice[tempChoice] == 2) {
							tempX = random.nextDouble() * 420;
							tempIcon = e2Icon;
						} else {
							tempX = random.nextDouble() * 380;
							tempIcon = e3Icon;
						}
							
						Enemy tempEnemy = new Enemy(tempX, -100, choice[tempChoice], tempIcon);
						enemies.add(tempEnemy);
					}
					// 飞机移动
					for(int i = 0; i < enemies.size(); ++i) {
						Enemy temp = enemies.get(i);
						temp.move();
						if(temp.getY() > 800)
							enemies.remove(temp);
					}
					repaint();
				}
			}, 100, 100);
		} catch(Exception e) {
			e.printStackTrace();
		}
		// 刷新补给， 30s一个补给
		try {
			timer4.schedule(new TimerTask() {
				@Override
				public void run() {
					// 生成补给
					count3 = (count3 + 1) % 151;
					if(count3 == 150) {
						double tempX = random.nextDouble() * 450;
						Supply tempSupply = new Supply(tempX, -100, count1 % 2);
						supplies.add(tempSupply);
						playWav("supply.wav");
					}
					// 子弹移动
					for(int i = 0; i < supplies.size(); ++i) {
						Supply temp = supplies.get(i);
						temp.move();
						if(collision_with_myplane(temp.getX(), temp.getY(), temp.getKind()) || temp.getY() > 800)
							supplies.remove(temp);
					}
					repaint();
				}
			}, 30000, 200);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		playWav("game_music.wav");
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 绘制背景
		backgroundIcon.paintIcon(this, g, 0, 0);
		// 绘制当前得分、等级、炸弹数量
		g.setColor(Color.WHITE);
		g.setFont(new Font("华文行楷", Font.BOLD, 30));
		g.drawString("Score: " + score, 10, 50);
		g.drawString("Level: " + level, 350, 50);
		bombIcon.paintIcon(this, g, 10, 700);
		g.drawString(" × " + bomb_count, 70, 740);
		// 绘制我方飞机
		myPlane.getIcon().paintIcon(this, g, (int)myPlane.getX(), (int)myPlane.getY());
		// 绘制子弹
		for(int i = 0; i < bullets.size(); ++i) {
			Bullet temp = bullets.get(i);
			temp.getIcon().paintIcon(this, g, (int)temp.getX(), (int)temp.getY());
		}
		// 绘制敌机
		for(int i = 0; i < enemies.size(); ++i) {
			Enemy temp = enemies.get(i);
			temp.getIcon().paintIcon(this, g, (int)temp.getX(), (int)temp.getY());
		}
		// 绘制补给
		for(int i = 0; i < supplies.size(); ++i) {
			Supply temp = supplies.get(i);
			if(temp.getKind() == 0)
				bomb_supplyIcon.paintIcon(this, g, (int)temp.getX(), (int)temp.getY());
			else 
				bullet_supplyIcon.paintIcon(this, g, (int)temp.getX(), (int)temp.getY());
		}
	}
	
	// 缩放图片， times为缩放倍数
	public ImageIcon change(ImageIcon image, double times) {
		int width = (int)(image.getIconWidth() * times);
		int height = (int)(image.getIconHeight() * times);
		Image img = image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		ImageIcon image2 = new ImageIcon(img);
		
		return image2;
	}
	
	private void setCreate_enemy_speed() {
		if(level == 1)
			create_enemy_speed = 10;
		else if(level == 2)
			create_enemy_speed = 8;
		else if(level == 3)
			create_enemy_speed = 5;
		else if(level == 4)
			create_enemy_speed = 3;
		else if(level == 3)
			create_enemy_speed = 2;
	}
	
	private boolean collision_with_enemy(double x, double y) {
		for(int i = 0; i < enemies.size(); ++i) {
			Enemy temp = enemies.get(i);
			double x1 = temp.getX();
			double y1 = temp.getY();
			double x2 = x1 + temp.getWidth();
			double y2 = y1 + temp.getHeight();
			if(x1 <= x && x <= x2 && y1 <= y && y <= y2) {
				temp.setHp(temp.getHp() - 1);
				if(temp.getHp() == 0) {
					if(temp.getSize() == 1) {
						// 死亡动画
						timer6 = new Timer();
						try {
							timer6.schedule(new TimerTask() {
								int temp_count = 0;
								@Override
								public void run() {
									// 生成补给
									if(temp_count == 4) {
										enemies.remove(temp);
										temp_count = 0;
										timer6.cancel();
									}
											
									temp.setIcon(e1_down_icons[temp_count]);
									repaint();
									temp_count++;
									
								}
							}, 10, 10);
						} catch(Exception e) {
							e.printStackTrace();
						}
						// 死亡音乐
						playWav("enemy1_down.wav");
					} else if(temp.getSize() == 2) {
						// 死亡动画
						timer7 = new Timer();
						try {
							timer7.schedule(new TimerTask() {
								int temp_count = 0;
								@Override
								public void run() {
									// 生成补给
									if(temp_count == 4) {
										enemies.remove(temp);
										temp_count = 0;
										timer7.cancel();
									}
											
									temp.setIcon(e2_down_icons[temp_count]);
									repaint();
									temp_count++;
									
								}
							}, 10, 20);
						} catch(Exception e) {
							e.printStackTrace();
						}
						
						playWav("enemy2_down.wav");
					} else {
						// 死亡动画
						timer8 = new Timer();
						try {
							timer8.schedule(new TimerTask() {
								int temp_count = 0;
								@Override
								public void run() {
									// 生成补给
									if(temp_count == 6) {
										enemies.remove(temp);
										temp_count = 0;
										timer8.cancel();
									}
											
									temp.setIcon(e3_down_icons[temp_count]);
									repaint();
									temp_count++;
									
								}
							}, 10, 20);
						} catch(Exception e) {
							e.printStackTrace();
						}
						
						playWav("enemy3_down.wav");
					}
						
					score += temp.getScore();
					if(judge_upgrade()) {
						setCreate_enemy_speed();
						playWav("upgrade.wav");
					}
				}
				return true;
			}
		}
		return false;
	}
	
	private boolean collision_with_myplane(double x, double y, int kind) {
		double x1 = myPlane.getX();
		double y1 = myPlane.getY();
		double x2 = x1 + MyPlane.WIDTH;
		double y2 = y1 + MyPlane.HEIGHT;
		if(x1 <= x && x <= x2 && y1 <= y && y <= y2) {
			if(kind == 0) {
				bomb_count++;
				if(bomb_count > 3)
					bomb_count = 3;
				playWav("get_bomb.wav");
			} else {
				myPlane.setDoubleshot(true);
				playWav("get_bullet.wav");
			}
			return true;
		}
		return false;
	}
	
	private boolean judge_upgrade() {
		int temp = level;
		if(score >= 500000)
			temp = 2;
		if(score >= 1000000)
			temp = 3;
		if(score >= 5000000)
			temp = 4;
		if(score >= 10000000)
			temp = 5;
		
		if(temp > level) {
			level = temp;
			return true;
		} else
			return false;
	}
	
	private static void playWav(String path) {
		try {
			AudioInputStream as = AudioSystem.getAudioInputStream(new File(path));
			Clip clip = AudioSystem.getClip();
			clip.open(as);
			clip.start();
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
	}
	
	private void gameover() {
		// 死亡动画
		timer5 = new Timer();
		try {
			timer5.schedule(new TimerTask() {
				int temp_count = 0;
				@Override
				public void run() {
					// 生成补给
					if(temp_count == 4) {
						temp_count = 0;
						timer5.cancel();
					}

					myPlane.setIcon(me_down_icons[temp_count]);
					repaint();
					temp_count++;
				}
			}, 10, 200);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(this, "很遗憾你输了！\n", "Big-Bomb", JOptionPane.PLAIN_MESSAGE);
		initPanel();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keycode = e.getKeyCode();
		if(keycode == KeyEvent.VK_SPACE) {
			if(bomb_count > 0) {
				bomb_count--;
				playWav("use_bomb.wav");
				for(int i = 0; i < enemies.size(); ++i) {
					Enemy temp = enemies.get(i);
					score += temp.getScore();
				}
				enemies.clear();
				
				if(judge_upgrade()) {
					setCreate_enemy_speed();
					playWav("upgrade.wav");
				}	
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
