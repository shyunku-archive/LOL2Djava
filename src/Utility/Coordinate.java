package Utility;

public class Coordinate {
	private int x, y;
	
	public Coordinate(int px, int py) {
		this.setX(px);
		this.setY(py);
	}
	
	public void setPos(int px, int py) {
		this.setX(px);
		this.setY(py);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public String getPosByString() {
		return String.format("(%d, %d)", x,y);
	}
}
