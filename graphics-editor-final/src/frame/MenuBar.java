package frame;
import javax.swing.JMenuBar;

import menu.EditMenu;
import menu.FileMenu;

public class MenuBar extends JMenuBar {
	
	// attributes
	private static final long serialVersionUID = 1L;
	
	// components
	private FileMenu fileMenu;
	private EditMenu editMenu;
	private DrawingPanel drawingPanel;
	
	public MenuBar() {
		// components
		this.fileMenu = new FileMenu("File");
		this.add(this.fileMenu);
		
		this.editMenu = new EditMenu("Edit");
		this.add(this.editMenu);
	}
	
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		this.fileMenu.associate(this.drawingPanel);
		this.editMenu.associate(this.drawingPanel);
	}

	public void initialize() {
		this.fileMenu.initialize();
		this.editMenu.initialize();	
	}
}
