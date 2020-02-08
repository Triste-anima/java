package AnimalChess;

import javax.swing.JFrame;

public class Main{
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBounds(300, 100, 540, 700);
		frame.setTitle("AnimalChess-ZCM");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new APanel());
		
		frame.setVisible(true);
	}
}
