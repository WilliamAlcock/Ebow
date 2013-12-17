
import VMQ.Vec3;

public class Alien1 extends Enemy {
	
	public Alien1(Vec3 position, float speed,float rotSpeed,float activationDistance ) {
		super(position, speed, rotSpeed,activationDistance, 5,1); 	//health 5 damage 1
	}

	@Override
	public void tick(float timeSinceLastTick,Vec3 scrollingVector) {
		super.tick(timeSinceLastTick, scrollingVector);
		super.rotateTimeBased(timeSinceLastTick,new Vec3(0,1,0));
	}

	@Override
	public String getType() {
		return "Alien1";
	}
}

