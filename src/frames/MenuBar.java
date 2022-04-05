package frames;
import javax.swing.JMenuBar;

import menus.EditMenu;
import menus.FileMenu;

public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	private FileMenu fileMenu;
	private EditMenu editMenu;
	
	public MenuBar() {
		this.fileMenu = new FileMenu("File");
		this.add(this.fileMenu);
		
		this.editMenu = new EditMenu("Edit");
		this.add(this.editMenu);
	}
}