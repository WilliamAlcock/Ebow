package VMQ;

import java.io.Serializable;
import java.util.Arrays;

public class Vec4 implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float[] vec4 = new float[4];
			
	public Vec4(float[] vec4) {
		if (vec4.length!=4) {
	        throw new IllegalArgumentException("float array length must be 3");
		} else {
			this.vec4 = vec4;
		}
	}
	
	public Vec4(float x,float y,float z,float w) {
		this.vec4[0] = x;
		this.vec4[1] = y;
		this.vec4[2] = z;
		this.vec4[3] = w;
	}
	
	public Vec4(Vec3 vec3,float w) {
		this.vec4[0] = vec3.getX();
		this.vec4[1] = vec3.getY();
		this.vec4[2] = vec3.getZ();
		this.vec4[3] = w;
	}
	
	public Vec4() {
	}
	
	public float getX() {
		return vec4[0];
	}
	
	public float getY() {
		return vec4[1];
	}
	
	public float getZ() {
		return vec4[2];
	}		
	
	public float getW() {
		return vec4[3];
	}
	
	public void setX(float x) {
		vec4[0] = x;
	}
	
	public void setY(float y) {
		vec4[1] = y;
	}
	
	public void setZ(float z) {
		vec4[2] = z;
	}
	
	public void setW(float w) {
		vec4[3] = w;
	}
	
	public String toString() {
		return vec4[0]+","+vec4[1]+","+vec4[2]+","+vec4[3];
	}
	
	public int getRows() {
		return 4;
	}
	
	public Vec4 round(int places) {
		float power = (float)Math.pow(10, places);
		float x = (Math.round((vec4[0]*power)))/power;
		float y = (Math.round((vec4[1]*power)))/power;
		float z = (Math.round((vec4[2]*power)))/power;
		float w = (Math.round((vec4[3]*power)))/power;
		return new Vec4(x,y,z,w);
	}
	
	public Vec4 add(Vec4 vector) {
		return new Vec4(this.vec4[0]+vector.getX(),this.vec4[1]+vector.getY(),this.vec4[2]+vector.getZ(),this.vec4[3]+vector.getW());
	}
	
	public Vec4 sub(Vec4 vector) {
		return new Vec4(this.vec4[0]-vector.getX(),this.vec4[1]-vector.getY(),this.vec4[2]-vector.getZ(),this.vec4[3]-vector.getW());
	}
	
	public Vec4 multiply(float m) {
		return new Vec4(this.vec4[0]*m,this.vec4[1]*m,this.vec4[2]*m,this.vec4[3]*m);
	}
	
	public Vec4 multiply(Mat4x4 mat4x4) {
		Vec4 retVec4 = new Vec4();
		retVec4.setX(this.vec4[0]*mat4x4.getPos(0,0)+(this.vec4[1]*mat4x4.getPos(0,1))+(this.vec4[2]*mat4x4.getPos(0,2))+(this.vec4[3]*mat4x4.getPos(0,3)));
		retVec4.setY(this.vec4[0]*mat4x4.getPos(1,0)+(this.vec4[1]*mat4x4.getPos(1,1))+(this.vec4[2]*mat4x4.getPos(1,2))+(this.vec4[3]*mat4x4.getPos(1,3)));
		retVec4.setZ(this.vec4[0]*mat4x4.getPos(2,0)+(this.vec4[1]*mat4x4.getPos(2,1))+(this.vec4[2]*mat4x4.getPos(2,2))+(this.vec4[3]*mat4x4.getPos(2,3)));
		retVec4.setW(this.vec4[0]*mat4x4.getPos(3,0)+(this.vec4[1]*mat4x4.getPos(3,1))+(this.vec4[2]*mat4x4.getPos(3,2))+(this.vec4[3]*mat4x4.getPos(3,3)));
		return retVec4;
	}
	
	public float getMagnitude() {
		return (float) Math.sqrt((this.vec4[0]*this.vec4[0])+(this.vec4[1]*this.vec4[1])+(this.vec4[2]*this.vec4[2])+(this.vec4[3]*this.vec4[3]));
	}
		
	public Vec4 normalize() {
		return new Vec4(this.vec4[0]/getMagnitude(),this.vec4[1]/getMagnitude(),this.vec4[2]/getMagnitude(),this.vec4[3]/getMagnitude());
	}
	
	public float dotProduct(Vec4 vector) {
		// will not work unless normalized
		return (this.vec4[0]*vector.getX())+(this.vec4[1]*vector.getY())+(this.vec4[2]*vector.getZ())+(this.vec4[3]*vector.getW());
	}
	
	public Vec4 copy() {
		return new Vec4(this.vec4[0],this.vec4[1],this.vec4[2],this.vec4[3]);
	}
	
	
	public Vec3 getVec3() {
		return new Vec3(vec4[0],vec4[1],vec4[2]);
	}
	
	public float[] getAsArray() {
		return vec4;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj==null) return false;
		if (obj==this) return true;
		if (!(obj instanceof Vec4)) return false;
		
		return Arrays.equals(vec4,((Vec4)obj).vec4);
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(vec4); 
	}
}
