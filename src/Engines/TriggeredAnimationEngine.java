package Engines;

import java.awt.Graphics2D;

import Global.Functions;

public class TriggeredAnimationEngine {
	private long startFlag;
	private long elapsed;
	private long maintain;
	
	private boolean userTrigger = false;
	
	private long lastTouch = 0, lastRelease = 0;
	
	public TriggeredAnimationEngine(long maintain) {
		this.startFlag = 0;
		this.maintain = maintain;
	}
	
	public void update() {
		this.elapsed = System.currentTimeMillis() - this.startFlag;
	}
	
	//Override this or Overwrite on PagePanel
	public void draw(Graphics2D g) {
	}
	
	public double getProcessRate() {
		if(this.elapsed>this.maintain)return 1;
		return (double)elapsed/(double)maintain;
	}
	
	public double getProcessRate(boolean fadeIn) {
		long LastElapsed;
		double returns;
		if(fadeIn) {
			LastElapsed = System.currentTimeMillis() - this.lastRelease;
			savedProcess[0] = RateCal(savedProcess[1] + RateCal((double)LastElapsed/(double)maintain));
			return savedProcess[0];
		}
		LastElapsed = System.currentTimeMillis() - this.lastTouch;
		savedProcess[1] = RateCal(savedProcess[0] - RateCal((double)LastElapsed/(double)maintain));
		return savedProcess[1];
	}
	
	public long getElapsedTime() {
		return this.elapsed;
	}

	//fade in, fade out animation - true
	//just fade out or fade in animation - false
	public void start() {
		this.startFlag = System.currentTimeMillis();
		this.userTrigger = true;
	}
	
	public void start(boolean Pressing) {
		if(Pressing)
			this.lastTouch = System.currentTimeMillis();
		else
			this.lastRelease = System.currentTimeMillis();
		this.start();
	}
	
	public void stop() {
		this.startFlag = 0;
	}
	
	public boolean isRunning(boolean isUserTriggered) {
		if(isUserTriggered) return this.userTrigger;
		return this.elapsed <= this.maintain;
	}
	
	public void forceTermination() {
		this.userTrigger = false;
	}
	
	private double RateCal(double db) {
		if(db>1)return 1;
		if(db<0)return 0;
		return db;
	}
	
	Functions ff = new Functions();
	private double savedProcess[] = {0,0};
}
