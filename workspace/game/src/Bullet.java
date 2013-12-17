import VMQ.Vec3;

public abstract class Bullet extends InPlayObj{ 
	
	public Bullet(Vec3 position, float speed, float rotSpeed,int damage) {
		super(position,speed,rotSpeed,0,1,damage);			// all bullets have 1 health
	}
	
	@Override
	public Explosion getExplosion(Vec3 dimensions) {
		return new Explosion(getPosition(),1,dimensions,false,false,false,false,false,false);
	}
	
	@Override
	public DISPLAYTYPE getDisplayType() {
		return DISPLAYTYPE.TRANSPARENT_ONE;
	}
	
	@Override
	public String getCategory() {
		return "Bullet";
	}
	
	@Override
	public void handleCollision(InPlayObj objCollidingWith) {
		objCollidingWith.decHealth(getDamage());
		this.decHealth(objCollidingWith.getDamage());
	}
	
	@Override
	public void tick(float timeSinceLastTick,Vec3 scrollingVector) {
		processMovement(timeSinceLastTick,scrollingVector);
	}
	
	public abstract Bullet copy(Vec3 position); 
}