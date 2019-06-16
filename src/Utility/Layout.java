package Utility;

public class Layout {
	private int w, h;
	
	public Layout(int pw, int ph) {
		this.setWidth(pw);
		this.setHeight(ph);
	}
	
	public void setLayout(int pw, int ph) {
		this.setWidth(pw);
		this.setHeight(ph);
	}

	public int getWidth() {
		return w;
	}

	public void setWidth(int w) {
		this.w = w;
	}

	public int getHeight() {
		return h;
	}

	public void setHeight(int h) {
		this.h = h;
	}
	
	public String getPosByString() {
		return String.format("(%d, %d)", w, h);
	}
}
