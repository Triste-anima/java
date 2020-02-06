package Push;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("ÍÆÏä×Ó-ZCM");
		frame.setBounds(500, 100, 400, 420);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new ZPanel());
		
		frame.setVisible(true);
	}
}
