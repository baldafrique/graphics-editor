package global;

import shapes.TLine;
import shapes.TOval;
import shapes.TPolygon;
import shapes.TRectangle;
import shapes.TShape;

public class Constants {
	public enum ETools {
		eRectangle("네모", new TRectangle()),
		eOval("동그라미", new TOval()),
		eLine("라인",new TLine()),
		ePolygon("폴리곤", new TPolygon());
		
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
}
