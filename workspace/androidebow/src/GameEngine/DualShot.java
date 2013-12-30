package GameEngine;

import java.util.List;

import VMQ.Vec3;

public class DualShot extends Weapon implements Fires{

	private BulletGenerator bulletGenerator1;
	private BulletGenerator bulletGenerator2;
	
	public DualShot() {
		super(new Vec3(), 0, 0, new Vec3(0,0,6), 20, 1);
		setFollowRotation(true);
		Bullet myBullet = new LaserRectangle(new Vec3(),100);
		bulletGenerator1 = new BulletGenerator(myBullet);
		bulletGenerator2 = new BulletGenerator(myBullet);		
	}

	@Override
	public String getType() {
		return "DualShot";
	}

	@Override
	public void fireWeapon(float timeSinceLastTick,List<InPlayObj> fireList) {
		bulletGenerator1.tick(timeSinceLastTick);
		bulletGenerator2.tick(timeSinceLastTick);
		if (bulletGenerator1.readyToFire()) {
			bulletGenerator1.setPosition(new Vec3(getPosition().getX()-1.0f,getPosition().getY()+5.0f,getPosition().getZ()));
			bulletGenerator1.addMovement(new MovementLinear(getRotation().GetZVector(),60.0f));
			fireList.add(bulletGenerator1.generateBullet());
		}
		if (bulletGenerator2.readyToFire()) {
			bulletGenerator2.setPosition(new Vec3(getPosition().getX()+1.0f,getPosition().getY()+5.0f,getPosition().getZ()));
			bulletGenerator2.addMovement(new MovementLinear(getRotation().GetZVector(),60.0f));
			fireList.add(bulletGenerator2.generateBullet());
		}
	}
	
	@Override
	public void setRateOfFire(float rateOfFire) {
		bulletGenerator1.setRateOfFire(rateOfFire);
		bulletGenerator2.setRateOfFire(rateOfFire);
	}
}
