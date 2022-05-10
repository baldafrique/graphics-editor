package global;

import shapes.TLine;
import shapes.TOval;
import shapes.TPolygon;
import shapes.TRectangle;
import shapes.TShape;

public class Constants {
	
	public enum ETools {
		eRectangle("Rectangle", new TRectangle()),
		eOval("Oval", new TOval()),
		eLine("Line",new TLine()),
		ePolygon("Polygon", new TPolygon());
		
		private String label;
		private TShape tool;
		
		private ETools(String label, TShape tool) {
			this.label = label;
			this.tool = tool;
		}
		
		public String getLabel() {
			return this.label;
		}
		
		public TShape newShape() {
			return this.tool.clone();
		}
	}
	
	public enum EFileMenu {
		eNew("New"),
		eOpen("Open"),
		eSave("Save"),
		eSaveAs("SaveAs"),
		eClose("Close"),
		ePrint("Print"),
		eQuit("Quit");
		
		private String label;
		
		private EFileMenu(String label) {
			this.label = label;
		}
		
		public String getLabel() {
			return this.label;
		}
	}
	
}
