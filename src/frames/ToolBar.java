package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import global.Constants.ETools;

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
		this.rectangleTool.setActionCommand(ETools.eRectangle.name());
		this.add(this.rectangleTool);
		this.rectangleTool.addActionListener(actionHandler);
		buttonGroup.add(this.rectangleTool);
		
		this.ovalTool = new JRadioButton("oval");
		this.ovalTool.setActionCommand(ETools.eOval.name());
		this.add(this.ovalTool);
		this.ovalTool.addActionListener(actionHandler);
		buttonGroup.add(this.ovalTool);
		
		this.lineTool = new JRadioButton("line");
		this.lineTool.setActionCommand(ETools.eLine.name());
		this.add(this.lineTool);
		this.lineTool.addActionListener(actionHandler);
		buttonGroup.add(this.lineTool);
		
		this.polygonTool = new JRadioButton("polygon");
		this.polygonTool.setActionCommand(ETools.ePolygon.name());
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
			drawingPanel.setSelectedTool(ETools.valueOf(e.getActionCommand()));
		}
	}
}