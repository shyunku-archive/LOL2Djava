package Game.Utility;

public class Gage {
	private double maxGage;
	private double curGage;
	public Gage(double maxGage) {
		this.setMaxGage(maxGage);
		this.setCurGage(maxGage);
	}
	public double getMaxGage() {
		return maxGage;
	}
	public void setMaxGage(double maxGage) {
		this.maxGage = maxGage;
	}
	public double getCurGage() {
		return curGage;
	}
	public void setCurGage(double curGage) {
		this.curGage = curGage;
	}
	public void addGage(double add) {
		this.curGage += add;
		refine();
	}
	public void subtractGage(double add) {
		this.curGage -= add;
		refine();
	}
	public void levelup(double add) {
		this.maxGage += add;
	}
	public void setFull() {
		this.curGage = this.maxGage;
	}
	public void setEmpty() {
		this.curGage = 0;
	}
	
	private void refine() {
		if(this.curGage > this.maxGage)
			this.curGage = this.maxGage;
		if(this.curGage < 0)
			this.curGage = 0;
	}
}
