package frames;
import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	// attributes
	private static final long serialVersionUID = 1L;
	
	// components
	private MenuBar menuBar;
	private ToolBar toolBar;
	private DrawingPanel drawingPanel;
	
	public MainFrame() {
		// attributes
		this.setSize(400, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// components
		BorderLayout layoutManager = new BorderLayout();
		this.setLayout(layoutManager);
		
		this.menuBar = new MenuBar();
		this.setJMenuBar(this.menuBar);
		
		this.toolBar = new ToolBar();
		this.add(this.toolBar, BorderLayout.NORTH);
		
		this.drawingPanel = new DrawingPanel();
		this.add(this.drawingPanel, BorderLayout.CENTER);
		
		// association
		this.toolBar.associate(this.drawingPanel);
	}
}