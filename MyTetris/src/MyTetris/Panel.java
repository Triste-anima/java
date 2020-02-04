package MyTetris;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel{
	private static final long serialVersionUID = 1L;
	// ��Ҫ��Ϸ��
	GameView main_panel = new GameView(20, 10);
	// ���״̬��
	WatchView watch_panel = new WatchView();
	// ��ʱ��
	Timer downMoveTimer;
	// �½��ٶ�
	private int speed;
	// �ж��Ƿ���ͣ
	private boolean isPaused;
	
	public Panel() {
		super();
		// Ҫʹ��setBounds�Զ��岼��ʱ��ҪsetLayout(null)
		this.setLayout(null);
		// �������Ϸ��
		main_panel.setBounds(0, 0, 250, 500);
		main_panel.setPreferredSize(new Dimension(250, 500));
		this.add(main_panel);
		// ��Ӳ��״̬��
		watch_panel.setBounds(250, 0, 120, 500);
		this.add(watch_panel);
		
		downMoveTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!main_panel.moveCell(3))
					updateData();
				
			}
		});
		addListener();
		isPaused = false;
	}
	
	// ���¿�ʼ��Ϸ
	protected void resetGame() {
		isPaused = false;
		main_panel.resetGame();
		watch_panel.reset();
		updateData();
	}
	
	// �����䵽�ײ�ʱ���������¸������ݣ��������·�������
	protected int updateData() {
		int remove_count = main_panel.removeRow();
		watch_panel.updateScore(remove_count, main_panel.creatNextCell());
		speed = (int) (1000 * Math.pow(0.75, watch_panel.getLevel() - 1));
		if(main_panel.getNextUnit())
			downMoveTimer.setDelay(speed);
		else 
			stopGame();
		return remove_count;
	}
	
	// ��ͣ��Ϸ
	protected void pauseGame() {
		if(isPaused == false) {
			downMoveTimer.stop();
			isPaused = true;
			watch_panel.pause_button.setText("Start");
		} else {
			downMoveTimer.restart();
			isPaused = false;
			watch_panel.pause_button.setText("Pause");
		}
		
	}
	
	protected void restartGame() {
		resetGame();
		downMoveTimer.restart();
	}
	
	// ������Ϸ
	protected void stopGame() {
		JOptionPane.showMessageDialog(null, "������յ÷��ǣ�"+ watch_panel.score, "����˹����", JOptionPane.ERROR_MESSAGE);
	}
	
	private void addListener() {
		watch_panel.addStartListener(
			new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					restartGame();
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			}, 
			new KeyListener() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					if(arg0.getKeyCode()==KeyEvent.VK_LEFT)
						main_panel.moveCell(1);
					else if(arg0.getKeyCode()==KeyEvent.VK_RIGHT)
						main_panel.moveCell(2);
					else if(arg0.getKeyCode()==KeyEvent.VK_UP)
						main_panel.moveCell(0);
					else if(arg0.getKeyCode()==KeyEvent.VK_DOWN)
						main_panel.moveCell(3);
				}

				@Override
				public void keyReleased(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void keyTyped(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			watch_panel.addPauseListener(
			new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					pauseGame();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
	}
}
