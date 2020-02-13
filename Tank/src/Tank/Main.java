package Tank;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(795, 820);
		frame.setLocationRelativeTo(null);
		frame.setTitle("TankWar-ZCM");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new TPanel());
		
		frame.setVisible(true);
	}
}
