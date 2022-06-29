package global;

import java.awt.Cursor;

import shape.TLine;
import shape.TOval;
import shape.TPolygon;
import shape.TRectangle;
import shape.TSelection;
import shape.TShape;
import transformer.Drawer;
import transformer.Mover;
import transformer.Resizer;
import transformer.Rotator;
import transformer.Transformer;

public class Constant {
	
	public enum ETransformationStyle {
		e2PTransformation,
		eNPTransformation
	}
	
	public enum ETools {
		eSelection("Selection", new TSelection(), ETransformationStyle.e2PTransformation, "select shape"),
		eRectangle("Rectangle", new TRectangle(), ETransformationStyle.e2PTransformation, "draw rectangle"),
		eOval("Oval", new TOval(), ETransformationStyle.e2PTransformation, "draw oval"),
		eLine("Line",new TLine(), ETransformationStyle.e2PTransformation, "draw line"),
		ePolygon("Polygon", new TPolygon(), ETransformationStyle.eNPTransformation, "draw polygon");
		
		private String label;
		private TShape tool;
		private ETransformationStyle eTransformationStyle;
		private String toolTip;
		
		private ETools(String label, TShape tool, ETransformationStyle eTransformationStyle, String toolTip) {
			this.label = label;
			this.tool = tool;
			this.eTransformationStyle = eTransformationStyle;
			this.toolTip = toolTip;
		}
		
		public String getLabel() {
			return this.label;
		}
		
		public TShape newShape() {
			return this.tool.clone();
		}
		
		public ETransformationStyle getTransformationStyle() {
			return this.eTransformationStyle;
		}
		
		public String getToolTip() {
			return this.toolTip;
		}
	}
	
	public enum ETransformers {
		eDrawer(new Drawer()),
		eMover(new Mover()),
		eResizer(new Resizer()),
		eRotator(new Rotator());
		
		private Transformer transformer;
		
		private ETransformers(Transformer transformer) {
			this.transformer = transformer;			
		}
		
		public Transformer getTransformer() {
			return this.transformer;
		}	
	}
	
	public enum EFileMenu {
		eNew("New"),
		eOpen("Open"),
		eSave("Save"),
		eSaveAs("SaveAs"),
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
	
	public enum EEditMenu {
		eUndo("undo"),
		eRedo("redo"),
		eCut("cut"),
		eCopy("copy"),
		ePaste("paste"),
		eGroup("group"),
		eUngroup("ungroup"),
		eDelete("delete");
		
		private String label;
		
		private EEditMenu(String label) {
			this.label = label;
		}
		
		public String getLabel() {
			return this.label;
		}
	}
	public enum ECursor {
		eRR(new Cursor(Cursor.HAND_CURSOR)),
		eNW(new Cursor(Cursor.NW_RESIZE_CURSOR)),
		eWW(new Cursor(Cursor.W_RESIZE_CURSOR)),
		eSW(new Cursor(Cursor.SW_RESIZE_CURSOR)),
		eSS(new Cursor(Cursor.S_RESIZE_CURSOR)),
		eSE(new Cursor(Cursor.SE_RESIZE_CURSOR)),
		eEE(new Cursor(Cursor.E_RESIZE_CURSOR)),
		eNE(new Cursor(Cursor.NE_RESIZE_CURSOR)),
		eNN(new Cursor(Cursor.N_RESIZE_CURSOR)),
		eMove(new Cursor(Cursor.MOVE_CURSOR));
		
		private Cursor cursor;
		
		private ECursor(Cursor cursor) {
			this.cursor = cursor;
		}
		
		public Cursor getCursor() {
			return this.cursor;
		}
	}
}
