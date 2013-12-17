package VMQ;
import java.io.Serializable;

import VMQ.Vec3;

public class Quaternion implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float w;
	private float x;
	private float y;
	private float z;
	
	public final static Quaternion Identity = new Quaternion(1,0,0,0);
	
	public Quaternion(float angle,Vec3 axis) {
		this.w = (float) Math.cos(Math.toRadians(angle/2.0f));
		this.x = (float) (axis.getX() * Math.sin(Math.toRadians(angle/2.0f)));
		this.y = (float) (axis.getY() * Math.sin(Math.toRadians(angle/2.0f)));
		this.z = (float) (axis.getZ() * Math.sin(Math.toRadians(angle/2.0f)));
	}
	
	public Quaternion(float w,float x,float y,float z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Quaternion(Vec3 axis) {
		this.w = 0;
		this.x = axis.getX();
		this.y = axis.getY();
		this.z = axis.getZ();
	}
	
	public Quaternion() {
		this.w = 1;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getZ() {
		return z;
	}
	
	public float getW() {
		return w;
	}			
	
	public Quaternion round(int places) {
		float power = (float)Math.pow(10, places);
		float w = (Math.round((this.w*power)))/power;
		float x = (Math.round((this.x*power)))/power;
		float y = (Math.round((this.y*power)))/power;
		float z = (Math.round((this.z*power)))/power;
		return new Quaternion(w,x,y,z);
	}
	
	public Quaternion setFromEulerAngles (float roll, float pitch, float yaw) {
	    yaw = (float)Math.toRadians(yaw);
	    pitch = (float)Math.toRadians(pitch);
	    roll = (float)Math.toRadians(roll);
	    float halfRoll = roll * 0.5f;
	    float rollSin = (float)Math.sin(halfRoll);
	    float rollCos = (float)Math.cos(halfRoll);
	    float halfPitch = pitch * 0.5f;
	    float pitchSin = (float)Math.sin(halfPitch);
	    float pitchCos = (float)Math.cos(halfPitch);
	    float halfYaw = yaw * 0.5f;
	    float yawSin = (float)Math.sin(halfYaw);
	    float yawCos = (float)Math.cos(halfYaw);
	    w = ((rollCos * pitchCos) * yawCos) + ((rollSin * pitchSin) * yawSin);
	    x = ((rollSin * pitchCos) * yawCos) - ((rollCos * pitchSin) * yawSin);
	    y = ((rollCos * pitchSin) * yawCos) + ((rollSin * pitchCos) * yawSin);
	    z = ((rollCos * pitchCos) * yawSin) - ((rollSin * pitchSin) * yawCos);	    
	    return this;
	}
	
	public float getMagnitude() {
		return (float) Math.sqrt((w*w)+(x*x)+(y*y)+(z*z));
	}
	
	public Quaternion normalize() {
		return new Quaternion (w/getMagnitude(),x/getMagnitude(),y/getMagnitude(),z/getMagnitude());
	}
	
	public Quaternion add(Quaternion q) {
		return new Quaternion (this.w+q.w,this.x+q.x,this.y+q.y,this.z+q.z);
	}
	
	public Quaternion sub(Quaternion q) {
		return new Quaternion (this.w-q.w,this.x-q.x,this.y-q.y,this.z-q.z);
	}
	
	public Quaternion multiply(Quaternion q) {
		float w = this.w*q.w - this.x*q.x - this.y*q.y - this.z*q.z;
		float x = this.w*q.x + this.x*q.w + this.y*q.z - this.z*q.y;
		float y = this.w*q.y - this.x*q.z + this.y*q.w + this.z*q.x;
		float z = this.w*q.z + this.x*q.y - this.y*q.x + this.z*q.w;
		return new Quaternion(w,x,y,z);
	}
	
	public Quaternion multiply(float scalar) {
		return new Quaternion(scalar*w,scalar*x,scalar*y,scalar*z);
	}
	
	public Quaternion divide(Quaternion q) {
		return this.getInverse().multiply(q);
	}
	
	public Quaternion getConjugate() {
		return new Quaternion(this.w,-this.x,-this.y,-this.z);
	}
	
	public Quaternion getInverse() {
		Quaternion retQuat = this.normalize();
		return new Quaternion(retQuat.w,-retQuat.x,-retQuat.y,-retQuat.z);
	}
	
	public Mat4x4 rotationMatrix() {
		Quaternion normalized = this.normalize();
		float x2 = normalized.x * normalized.x;
		float y2 = normalized.y * normalized.y;
		float z2 = normalized.z * normalized.z;
		float xy = normalized.x * normalized.y;
		float xz = normalized.x * normalized.z;
		float yz = normalized.y * normalized.z;
		float wx = normalized.w * normalized.x;
		float wy = normalized.w * normalized.y;
		float wz = normalized.w * normalized.z;
		
		float[] newMat  =  {1.0f - 2.0f * (y2 + z2), 2.0f * (xy + wz), 2.0f * (xz - wy), 0.0f,
							2.0f * (xy - wz), 1.0f - 2.0f * (x2 + z2), 2.0f * (yz + wx), 0.0f,
							2.0f * (xz + wy), 2.0f * (yz - wx), 1.0f - 2.0f * (x2 + y2), 0.0f,
							0.0f, 0.0f, 0.0f, 1.0f};
		return new Mat4x4(newMat); 
	}	
	
	public float dotProduct (Quaternion q) {
		return (this.w*q.w)+(this.x*q.x)+(this.y*q.y)+(this.z*q.z);
	}
	
	public Vec3 GetZVector() {
	    return new Vec3( 2 * (x * z + w * y), 
	                    	2 * (y * z - w * x),
	                    	1 - 2 * (x * x + y * y));
	}
	 
	public Vec3 GetYVector() {
	    return new Vec3( 2 * (x * y - w * z), 
	                    	1 - 2 * (x * x + z * z),
	                    	2 * (y * z + w * x));
	}
	 
	public Vec3 GetXVector() {
	    return new Vec3( 1 - 2 * (y * y + z * z),
	                    	2 * (x * y + w * z),
	                    	2 * (x * z - w * y));
	}
	
	@Override
	public boolean equals(final Object o) {
		if (o == null) return false;
		if (this == o) return true;
		if (!(o instanceof Quaternion)) return false;
		final Quaternion q = (Quaternion) o;
		return this.w == q.w && this.x == q.x && this.y == q.y && this.z == q.z; 
	}
	
	@Override
	public int hashCode() {
		int hashcode = 17;
		hashcode = 31 * hashcode + Float.floatToIntBits(w);
		hashcode = 31 * hashcode + Float.floatToIntBits(x);
		hashcode = 31 * hashcode + Float.floatToIntBits(y);
		hashcode = 31 * hashcode + Float.floatToIntBits(z);
		return hashcode;
	}
	
	@Override
	public String toString() {
		return "w: "+w+" x: "+x+" y: "+y+" z: "+z;
	}
	
	public Quaternion copy() {
		return new Quaternion(this.w,this.x,this.y,this.z);
	}
}
