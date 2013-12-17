package VMQ;

import java.io.Serializable;
import java.util.Arrays;

public class Vec2 implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float[] vec2 = new float[2];
	
	public Vec2(float[] vec2) {
		if (vec2.length!=2) {
	        throw new IllegalArgumentException("float array length must be 2");
		} else {
			this.vec2 = vec2;
		}
	}
	
	public Vec2(float x,float y) {
		this.vec2[0] = x;
		this.vec2[1] = y;
	}
	
	public Vec2() {
	}
	
	public float getX() {
		return vec2[0];
	}
	
	public float getY() {
		return vec2[1];
	}
	
	public void setX(float x) {
		this.vec2[0] = x;
	}
	
	public void setY(float y) {
		this.vec2[1] = y;
	}
	
	@Override
	public String toString() {
		return vec2[0]+","+vec2[1];
	}
	
	public int getRows() {
		return 2;
	}
	
	public Vec2 round (int places) {
		float power = (float)Math.pow(10, places);
		float x = (Math.round((vec2[0]*power)))/power;
		float y = (Math.round((vec2[1]*power)))/power;
		return new Vec2(x,y);
	}
	
	public Vec2 add(Vec2 vector) {
		return new Vec2(this.vec2[0]+vector.getX(),this.vec2[1]+vector.getY());
	}
	
	public Vec2 sub(Vec2 vector) {
		return new Vec2(this.vec2[0]-vector.getX(),this.vec2[1]-vector.getY());
	}

	public Vec2 multiply(float m) {
		return new Vec2(this.vec2[0]*m,this.vec2[1]*m);
	}
		
	// Matrix multiplication insert here
	
	public float getMagnitude() {
		return (float) Math.sqrt((this.vec2[0]*this.vec2[0])+(this.vec2[1]*this.vec2[1]));
	}
		
	public Vec2 normalize() {
		return new Vec2(this.vec2[0]/getMagnitude(),this.vec2[1]/getMagnitude());
	}
	
	public float dotProduct(Vec2 vector) {
		// will not work unless normalized
		return (this.vec2[0]*vector.getX())+(this.vec2[1]*vector.getY());
	}
	
	public float getAngleBetween(Vec2 vector) {
		// will not work unless normalized
		return (float) Math.toDegrees(Math.acos((dotProduct(vector)/(this.getMagnitude()*vector.getMagnitude()))));
	}
	
	public Vec2 copy() {
		return new Vec2(this.vec2[0],this.vec2[1]);
	}
	
	public Vec3 getVec3(float z) {
		return new Vec3(this,z);
	}
	
	public float[] getArray() {
		return vec2;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj==null) return false;
		if (obj==this) return true;
		if (!(obj instanceof Vec2)) return false;
		
		return Arrays.equals(vec2,((Vec2)obj).vec2);
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(vec2); 
	}
}
