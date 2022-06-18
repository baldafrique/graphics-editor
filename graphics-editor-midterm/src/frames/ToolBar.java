package frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import frames.DrawingPanel.EDrawingState;
import global.Constants.ETools;

public class ToolBar extends JToolBar {
	// attributes
	private static final long serialVersionUID = 1L;
	
	// associations
	private DrawingPanel drawingPanel;
	
	public ToolBar() {
		// components
		ButtonGroup buttonGroup = new ButtonGroup();
		ActionHandler actionHandler = new ActionHandler();
		
		for (ETools eTool : ETools.values()) {
			JRadioButton toolButton = new JRadioButton(new ImageIcon(eTool.name() + ".png"));
			toolButton.setToolTipText(eTool.getToolTip());
			toolButton.setActionCommand(eTool.name());
			toolButton.addActionListener(actionHandler);
			this.add(toolButton);
			buttonGroup.add(toolButton);
			toolButton.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					changeCursor(e.getX(), e.getY(), true);
				}
				public void mouseExited(MouseEvent e) {
					changeCursor(e.getX(), e.getY(), false);
				}
			});
		}
	}

	private void changeCursor(int x, int y, boolean flag) {
		Cursor cursor = flag ? new Cursor(Cursor.HAND_CURSOR) : new Cursor(Cursor.DEFAULT_CURSOR);
		this.setCursor(cursor);
	}

	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		JRadioButton defaultButton = (JRadioButton) this.getComponent(ETools.eRectangle.ordinal());
		defaultButton.doClick();
	}
	
	public void setColor(Color color) {
		this.drawingPanel.setColor(color);
	}
	
	public void capture() {
		this.drawingPanel.capture();
	}
	
	public void erase() {
		this.drawingPanel.setDrawingState(EDrawingState.eErasing);
	}
	
	public void toggle() {
		this.drawingPanel.toggle();
	}
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals(ETools.eBlue.name())) {
				setColor(Color.BLUE);
			}
			else if (e.getActionCommand().equals(ETools.eRed.name())) {
				setColor(Color.RED);
			}
			else if (e.getActionCommand().equals(ETools.eBlack.name())) {
				setColor(Color.BLACK);
			}
			else if (e.getActionCommand().equals(ETools.eCapture.name())) {
				capture();
			}
			else if (e.getActionCommand().equals(ETools.eErase.name())) {
				erase();
			}
			else if (e.getActionCommand().equals(ETools.eToggle.name())) {
				toggle();
			}
			else {
				drawingPanel.setSelectedTool(ETools.valueOf(e.getActionCommand()));
			}
		}
	}
}