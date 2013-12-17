import java.util.List;

import VMQ.Vec3;

public class MissileLauncherBarrel extends Weapon implements Fires {

	private BulletGenerator bulletGenerator;
	private float cockingDistance = -2;
	private CollisionDetector collider;
	
	public MissileLauncherBarrel(CollisionDetector collider) {
		super(new Vec3(), 0, 100, new Vec3(3.98473f,-0.98575f,0), 20, 1);
		this.collider = collider;
		LaserRound laser = new LaserRound(new Vec3(),60);
		bulletGenerator = new BulletGenerator(laser);
	}
	
	@Override
	public String getType() {
		return "MissileLauncherBarrel";
	}

	@Override
	public void fireWeapon(float timeSinceLastTick,List<InPlayObj> fireList) {
		if (bulletGenerator.readyToFire()) {
			InPlayObj followObj = collider.getClosestObject(this);
			if (followObj!=null) {
				bulletGenerator.setPosition(getPosition().copy());
				bulletGenerator.addMovement(new MovementFollow(followObj,0,0));
				fireList.add(bulletGenerator.generateBullet());
			}
		} 
	}
	
	@Override
	public void tick(float timeSinceLastTick,Vec3 scrollingVector) {
		bulletGenerator.tick(timeSinceLastTick);
		getOffset().setZ((cockingDistance/100)*bulletGenerator.getCockingPosition());
		super.tick(timeSinceLastTick,scrollingVector);
	}
	
	@Override
	public void setRateOfFire(float rateOfFire) {
		bulletGenerator.setRateOfFire(rateOfFire);
	}
}
