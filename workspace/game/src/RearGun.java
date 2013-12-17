import VMQ.Vec3;

import java.util.List;

public class RearGun extends Weapon implements Fires{

	private BulletGenerator bulletGenerator;
	
	public RearGun() {
		super(new Vec3(), 0, 100, new Vec3(0,0,-8),20, 1);
		bulletGenerator = new BulletGenerator(new LaserRound(new Vec3(),100));
	}

	@Override
	public String getType() {
		return "RearGun";
	}
	
	@Override
	public void fireWeapon(float timeSinceLastTick,List<InPlayObj> fireList) {
		if (bulletGenerator.readyToFire()) {
			bulletGenerator.setPosition(getPosition().copy());
			bulletGenerator.addMovement(new MovementLinear(getRotation().GetZVector().multiply(-1),60.0f));
			fireList.add(bulletGenerator.generateBullet());
		}
	}
	
	@Override
	public void tick(float timeSinceLastTick,Vec3 scrollingVector) {
		rotateTimeBased(timeSinceLastTick,getRotation().GetZVector());
		bulletGenerator.tick(timeSinceLastTick);
		float size = 1+(bulletGenerator.getCockingPosition()/750);
		setScale(new Vec3(size,size,size));
		super.tick(timeSinceLastTick,scrollingVector);
	}
	
	@Override
	public void setRateOfFire(float rateOfFire) {
		bulletGenerator.setRateOfFire(rateOfFire);
	}

}
