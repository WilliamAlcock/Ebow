package GameEngine;

import VMQ.Quaternion;
import VMQ.Vec3;

public class BulletGenerator {

	private float timeSinceLastFire;
	private float rateOfFire;
	private Bullet bullet;
	private Movement movement;
	private Vec3 position;
	private Quaternion rotation;
	private boolean rotate = false;
	
	/*
	 * factory class to generate a bullet
	 */
	public BulletGenerator(Bullet bullet) {
		this.rateOfFire = 1;										// default is 1 per second
		this.bullet = bullet;
		this.timeSinceLastFire = rateOfFire + 0.001f;
	}
	
	public Bullet generateBullet() {
		if (readyToFire()) {
			timeSinceLastFire = 0;
			Bullet retBullet = bullet.copy(position);
			retBullet.addMovement(movement);
			if (rotate) retBullet.setRotation(rotation);
			return retBullet;
		}
		return null;
	}	
	
	public void setRotationOff() {
		this.rotate = false;
	}
	
	public void setRotation(Quaternion rotation) {
		this.rotate = true;
		this.rotation = rotation;
	}
	
	public void addMovement(Movement movement) {
		this.movement = movement;
	}
	
	public void setPosition(Vec3 position) {
		this.position = position;
	}
	
	public boolean readyToFire() {
		return timeSinceLastFire>rateOfFire;
	}
	
	public float getCockingPosition() {
		float nextFire = (timeSinceLastFire/(rateOfFire/100))*2;
		if (nextFire>=200) return 0;
		if (nextFire>100) return 200-nextFire;
		return nextFire;
	}
	
	public void tick (float timeSinceLastTick) {
		this.timeSinceLastFire += timeSinceLastTick;
	}
	
	public void setRateOfFire(float rateOfFire) {
		this.rateOfFire = rateOfFire;
	}
}
