package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import frames.DrawingPanel.ETools;

public class ToolBar extends JToolBar {
	// attributes
	private static final long serialVersionUID = 1L;
	
	// components
	private JRadioButton rectangleTool;
	private JRadioButton ovalTool;
	private JRadioButton lineTool;
	private JRadioButton polygonTool;
	
	// associations
	private DrawingPanel drawingPanel;
	
	public ToolBar() {
		// components
		ButtonGroup buttonGroup = new ButtonGroup();
		ActionHandler actionHandler = new ActionHandler();
		
		this.rectangleTool = new JRadioButton("rectangle");
		this.add(this.rectangleTool);
		this.rectangleTool.addActionListener(actionHandler);
		buttonGroup.add(this.rectangleTool);
		
		this.ovalTool = new JRadioButton("oval");
		this.add(this.ovalTool);
		this.ovalTool.addActionListener(actionHandler);
		buttonGroup.add(this.ovalTool);
		
		this.lineTool = new JRadioButton("line");
		this.add(this.lineTool);
		this.lineTool.addActionListener(actionHandler);
		buttonGroup.add(this.lineTool);
		
		this.polygonTool = new JRadioButton("polygon");
		this.add(this.polygonTool);
		this.polygonTool.addActionListener(actionHandler);
		buttonGroup.add(this.polygonTool);
	}

	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		this.rectangleTool.doClick();
	}
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == rectangleTool) {
				drawingPanel.setSelectedTool(ETools.eRectangle);
			}
			else if (e.getSource() == ovalTool) {
				drawingPanel.setSelectedTool(ETools.eOval);
			}
			else if (e.getSource() == lineTool) {
				drawingPanel.setSelectedTool(ETools.eLine);
			}
			else if (e.getSource() == polygonTool) {
				drawingPanel.setSelectedTool(ETools.ePolygon);
			}
		}
	}
}