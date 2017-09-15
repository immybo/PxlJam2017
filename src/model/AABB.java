package model;

import java.awt.Color;
import java.awt.Graphics;

public class AABB {
	public Vector center;
	public Vector extents;
	public Vector velocity;
	public Vector acceleration;
	
	public AABB(double x, double y, double width, double height) {
		this(new Vector(x + width/2, y + height/2), new Vector(width/2, height/2), null, null);
	}
	
	public AABB(Vector center, Vector extents, Vector velocity, Vector acceleration) {
		this.center = center;
		this.extents = extents;
		
		if (velocity != null) {
			this.velocity = velocity;
		} else {
			this.velocity = Vector.zero();
		}
		
		if (acceleration != null) {
			this.acceleration = acceleration;
		} else {
			this.acceleration = Vector.zero();
		}
	}
	
	//public var min(get, never):Vector;
	public Vector min() {
		return new Vector(center.x - extents.x, center.y - extents.y);
	}
	
	//public var max(get, never):Vector;
	public Vector max() {
		return new Vector(center.x + extents.x, center.y + extents.y);
	}
	
	//public var size(get, never):Vector;
	public Vector get_size() {
		return new Vector(extents.x * 2, extents.y * 2);
	}
	
	public boolean isMoving() {
		return velocity.isZero();
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.drawRect((int) min().x, (int) min().y, (int) (2 * extents.x), (int) (2 * extents.y));
	}
	
	public AABB minkowskiDifference(AABB other) {
		Vector topLeft = min().sub(other.max());
		Vector fullSize = get_size().add(other.get_size());
		return new AABB(topLeft.add(fullSize.mult(0.5)), fullSize.mult(0.5), null, null);
	}
	
	public Vector closestPointOnBoundsToPoint(Vector point) {
		// test x first
		double minDist = Math.abs(point.x - min().x);
		Vector boundsPoint = new Vector(min().x, point.y);
		if (Math.abs(max().x - point.x) < minDist) {
			minDist = Math.abs(max().x - point.x);
			boundsPoint = new Vector(max().x, point.y);
		}
		if (Math.abs(max().y - point.y) < minDist) {
			minDist = Math.abs(max().y - point.y);
			boundsPoint = new Vector(point.x, max().y);
		}
		if (Math.abs(min().y - point.y) < minDist) {
			minDist = Math.abs(min().y - point.y);
			boundsPoint = new Vector(point.x, min().y);
		}
		return boundsPoint;
	}
	
	// returns t of intersection point, infinity if they don't intersect
	private double getRayIntersectionFractionOfFirstRay(Vector originA, Vector endA, Vector originB, Vector endB)
	{
		Vector r = endA.sub(originA);
		Vector s = endB.sub(originB);
		
		double numerator = originB.sub(originA).cross(r);
		double denominator = r.cross(s);
		
		if (numerator == 0 && denominator == 0)
		{
			// the lines are co-linear
			// check if they overlap
			/*return	((originB.x - originA.x < 0) != (originB.x - endA.x < 0) != (endB.x - originA.x < 0) != (endB.x - endA.x < 0)) || 
					((originB.y - originA.y < 0) != (originB.y - endA.y < 0) != (endB.y - originA.y < 0) != (endB.y - endA.y < 0));*/
			return Double.POSITIVE_INFINITY;
		}
		if (denominator == 0)
		{
			// lines are parallel
			return Double.POSITIVE_INFINITY;
		}
		
		double u = numerator / denominator;
		double t = (originB.sub(originA).cross(s)) / denominator;
		if ((t >= 0) && (t <= 1) && (u >= 0) && (u <= 1)) {
			//return originA + (r * t);
			return t;
		}
		return Double.POSITIVE_INFINITY;
	}
	
	public double getRayIntersectionFraction(Vector origin, Vector direction) {
		Vector end = origin.add(direction);
		
		double minT = getRayIntersectionFractionOfFirstRay(origin, end, new Vector(min().x, min().y), 
				new Vector(min().x, max().y));
		double x;
		x = getRayIntersectionFractionOfFirstRay(origin, end, new Vector(min().x, max().y), new Vector(max().x, max().y));
		if (x < minT)
			minT = x;
		x = getRayIntersectionFractionOfFirstRay(origin, end, new Vector(max().x, max().y), new Vector(max().x, min().y));
		if (x < minT)
			minT = x;
		x = getRayIntersectionFractionOfFirstRay(origin, end, new Vector(max().x, min().y), new Vector(min().x, min().y));
		if (x < minT)
			minT = x;
		
		// ok, now we should have found the fractional component along the ray where we collided
		return minT;
	}
	
	public static double getIntersectionFraction(AABB boxA, AABB boxB, double dt) {
//		if(!boxA.isMoving()) {
//			return Double.POSITIVE_INFINITY;
//		}
		
		AABB md = boxB.minkowskiDifference(boxA);
		if (md.min().x <= 0 &&
		    md.max().x >= 0 &&
		    md.min().y <= 0 &&
		    md.max().y >= 0)
		{
			return 0;
		}
		else
		{
		    // calculate the relative motion between the two boxes
		    Vector relativeMotion = boxA.velocity.sub(boxB.velocity).mult(dt);
		
		    // ray-cast the relativeMotion vector against the Minkowski AABB
		    return md.getRayIntersectionFraction(Vector.zero(), relativeMotion);
		}
	}
	
	public static void doMove(AABB boxA, AABB boxB, double dt) {
//		if(!boxA.isMoving()) {
//			return;
//		}
		
		AABB md = boxB.minkowskiDifference(boxA);
		if (md.min().x <= 0 &&
		    md.max().x >= 0 &&
		    md.min().y <= 0 &&
		    md.max().y >= 0)
		{
		    // TODO normal discrete collision detection / separation code correct?
			Vector penetrationVector = md.closestPointOnBoundsToPoint(Vector.zero());
			boxA.center = boxA.center.add(penetrationVector);
		}
		else
		{
		    // calculate the relative motion between the two boxes
		    Vector relativeMotion = boxA.velocity.sub(boxB.velocity).mult(dt);
		
		    // ray-cast the relativeMotion vector against the Minkowski AABB
		    double h = md.getRayIntersectionFraction(Vector.zero(), relativeMotion);
		
		    // check to see if a collision will happen this frame
		    // getRayIntersectionFraction returns Math.POSITIVE_INFINITY if there is no intersection
		    if(h < Double.POSITIVE_INFINITY)
		    {
		        // yup, there WILL be a collision this frame
		        // move the boxes appropriately
		        boxA.center = boxA.center.add(boxA.velocity.mult(dt * h));
		        boxB.center = boxB.center.add(boxB.velocity.mult(dt * h));
		
		        // zero the normal component of the velocity
		        // (project the velocity onto the tangent of the relative velocities
		        //  and only keep the projected component, tossing the normal component)
		        Vector tangent = relativeMotion.normalized().tangent();
		        boxA.velocity = tangent.mult(boxA.velocity.dot(tangent));
		        boxB.velocity = tangent.mult(boxB.velocity.dot(tangent));
		    }
		    else
		    {
		        // no intersection, move it along
		        boxA.center = boxA.center.add(boxA.velocity.mult(dt));
		        boxB.center = boxB.center.add(boxB.velocity.mult(dt));
		    }
		}
	}
}
