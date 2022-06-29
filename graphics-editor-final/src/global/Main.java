package global;

import frame.MainFrame;

public class Main {
	
	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		mainFrame.initialize();
	}
	
}