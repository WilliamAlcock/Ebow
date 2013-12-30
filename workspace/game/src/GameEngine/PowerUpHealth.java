package GameEngine;
import VMQ.Vec3;

public class PowerUpHealth extends PowerUp{

	private int strength;
	
	public PowerUpHealth(Vec3 position, float speed, float activationDistance,int strength) {
		super(position, speed, activationDistance);
		this.strength = strength;
	}
	
	public void handleCollision(InPlayObj objCollidingWith) {
		if (objCollidingWith.getCategory().equals("PlayerPart")) {
			objCollidingWith.incHealth(strength);
		} else {
			objCollidingWith.decHealth(1);
		}
		super.handleCollision(objCollidingWith);
	}
}
