package GameEngine;
import java.util.Observable;

import VMQ.Quaternion;
import VMQ.Vec3;


public abstract class GameObj extends Observable{
	private Vec3 position;
	private Quaternion rotation;
	private Vec3 scale;
	
	private boolean colorObject = false;
	private Vec3 color;
	private float life;
	
	public static enum DISPLAYTYPE{SOLID,TRANSPARENT_ONE,TRANSPARENT,TEXT};
	
	public GameObj(Vec3 position) {
		this.position = position;
		this.rotation = new Quaternion();
		this.scale = new Vec3(1,1,1);
		this.life = 1.0f;
		this.color = new Vec3(1,1,1);
	}

	public boolean useObjectColor() {
		return colorObject;
	}
	
	public void setColorObject(boolean colorObject) {
		this.colorObject = colorObject;
	}
	
	public void adjustPositionForScrolling(float timeSinceLastTick,Vec3 scrollingVector) {
		Vec3 newPosition = position.add(scrollingVector.multiply(timeSinceLastTick));
		this.position.setX(newPosition.getX());
		this.position.setY(newPosition.getY());
		this.position.setZ(newPosition.getZ());
	}
	
	public Vec3 getPosition() {
		return position;
	}
	
	public void setPosition(Vec3 position) {
		this.position = position;
	}

	public Quaternion getRotation() {
		return rotation;
	}
	
	public void setRotation(Quaternion q) {
		this.rotation = q;
	}
	
	public Vec3 getScale() {
		return this.scale;
	}
	
	public void setScale(Vec3 scale) {
		this.scale = scale;
	}
	
	public float getLife() {
		return life;
	}
	
	public void setLife(float life) {
		this.life = life;
	}
	
	public Vec3 getColor() {
		return color;
	}
	
	public void setColor(Vec3 color) {
		this.color = new Vec3(checkColorCode(color.getX()),checkColorCode(color.getY()),checkColorCode(color.getZ()));
	}
	
	private float checkColorCode(float code) {
		if (code<0) {
			return 0.0f;
		} else if (code>255) {
			return 255.0f;
		} else {
			return code;
		}
	}
	
	public abstract String getType();
	
	public abstract DISPLAYTYPE getDisplayType();
}
