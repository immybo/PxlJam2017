package model;

public class Vector {
	public final double x, y;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector sub(Vector other) {
		return new Vector(x-other.x, y-other.y);
	}

	public Vector add(Vector other) {
		return new Vector(x+other.x, y+other.y);
	}

	public Vector mult(double scale) {
		return new Vector(x*scale, y*scale);
	}
	
	public Vector normalized() {
		double mag = mag();
		
		if(mag == 0)
			return zero();
		
		return new Vector(x/mag, y/mag);
	}
	
	public Vector tangent() {
		return new Vector(-y, x);
	}
	
	public double mag() {
		return Math.sqrt(x*x + y*y);
	}

	public double dot(Vector other) {
		return x*other.x + y * other.y;
	}
	
	public double cross(Vector other) {
		return x*other.y - y*other.x;
	}

	public static Vector zero() {
		return new Vector(0, 0);
	}

	public boolean isZero() {
		return x == 0 && y == 0;
	}
}
