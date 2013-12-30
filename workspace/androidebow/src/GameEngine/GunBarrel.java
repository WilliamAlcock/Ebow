package GameEngine;
import java.util.List;

import VMQ.Vec3;

public class GunBarrel extends Enemy implements Fires{
	
	private Tracker tracker;
	private BulletGenerator bulletGenerator;
	private InPlayObj parent; 
	
	public GunBarrel (Vec3 position, float rotSpeed,InPlayObj toTrack,Vec3 offset,float rateOfFire) {
		// offset the position of the barrel by -3.5f so it appears at the correct end of the turret		
		super(position,0.0f,rotSpeed, 0.0f, 5,1); 	//health 5 damage 1
		this.addMovement(new MovementNone());
		this.parent = toTrack;
		Bullet laser = new LaserRectangle(new Vec3(),60.0f);
		laser.setColor(new Vec3(1,0.2f,0.2f));
		this.bulletGenerator = new BulletGenerator(laser);
		this.tracker = new Tracker(this,toTrack,offset);
		tracker.setFollowRotation(true);
	}

	@Override
	public void tick(float timeSinceLastTick,Vec3 scrollingVector) {
		super.tick(timeSinceLastTick, scrollingVector);
		rotateTimeBased(timeSinceLastTick,getRotation().GetZVector().normalize().round(4));	
	}
	
	@Override
	public void fireWeapon(float timeSinceLastTick,List<InPlayObj> fireList) {
		bulletGenerator.tick(timeSinceLastTick);
		if (bulletGenerator.readyToFire()) {
			bulletGenerator.setPosition(getPosition());
			bulletGenerator.addMovement(new MovementLinear(getRotation().GetZVector().normalize().round(4),60.0f));
			bulletGenerator.setRotation(tracker.getLastTrackAngle());
			fireList.add(bulletGenerator.generateBullet());
		}
	}					
	
	@Override
	public String getType() {
		return "GunBarrel";
	}
	
	@Override
	public boolean isFinished() {
		return parent.isFinished();
	}

	public void track() {
		tracker.track();
	}
	
	@Override
	public void setRateOfFire(float rateOfFire) {}
}
