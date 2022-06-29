package menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import frame.DrawingPanel;
import global.Constant.EFileMenu;

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
	
	private void store(File file) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
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
			FileInputStream fileInputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			Object object = objectInputStream.readObject();
			this.drawingPanel.initialize();
			this.drawingPanel.setShapes(object);
			objectInputStream.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void open() {
		if (this.drawingPanel.isUpdated()) {
			 int reply = JOptionPane.CANCEL_OPTION;
			 reply = JOptionPane.showConfirmDialog(drawingPanel, "Want to save your changes?");
			 if (reply != JOptionPane.CANCEL_OPTION) {
				 	if (reply == JOptionPane.YES_OPTION) {
				 		this.save();
				 	}
				 	JFileChooser fileChooser = new JFileChooser();
				 	int returnVal = fileChooser.showOpenDialog(this.drawingPanel);
				 	if (returnVal == JFileChooser.APPROVE_OPTION) {
				 		this.file = fileChooser.getSelectedFile();
				 		this.load(this.file);
				 	}
			 }
		}
		else {
			JFileChooser fileChooser = new JFileChooser();
		 	int returnVal = fileChooser.showOpenDialog(this.drawingPanel);
		 	if (returnVal == JFileChooser.APPROVE_OPTION) {
		 		this.file = fileChooser.getSelectedFile();
		 		this.load(this.file);
		 	}
		}
	}
	
	private void save() {
		if (this.drawingPanel.isUpdated()) {
			if (this.file == null) {
				this.saveAs();
			}
			else {
				this.store(this.file);
			}
		}
	}
	
	private void saveAs() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showSaveDialog(drawingPanel);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.file = fileChooser.getSelectedFile();
			this.store(this.file);
		}
	}
	
	private void print() {
		PrinterJob printerJob = PrinterJob.getPrinterJob();
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        PageFormat pageFormat = new PageFormat();
        printerJob.setPrintable(drawingPanel, pageFormat);
        boolean flag = printerJob.printDialog(printRequestAttributeSet);
        if (flag) {
            try {
            	printerJob.print(printRequestAttributeSet);
            } catch (PrinterException ex) {

            }
        }
	}
	
	class ActionHandler implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals(EFileMenu.eNew.name())) {
				drawingPanel.initialize();
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
				System.exit(0);
			}
		}
	}
	
}
