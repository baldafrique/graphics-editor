package global;

import shapes.TLine;
import shapes.TOval;
import shapes.TPolygon;
import shapes.TRectangle;
import shapes.TShape;

public class Constants {
	public enum ETools {
		eRectangle("네모", new TRectangle(), "네모를 그립니다"),
		eOval("동그라미", new TOval(), "원을 그립니다"),
		eLine("라인",new TLine(), "선을 그립니다"),
		ePolygon("폴리곤", new TPolygon(), "다각형을 그립니다"),
		eErase("지우개", null, "도형을 삭제합니다"),
		eMove("이동", null, "도형을 이동시킵니다"),
		eResize("크기조절", null, "도형의 크기를 조절합니다"),
		eToggle("채우기", null, "도형의 내부를 채울지 선택합니다"),
		eBlue("파란색", null, "선의 색을 파란색으로 바꿉니다"),
		eRed("빨간색", null, "선의 색을 빨간색으로 바꿉니다"),
		eBlack("검정색", null, "선의 색을 검정색으로 바꿉니다"),
		eCapture("캡처", null, "현재 화면을 캡처합니다");
		
		private String label;
		private TShape tool;
		private String toolTip;
		
		private ETools(String label, TShape tool, String toolTip) {
			this.label = label;
			this.tool = tool;
			this.toolTip = toolTip;
		}
		
		public String getLabel() {
			return this.label;
		}
		
		public TShape newShape() {
			return this.tool.clone();
		}
		
		public String getToolTip() {
			return this.toolTip;
		}
	}
	
	public enum EFileMenu {
		eNew("새로만들기", "파일을 새로 만듭니다"),
		eOpen("열기", "기존의 파일을 엽니다"),
		eSave("저장하기", "파일을 저장합니다"),
		eSaveAs("다른이름으로", "파일을 다른 이름으로 저장합니다"),
		ePrint("프린트", "파일을 프린트합니다"),
		eQuit("종료", "프로그램을 종료합니다");
		
		private String label;
		private String toolTip;
		private EFileMenu(String label, String toolTip) {
			this.label = label;
			this.toolTip = toolTip;
		}
		
		public String getLabel() {
			return this.label;
		}
		
		public String getToolTip() {
			return this.toolTip;
		}
	}
	
	public enum EEditMenu {
		eUndo("되돌리기", "이전 상태로 되돌립니다"),
		eRedo("다시하기", "복구합니다"),
		eCut("자르기", "도형을 잘라냅니다"),
		eCopy("복사하기", "도형을 복사합니다"),
		ePaste("붙여넣기", "도형을 붙여넣습니다"),
		eGroup("그룹핑하기", "도형을 묶습니다"),
		eUngroup("그룹핑 해제하기", "도형을 해체합니다");
		
		private String label;
		private String toolTip;
		
		private EEditMenu(String label, String toolTip) {
			this.label = label;
			this.toolTip = toolTip;
		}
		
		public String getLabel() {
			return this.label;
		}
		
		public String getToolTip() {
			return this.toolTip;
		}
	}
}
