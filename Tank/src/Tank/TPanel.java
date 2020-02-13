package Tank;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import Chunk.*;

public class TPanel extends JPanel implements KeyListener{
	// ����ͼƬ������
	ImageIcon steelIcon = new ImageIcon("steels.gif");
	ImageIcon wallIcon = new ImageIcon("walls.gif");
	ImageIcon grassIcon = new ImageIcon("grass.png");
	ImageIcon waterIcon = new ImageIcon("water.gif");
	ImageIcon homeIcon = new ImageIcon("star.png");

	// ��
	Mytank mytank;
	Mytank enemy;
	Walls wall;
	Water water;
	Grass grass;
	Bunker bunker = new Bunker(new Point(24, 12));
	// ̹�˼����ײ���ĸ���
	int[] dx = new int[] {0, 1, 1, 0};
	int[] dy = new int[] {0, 0, 1, 1};
	// װ�����л������ײ�Ŀ�
	LinkedList<Walls> walls = new LinkedList<Walls>();
	LinkedList<Water> waters = new LinkedList<Water>();
	LinkedList<Mytank> enemies = new LinkedList<Mytank>();
	LinkedList<Bullet> bullets = new LinkedList<Bullet>();
	LinkedList<Grass> grasses = new LinkedList<Grass>();
	// ��ʱ���� ���Ƶ����漴�ƶ�
	Timer timer1;
	// ��ʱ���� ��ʱ�����ӵ���Ϣ
	Timer timer2;
	// ��ʱ���� ���Ƶ������
	Timer timer3;
	// ��ʱ���� ���Ƽ������Ƶ��
	Timer timer4;
	// ��ʱ���� ���Ƽ����ƶ�Ƶ��
	Timer timer5;
	// ��ʱ���� �ո���ʱһ���ӵ��޵�ʱ��
	Timer timer6;
	// �����������enemy����λ��
	Random random = new Random();
	// �ҷ�̹��������
	private int mytank_life = 3;
	// ���˸���
	private int enemy_count = 5;
	
	
	public TPanel() {
		new My_map();
		// ����ͼƬ
		wallIcon = change(wallIcon, 0.5);
		steelIcon = change(steelIcon, 0.5);
		waterIcon = change(waterIcon, 0.5);
		grassIcon = change(grassIcon, 0.5);
		
		this.setBackground(Color.BLACK);
		initPanel();
		this.setFocusable(true);
		this.addKeyListener(this);
	}
	
	private void initPanel() {	
		homeIcon = new ImageIcon("star.png");
		homeIcon = change(homeIcon, 0.8);
		
		walls.clear();
		waters.clear();
		enemies.clear();
		bullets.clear();
		grasses.clear();
		// ��ʼ�����п�
		for(int i = 0; i < 26; ++i) {
			for(int j = 0; j < 26; ++j) {
				if(My_map.map[i][j] == 1) {
					wall = new Walls(new Point(i, j), 0);
					walls.add(wall);
				} else if(My_map.map[i][j] == 2) {
					wall = new Walls(new Point(i, j), 1);
					walls.add(wall);
				} else if(My_map.map[i][j] == 3) {
					water = new Water(new Point(i, j));
					waters.add(water);
				} else if(My_map.map[i][j] == 4) {
					bunker = new Bunker(new Point(i, j));
				} else if(My_map.map[i][j] == 5) {
					enemy = new Mytank(new Point(i, j), 0, "D");
					enemies.add(enemy);
				} else if(My_map.map[i][j] == 6) {
					mytank = new Mytank(new Point(i, j), 1, "U");
				} else if(My_map.map[i][j] == 7) {
					grass = new Grass(new Point(i, j));
					grasses.add(grass);
				}
			}
		}
		
		// ��ʱ���Ƶ����漴�ƶ�
		timer1 = new Timer(300, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < enemies.size(); ++i) {
					Mytank temp = enemies.get(i);
					int x = temp.getX();
					int y = temp.getY();
					String d = temp.getDir();
					if(d == "U")
						x -= 1;
					else if(d == "R")
						y += 1;
					else if(d == "D")
						x += 1;
					else
						y -= 1;
					
					if(!enemy_collision(x, y, i))
						temp.move_toward_dir();
					else 
						temp.change_dir();
				}
				repaint();
			}
		});
		timer1.start();
		
		// ˢ���ӵ�
		timer2 = new Timer(100, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < bullets.size(); ++i) {
					Bullet temp = bullets.get(i);
					int x = temp.getX();
					int y = temp.getY();
					if(bullet_collision(x, y, temp.getSide(), temp.getDir())) {
						bullets.remove(temp);
					} else {
						temp.move();
					}
				}
				repaint();
			}
		});
		timer2.start();
		
		// �������
		timer3 = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < enemies.size(); ++i) {
					Mytank temp = enemies.get(i);
					String dir = temp.getDir();
					Bullet tempBullet;
					if(dir == "U")
						tempBullet = new Bullet(new Point(temp.getX() - 1, temp.getY()), 0, dir);
					else if(dir == "R")
						tempBullet = new Bullet(new Point(temp.getX(), temp.getY() + 1), 0, dir);
					else if(dir == "D")
						tempBullet = new Bullet(new Point(temp.getX() + 1, temp.getY()), 0, dir);
					else
						tempBullet = new Bullet(new Point(temp.getX(), temp.getY() - 1), 0, dir);
					
					bullets.add(tempBullet);
				}
				repaint();
			}
		});
		timer3.start();
		
		// ��600ms���һ��
		timer4 = new Timer(600, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mytank.setFireable(true);
			}
		});
		timer4.start();
		
		// ��300ms�ƶ�һ��
		timer5 = new Timer(300, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mytank.setMoveable(true);
			}
		});
		timer5.start();
		
		// �޵�ʱ��1s
		timer6 = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mytank.setUnstoppable(false);
			}
		});
		timer6.start();
		
		playWav("start.wav");
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//����̹��
		mytank.getImage(mytank.getDir()).paintIcon(this, g, mytank.getY() * 30 + 3, mytank.getX() * 30 + 2);
		for(int i = 0; i < enemies.size(); ++i) {
			Mytank temp = enemies.get(i);
			temp.getImage(temp.getDir()).paintIcon(this, g, temp.getY() * 30 + 3, temp.getX() * 30 + 2);
		}
		// �����ӵ�
		for(int i = 0; i < bullets.size(); ++i) {
			Bullet temp = bullets.get(i);
			g.setColor(Color.WHITE);
			g.setFont(new Font("�����п�", Font.BOLD, 40));
			// ƫ����Ϊ���۹���
			if(temp.getDir() == "U")
				g.drawString("��", temp.getY() * 30 + 23, temp.getX() * 30 + 40);
			else if(temp.getDir() == "D")
				g.drawString("��", temp.getY() * 30 + 23, temp.getX() * 30 + 50);
			else if(temp.getDir() == "R")
				g.drawString("��", temp.getY() * 30 + 25, temp.getX() * 30 + 43);
			else 
				g.drawString("��", temp.getY() * 30 + 20, temp.getX() * 30 + 43);
		}
		// ����ǽ
		for(int i = 0; i < walls.size(); ++i) {
			Walls temp = walls.get(i);
			if(temp.getUnbreakable() == 0)
				wallIcon.paintIcon(this, g, temp.getY() * 30, temp.getX() * 30);
			else 
				steelIcon.paintIcon(this, g, temp.getY() * 30, temp.getX() * 30);
		}
		// ����ˮ
		for(int i = 0; i < waters.size(); ++i) {
			Water temp = waters.get(i);
			waterIcon.paintIcon(this, g, temp.getY() * 30, temp.getX() * 30);
		}
		// ���Ʋݵ�
		for(int i = 0; i < grasses.size(); ++i) {
			Grass temp = grasses.get(i);
			grassIcon.paintIcon(this, g, temp.getY() * 30, temp.getX() * 30);
		}
		// ���Ƶﱤ
		homeIcon.paintIcon(this, g, bunker.getY() * 30, bunker.getX() * 30 + 4);
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
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keycode = e.getKeyCode();
		int i = mytank.getX();
		int j = mytank.getY();
		
		if(mytank.getMoveable() == true) {
			mytank.setMoveable(false);
			if(keycode == KeyEvent.VK_UP) {
				mytank.setDir("U");
				if(!mytank_collision(i - 1, j)) {
					mytank.moveUp();
				}	
			} else if(keycode == KeyEvent.VK_RIGHT) {
				mytank.setDir("R");
				if(!mytank_collision(i, j + 1)) {
					mytank.moveRight();
				}
			} else if(keycode == KeyEvent.VK_DOWN) {
				mytank.setDir("D");
				if(!mytank_collision(i + 1, j)) {
					mytank.moveDown();
				}
			} else if(keycode == KeyEvent.VK_LEFT) {
				mytank.setDir("L");
				if(!mytank_collision(i, j - 1)) {
					mytank.moveLeft();
				}
			} 
		}
		
		if(keycode == KeyEvent.VK_SPACE) {
			if(mytank.getFireable() == true) {
				mytank.setFireable(false);
				String dir = mytank.getDir();
				Bullet temp;
				if(dir == "U")
					temp = new Bullet(new Point(i - 1, j), 1, dir);
				else if(dir == "R")
					temp = new Bullet(new Point(i, j + 1), 1, dir);
				else if(dir == "D")
					temp = new Bullet(new Point(i + 1, j), 1, dir);
				else
					temp = new Bullet(new Point(i, j - 1), 1, dir);
				
				bullets.add(temp);
				playWav("fire.wav");
			}
		}
		repaint();
	}
	
	// �ҷ�̹�ˣ� ��ˮ��ǽ���з�̹�˼����ײ
	private boolean mytank_collision(int nx, int ny) {
		if(nx < 0 || nx > 24 || ny < 0 || ny > 24)
			return true;
		
		for(int i = 0; i < walls.size(); ++i) {
			Walls temp = walls.get(i);
			for(int j = 0; j < 4; ++j) {
				if(temp.getX() == nx + dx[j] && temp.getY() == ny + dy[j])
					return true;
			}
		}
		
		for(int i = 0; i < waters.size(); ++i) {
			Water temp = waters.get(i);
			for(int j = 0; j < 4; ++j) {
				if(temp.getX() == nx + dx[j] && temp.getY() == ny + dy[j])
					return true;
			}
		}
		
		for(int i = 0; i < enemies.size(); ++i) {
			Mytank temp = enemies.get(i);
			for(int j = 0; j < 4; ++j) {
				for(int k = 0; k < 4; ++k) {
					if(temp.getX() + dx[k] == nx + dx[j] && temp.getY() + dy[k] == ny + dy[j])
						return true;
				}
			}
		}
		return false;
	}
	
	// �з�̹�ˣ� ��ˮ��ǽ�������з�̹�ˡ��ҷ�̹�˼����ײ
	private boolean enemy_collision(int nx, int ny, int index) {
		if(nx < 0 || nx > 24 || ny < 0 || ny > 24)
			return true;
		
		for(int i = 0; i < walls.size(); ++i) {
			Walls temp = walls.get(i);
			for(int j = 0; j < 4; ++j) {
				if(temp.getX() == nx + dx[j] && temp.getY() == ny + dy[j]) {
					return true;
				}
					
			}
		}
		
		for(int i = 0; i < waters.size(); ++i) {
			Water temp = waters.get(i);
			for(int j = 0; j < 4; ++j) {
				if(temp.getX() == nx + dx[j] && temp.getY() == ny + dy[j])
					return true;
			}
		}
		
		for(int i = 0; i < enemies.size(); ++i) {
			if(i == index)
				continue;
			
			Mytank temp = enemies.get(i);
			for(int j = 0; j < 4; ++j) {
				for(int k = 0; k < 4; ++k) {
					if(temp.getX() + dx[k] == nx + dx[j] && temp.getY() + dy[k] == ny + dy[j])
						return true;
				}
			}
		}
		
		for(int j = 0; j < 4; ++j) {
			for(int k = 0; k < 4; ++k) {
				if(mytank.getX() + dx[k] == nx + dx[j] && mytank.getY() + dy[k] == ny + dy[j])
					return true;
			}
		}
		return false;
	}

	private boolean bullet_collision(int x, int y, int side, String dir) {
		if(x < 0 || x > 25 || y < 0 || y > 25)
			return true;
		
		boolean collision_with_walls = false;
		for(int i = 0; i < walls.size(); ++i) {
			Walls temp = walls.get(i);
			if(dir == "R" || dir == "L"){
				if((temp.getX() == x && temp.getY() == y) || (temp.getX() == x + 1 && temp.getY() == y)) {
					collision_with_walls = true;
					if(temp.getUnbreakable() == 0) {
						walls.remove(temp);
					}
					playWav("hit.wav");
				}
			} else {
				if((temp.getX() == x && temp.getY() == y) || (temp.getX() == x && temp.getY() == y + 1)) {
					collision_with_walls = true;
					if(temp.getUnbreakable() == 0) {
						walls.remove(temp);
					}
					playWav("hit.wav");
				}
			} 
		}
		if(collision_with_walls)
			return true;
		
		for(int i = 0; i < waters.size(); ++i) {
			Water temp = waters.get(i);
			if(temp.getX() == x && temp.getY() == y) {
				playWav("hit.wav");
				return true;
			}
		}
		
		for(int i = 0; i < bullets.size(); ++i) {
			Bullet temp = bullets.get(i);
			if(temp.getX() == x && temp.getY() == y && temp.getSide() != side) {
				bullets.remove(temp);
				return true;
			}
		}
		
		if(x == bunker.getX() && y == bunker.getY()) {
			homeIcon = new ImageIcon("destory.gif");
			playWav("blast.wav");
			GameOver();
		}
			
		
		if(side == 1) {
			for(int i = 0; i < enemies.size(); ++i) {
				Mytank temp = enemies.get(i);
				for(int j = 0; j < 4; ++j) {
					if(temp.getX() + dx[j] == x && temp.getY() + dy[j] == y) {
						enemies.remove(temp);
						playWav("blast.wav");
						if(enemy_count > 0) {
							reborn_enemy();
							enemy_count--;
						} else if(enemies.isEmpty())
							Win();
						
						return true;
					}
				}
			}
		} else {
			for(int j = 0; j < 4; ++j) {
				if(mytank.getX() + dx[j] == x && mytank.getY() + dy[j] == y && mytank.getUnstoppable() == false) {
					playWav("blast.wav");
					if(mytank_life > 0) {
						reborn_mytank();
						playWav("newborn.wav");
						mytank_life--;
					} else
						GameOver();
					
					return true;
				}
			}
		}
		return false;
	}
	
	private void reborn_enemy() {
		int temp = random.nextInt(3) * 12;
		while(enemy_collision(0, temp, -1))
			temp = random.nextInt(3) * 12;
			
		enemy = new Mytank(new Point(0, temp), 0, "D");
		enemies.add(enemy);
	}
	
	private void reborn_mytank() {
		mytank = new Mytank(new Point(24, 8), 1, "U");
	}
	
	private void GameOver() {
		repaint();
		JOptionPane.showMessageDialog(this, "���ź������ˣ�\n", "Big-Bomb", JOptionPane.PLAIN_MESSAGE);
		initPanel();
	}
	
	private void Win() {
		repaint();
		JOptionPane.showMessageDialog(this, "��ϲ�㣬��Ӯ�ˣ�\n", "Big-Bomb", JOptionPane.PLAIN_MESSAGE);
		initPanel();
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
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
