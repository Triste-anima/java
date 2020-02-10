package NationalChess;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBounds(300, 100, 630, 650);
		frame.setTitle("NationalChess-ZCM");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new NPanel());
		
		frame.setVisible(true);
	}
}
