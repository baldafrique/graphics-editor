package global;

import frames.MainFrame;

public class Main {
	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame("그림판");
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
//		System.out.println(mainFrame.getLocationOnScreen());
	}
}