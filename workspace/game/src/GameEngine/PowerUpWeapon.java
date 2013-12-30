package GameEngine;
import VMQ.Vec3;

public class PowerUpWeapon extends PowerUp{

	private Weapon weapon;
	
	public PowerUpWeapon(Vec3 position, float speed,float activationDistance,Weapon weapon) {
		super(position, speed, activationDistance);
		this.weapon = weapon;
	}

	public void handleCollision(InPlayObj objCollidingWith) {
		if (objCollidingWith.getCategory().equals("PlayerPart")) {
			weapon.fit(((PlayerPart)objCollidingWith).getPlayer());
		} else {
			objCollidingWith.decHealth(1);
		}
		super.handleCollision(objCollidingWith);
	}
}
