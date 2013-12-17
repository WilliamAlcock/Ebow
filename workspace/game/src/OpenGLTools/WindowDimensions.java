package OpenGLTools;

import java.util.Observable;

import VMQ.Mat4x4;

public class WindowDimensions extends Observable{
	
	private float width;
	private float height;
	private float fovy;
	private float zFar;
	private float zNear;
	private Mat4x4 perspectiveMatrix;
	
	public WindowDimensions(float width,float height,float fovy,float zFar,float zNear) {
		this.width = width;
		this.height = height;
		this.fovy = fovy;
		this.zFar = zFar;
		this.zNear = zNear;
		setPerspectiveMatrix();
	}
	
	public void setWidth(int width) {
		this.width = width;
		setPerspectiveMatrix();
	}
	
	public void setHeight(int height) {
		this.height = height;
		setPerspectiveMatrix();
	}
	
	public void setFovy(float fovy) {
		this.fovy = fovy;
		setPerspectiveMatrix();
	}
	
	public void setZFar(float zFar) {
		this.zFar = zFar;
		setPerspectiveMatrix();
	}
	
	public void setZNear(float zNear) {
		this.zNear = zNear;
		setPerspectiveMatrix();
	}
	
	public float getZFar() {
		return zFar;
	}
	
	public float getZNear() {
		return zNear;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getFovyDeg() {
		return fovy;
	}
	
	public float getFovyRad() {
		return (float) Math.toRadians(fovy);
	}
	
	public float getAspect() {
		return width/height;
	}
	
	public float getMaxXPos(float zCalc) {
		return (float) (zCalc*Math.tan(getFovyRad()/2)*getAspect());
	}
	
	public float getMaxYPos(float zCalc) {
		return (float) (zCalc*Math.tan(getFovyRad()/2));		
	}
	
	public void setPerspectiveMatrix() {
		float f = (float) (1/Math.tan(getFovyRad()/2));
		float g = (zFar+zNear)/(zFar-zNear);
		float h = (2*zFar*zNear)/(zFar-zNear);
		
		// Column matrix
		this.perspectiveMatrix = new Mat4x4(new float[] {f/getAspect(),0,0,0,
														0,f,0,0,
														0,0,g,-1,
														0,0,h,0});
		this.setChanged();
	}
	
	public Mat4x4 getPerspectiveMatrix() {
		return perspectiveMatrix;
	}
}
