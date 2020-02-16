package AirWar;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(500, 800);
		frame.setLocationRelativeTo(null);
		frame.setTitle("AirWar-ZCM");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new APanel());
		
		frame.setVisible(true);
	}
}
