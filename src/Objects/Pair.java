package Objects;

public class Pair<pr1, pr2> {
	private pr1 first;
	private pr2 second;
	
	public Pair(pr1 p1, pr2 p2) {
		this.first = p1;
		this.second = p2;
	}
	
	public Pair() {
		this.first = null;
		this.second = null;
	}
	
	public pr1 getFirst() {
		return this.first;
	}
	
	public pr2 getSecond() {
		return this.second;
	}
}
