package GameEngine;
import VMQ.Vec3;

public abstract class Enemy extends InPlayObj{
	
	private int points;
	
	public Enemy(Vec3 position, float speed, float rotSpeed,float activationDistance, int health,int damage) {
		super(position, speed, rotSpeed, activationDistance,health,damage);
		this.points = health*5;
	}
	
	public int getPoints() {
		return points;
	}
	
	@Override
	public Explosion getExplosion(Vec3 dimensions) {
		return new Explosion(getPosition(),15,dimensions,true,true,true,true,true,true);
	}
	
	@Override
	public DISPLAYTYPE getDisplayType() {
		return DISPLAYTYPE.SOLID;
	}
	
	@Override
	public String getCategory() {
		return "Enemy";
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
}
