package menus;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import frames.DrawingPanel;
import global.Constants.EFileMenu;

public class FileMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	private File file;
	private DrawingPanel drawingPanel;
	
	public FileMenu(String title) {
		super(title);
		
		this.file = null;
		ActionHandler actionHandler = new ActionHandler();
		
		for (EFileMenu eMenuItem : EFileMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getLabel());
			menuItem.setToolTipText(eMenuItem.getToolTip());
			menuItem.addActionListener(actionHandler);
			menuItem.setActionCommand(eMenuItem.name());
			this.add(menuItem);
		}
	}
	
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	private void store(File file) {
		try {
			FileOutputStream fileOutputStream;
			fileOutputStream = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(this.drawingPanel.getShapes());
			objectOutputStream.close();
			this.drawingPanel.setUpdated(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void load(File file) {
		try {
			FileInputStream fileInputStream;
			fileInputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			Object object = objectInputStream.readObject();
			this.drawingPanel.setShapes(object);
			objectInputStream.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void newPanel() throws IOException {
		File file = new File("new");
		this.load(file);
	}

	public void open() {
		if (this.drawingPanel.isUpdated()) {
			int reply = JOptionPane.CANCEL_OPTION;
			reply = JOptionPane.showConfirmDialog(this.drawingPanel, "변경내용을 저장할까요?");
			if (reply != JOptionPane.CANCEL_OPTION) {
				if (reply == JOptionPane.YES_OPTION) {
					this.save();
				}
			}
		}
		
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(this.drawingPanel);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.file = fileChooser.getSelectedFile();
			this.load(this.file);
		}
	}
	
	public void save() {
		if (this.drawingPanel.isUpdated()) {
			if (this.file == null) {
				this.saveAs();
			}
			else {
				store(this.file);
			}
		}
	}
	
	public void saveAs() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showSaveDialog(this.drawingPanel);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.file = fileChooser.getSelectedFile();
		}
		this.store(this.file);
	}

	public void print() {
		
	}

	public void quit() {
		System.exit(ABORT);
	}
	
	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals(EFileMenu.eNew.name())) {
				try {
					newPanel();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if (e.getActionCommand().equals(EFileMenu.eOpen.name())) {
				open();
			}
			else if (e.getActionCommand().equals(EFileMenu.eSave.name())) {
				save();
			}
			else if (e.getActionCommand().equals(EFileMenu.eSaveAs.name())) {
				saveAs();
			}
			else if (e.getActionCommand().equals(EFileMenu.ePrint.name())) {
				print();
			}
			else if (e.getActionCommand().equals(EFileMenu.eQuit.name())) {
				quit();
			}
		}
	}
}
