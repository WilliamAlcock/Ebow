package VMQ;

import java.io.Serializable;
import java.util.Arrays;

public class Vec3 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float[] vec3 = new float[3];
	
	public Vec3(float[] vec3) {
		if (vec3.length!=3) {
	        throw new IllegalArgumentException("float array length must be 3");
		} else {
			this.vec3 = vec3;
		}
	}
	
	public Vec3(float x,float y,float z) {
		this.vec3[0] = x;
		this.vec3[1] = y;
		this.vec3[2] = z;
	}
	
	public Vec3(Vec2 vec2,float z) {
		this.vec3[0] = vec2.getX();
		this.vec3[1] = vec2.getY();
		this.vec3[2] = z;
	}
	
	public Vec3() {
	}
	
	public float getX() {
		return vec3[0];
	}
	
	public float getY() {
		return vec3[1];
	}
	
	public float getZ() {
		return vec3[2];
	}		
	
	public void setX(float x) {
		vec3[0] = x;
	}
	
	public void setY(float y) {
		vec3[1] = y;
	}
	
	public void setZ(float z) {
		vec3[2] = z;
	}
	
	public String toString() {
		return vec3[0]+","+vec3[1]+","+vec3[2];
	}
	
	public int getRows() {
		return 3;
	}
	
	public Vec3 round(int places) {
		float power = (float)Math.pow(10, places);
		float x = (Math.round((vec3[0]*power)))/power;
		float y = (Math.round((vec3[1]*power)))/power;
		float z = (Math.round((vec3[2]*power)))/power;
		return new Vec3(x,y,z);
	}
	
	public Vec3 add(Vec3 vector) {
		return new Vec3(this.vec3[0]+vector.getX(),this.vec3[1]+vector.getY(),this.vec3[2]+vector.getZ());
	}
	
	public Vec3 sub(Vec3 vector) {
		return new Vec3(this.vec3[0]-vector.getX(),this.vec3[1]-vector.getY(),this.vec3[2]-vector.getZ());
	}
	
	public Vec3 multiply(float m) {
		return new Vec3(this.vec3[0]*m,this.vec3[1]*m,this.vec3[2]*m);
	}
	
	// Matrix multiplication insert here
	
	public float getMagnitude() {
		return (float) Math.sqrt((this.vec3[0]*this.vec3[0])+(this.vec3[1]*this.vec3[1])+(this.vec3[2]*this.vec3[2]));
	}
		
	public Vec3 normalize() {
		return new Vec3(this.vec3[0]/getMagnitude(),this.vec3[1]/getMagnitude(),this.vec3[2]/getMagnitude());
	}
	
	public float dotProduct(Vec3 vector) {
		// will not work unless normalized
		return (this.vec3[0]*vector.getX())+(this.vec3[1]*vector.getY())+(this.vec3[2]*vector.getZ());
	}
	
	public Vec3 crossProduct(Vec3 vector) {
		return new Vec3((this.vec3[1] * vector.getZ()) - (this.vec3[2] * vector.getY()),
							(this.vec3[2] * vector.getX()) - (this.vec3[0] * vector.getZ()),
							(this.vec3[0] * vector.getY()) - (this.vec3[1] * vector.getX()));
	}
	
	public float getAngleBetween(Vec3 vector) {
		// will not work unless normalized
		return (float) Math.toDegrees(Math.acos((dotProduct(vector)/(this.getMagnitude()*vector.getMagnitude()))));
	}
	
	public Vec3 copy() {
		return new Vec3(this.vec3[0],this.vec3[1],this.vec3[2]);
	}
	
	public Vec2 getVec2() {
		return new Vec2(this.vec3[0],this.vec3[1]);
	}
	
	public Vec4 getVec4(float w) {
		return new Vec4(this,w);
	}
	
	public float[] getAsArray() {
		return vec3;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj==this) return true;
		System.out.println("Vector not same object");
		if (obj==null) return false;
		System.out.println("Vector not null");
		if (!(obj instanceof Vec3)) return false;
		System.out.println("Vector same type");
		System.out.println("Vector "+Arrays.equals(vec3,((Vec3)obj).vec3));
		return Arrays.equals(vec3,((Vec3)obj).vec3);
		
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(vec3); 
	}
}
