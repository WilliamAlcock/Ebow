package OpenGLTools;

import VMQ.Vec3;

public class Light {
	private Vec3 ambientColor;
	private Vec3 diffuseColor;
	private Vec3 diffuseDirection;
	private float diffuseStrength;
	private Vec3 eyeDirection;
	
	public Light(Vec3 ambientColor,Vec3 diffuseColor,Vec3 diffuseDirection,float diffuseStrength,Vec3 eyeDirection) {
		this.ambientColor = ambientColor;
		this.diffuseColor = diffuseColor;
		this.diffuseDirection = diffuseDirection;
		this.diffuseStrength = diffuseStrength;
		this.eyeDirection = eyeDirection;
	}

	public Vec3 getAmbientColor() {
		return ambientColor;
	}

	public Vec3 getDiffuseColor() {
		return diffuseColor;
	}

	public Vec3 getDiffuseDirection() {
		return diffuseDirection;
	}

	public float getDiffuseStrength() {
		return diffuseStrength;
	}

	public Vec3 getEyeDirection() {
		return eyeDirection;
	}
}
