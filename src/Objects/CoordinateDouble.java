package Objects;

public class CoordinateDouble {
	private double x, y;
	
	public CoordinateDouble(double px, double py) {
		this.setX(px);
		this.setY(py);
	}
	
	public void setPos(double px, double py) {
		this.setX(px);
		this.setY(py);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public String getPosByString() {
		return String.format("(%.2f, %.2f)", x,y);
	}
}
