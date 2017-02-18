package org.usfirst.frc.team5338.robot;

public class Snapshot {
	public long time;
	public double x;
	public double y;
	public double width;

	public Snapshot(long time, double x, double y, double width) {
		this.time = time;
		this.x = x;
		this.y = y;
		this.width = width;
	}
}