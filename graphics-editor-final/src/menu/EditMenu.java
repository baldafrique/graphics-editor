package menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import frame.DrawingPanel;
import global.Constant.EEditMenu;

public class EditMenu extends JMenu {
	
	private static final long serialVersionUID = 1L;
	private DrawingPanel drawingPanel;

	public EditMenu(String s) {
		super(s);
		ActionHandler actionHandler = new ActionHandler();
		
		
		for (EEditMenu eMenuItem : EEditMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getLabel());
			menuItem.addActionListener(actionHandler);
			menuItem.setActionCommand(eMenuItem.name());
			this.add(menuItem);
			
		}
	}
	
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}

	public void initialize() {
		
	}
	
	class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals(EEditMenu.eUndo.name())) {
				drawingPanel.undo();
			}
			else if (e.getActionCommand().equals(EEditMenu.eRedo.name())) {
				drawingPanel.redo();
			}
			else if (e.getActionCommand().equals(EEditMenu.eCut.name())) {
				drawingPanel.cut();
			}
			else if (e.getActionCommand().equals(EEditMenu.eCopy.name())) {
				drawingPanel.copy();				
			}
			else if (e.getActionCommand().equals(EEditMenu.ePaste.name())) {
				drawingPanel.paste();
			}
			else if (e.getActionCommand().equals(EEditMenu.eGroup.name())) {
				drawingPanel.group();
			}
			else if (e.getActionCommand().equals(EEditMenu.eUngroup.name())) {
				drawingPanel.ungroup();
			}
			else if (e.getActionCommand().equals(EEditMenu.eDelete.name())) {
				drawingPanel.delete();
			}
		}
		
	}		
}
