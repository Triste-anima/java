package plus;

import javax.swing.JFrame;

public class Game {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBounds(600, 50, 331, 500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Welcome to 2048");
		frame.add(new ZPanel());
		
		frame.setVisible(true);
	}
}
