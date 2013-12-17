import VMQ.Vec3;

public abstract class PowerUp extends InPlayObj{
	
	public PowerUp(Vec3 position, float speed,float activationDistance) {
		super(position, speed, 100.0f, activationDistance, 1, 0); // Health 1 damage 0
	}
	
	public String getType() {
		return "PowerUp";
	}
	
	public DISPLAYTYPE getDisplayType() {
		return DISPLAYTYPE.SOLID;
	}
	
	public String getCategory() {
		return "PowerUp";
	}
	
	public void handleCollision(InPlayObj objCollidingWith) {
		decHealth(1);
	}
	
	public void tick(float timeSinceLastTick,Vec3 scrollingVector) {
		super.processMovement(timeSinceLastTick,scrollingVector);
		super.rotateTimeBased(timeSinceLastTick,new Vec3(0,1,0));
	}
	
	public Explosion getExplosion(Vec3 dimensions) {
		return null;
	}
	
	public boolean fires() {
		return false;
	}
}
