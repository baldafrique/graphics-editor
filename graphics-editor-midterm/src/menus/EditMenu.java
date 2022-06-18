package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.Vector;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import frames.DrawingPanel;
import frames.DrawingPanel.EDrawingState;
import global.Constants.EEditMenu;
import shapes.TShape;

public class EditMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	private DrawingPanel drawingPanel;
	private Vector<TShape> shapes;
	private Stack<TShape> buffer;
	
	public EditMenu(String title) {
		super(title);
		ActionHandler actionHandler = new ActionHandler();
		
		for (EEditMenu eEditMenu : EEditMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eEditMenu.getLabel());
			menuItem.setToolTipText(eEditMenu.getToolTip());
			menuItem.addActionListener(actionHandler);
			menuItem.setActionCommand(eEditMenu.name());
			this.add(menuItem);
		}
	}
	
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	@SuppressWarnings("unchecked")
	public void undo() {
		this.shapes = (Vector<TShape>) this.drawingPanel.getShapes();
		this.buffer = this.drawingPanel.getBuffer();
		this.buffer.push(this.shapes.get(this.shapes.size() - 1));
		this.shapes.remove(this.shapes.size() - 1);
		this.drawingPanel.setShapes(this.shapes);
	}
	
	@SuppressWarnings("unchecked")
	public void redo() {
		this.shapes = (Vector<TShape>) this.drawingPanel.getShapes();
		this.buffer = this.drawingPanel.getBuffer();
		this.shapes.add(buffer.pop());
		this.drawingPanel.setShapes(this.shapes);
	}
	
	public void cut() {
		this.drawingPanel.setDrawingState(EDrawingState.eCut);
	}
	
	public void paste() {
		this.drawingPanel.setDrawingState(EDrawingState.ePaste);
	}
	
	public void copy() {
		this.drawingPanel.setDrawingState(EDrawingState.eCopy);
	}
	
	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals(EEditMenu.eUndo.name())) {
				undo();
			}
			else if (e.getActionCommand().equals(EEditMenu.eRedo.name())) {
				redo();
			}
			else if (e.getActionCommand().equals(EEditMenu.eCut.name())) {
				cut();
			}
			else if (e.getActionCommand().equals(EEditMenu.ePaste.name())) {
				paste();
			} 
			else if (e.getActionCommand().equals(EEditMenu.eCopy.name())) {
				copy();
			} 
		}
		
	}
}
