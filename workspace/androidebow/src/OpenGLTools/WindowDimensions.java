package OpenGLTools;

import java.util.Observable;

import VMQ.Mat4x4;
import android.annotation.SuppressLint;
import android.opengl.Matrix;

public class WindowDimensions extends Observable{
	
	private float width;
	private float height;
	private float fovy;
	private float zFar;
	private float zNear;
//	private Mat4x4 perspectiveMatrix;
	private float[] perspectiveMatrix = new float[16];
	
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
	
	@SuppressLint("NewApi")
	public void setPerspectiveMatrix() {
//		float f = (float) (1/Math.tan(getFovyRad()/2));
//		float g = (zFar+zNear)/(zFar-zNear);
//		float h = (2*zFar*zNear)/(zFar-zNear);
		
		// Column matrix
//		this.perspectiveMatrix = new Mat4x4(new float[] {f/getAspect(),0,0,0,
//														0,f,0,0,
//														0,0,g,-1,
//														0,0,h,0});
//		float ratio = (float)width/height;
//		Matrix.frustumM(perspectiveMatrix, 0, -ratio, ratio, -1, 1, zNear, zFar);
		System.out.println("Setting perspective Matrix");
		float identity[] = new float[16];
		float viewMat[] = new float[16];
		float perMat[] = new float[16];
		Matrix.setIdentityM(identity, 0);
		Matrix.setLookAtM(viewMat, 0, 0, 0, -10, 0, 0, 5, 0, 10, 0);
		Matrix.perspectiveM(perMat, 0, getFovyDeg(), getAspect(), zNear, zFar);
		
		Matrix.multiplyMM(perspectiveMatrix, 0, viewMat, 0, identity, 0);
		Matrix.multiplyMM(perspectiveMatrix, 0, perMat, 0, perspectiveMatrix, 0);
		this.setChanged();
		System.out.println("Perspective Matrix: \n"+new Mat4x4(perspectiveMatrix));
	}
	
	public float[] getPerspectiveMatrix() {
		return perspectiveMatrix;
	}
}
