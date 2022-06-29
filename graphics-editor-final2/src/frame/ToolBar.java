package frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import global.Constant.ETools;

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
		}
	}

	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		JRadioButton defaultButton = (JRadioButton) this.getComponent(ETools.eSelection.ordinal());
		defaultButton.doClick();
	}
	
	public void initialize() {
		
	}
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			drawingPanel.setSelectedTool(ETools.valueOf(e.getActionCommand()));
		}
	}
	
}