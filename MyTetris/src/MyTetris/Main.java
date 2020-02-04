package MyTetris;

import javax.swing.JFrame;

public class Main extends JFrame{
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBounds(500, 180, 370, 538);
		frame.setTitle("Tetris--ZCM");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Panel());
		
		frame.setVisible(true);
	}
}
