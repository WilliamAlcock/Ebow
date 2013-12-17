import VMQ.Vec3;

public abstract class Weapon extends PlayerPart{

	private Tracker tracker;
	private Vec3 offset;
	
	public Weapon(Vec3 position, float speed, float rotSpeed, Vec3 offset,int health,int damage) {
		super(position, speed, rotSpeed, health, damage);
		this.offset = offset;
	}
	
	public void fit(Player player) {
		setPlayer(player);
		this.tracker = new Tracker(this,player.getShip(),offset);
		tracker.track();
		player.addWeapon(this);
	}
	
	public boolean isFitted() {
		return getPlayer()!=null;
	}
	
	public void setFollowAxis (int axis,boolean followAxis) {
		if (tracker!=null) {
			tracker.setFollowAxis(axis, followAxis);
		}
	}
	
	public void setFollowRotation(boolean followRotation) {
		if (tracker!=null) {
			tracker.setFollowRotation(followRotation);
		}
	}
	
	public Explosion getExplosion(Vec3 dimensions) {
		return new Explosion(getPosition(),10,dimensions,true,true,true,true,true,false);
	}
	
	public Vec3 getOffset() {
		return tracker.getOffset();
	}
	
	public void tick(float timeSinceLastTick,Vec3 scrollingVector) {
		tracker.track();
	}
}
