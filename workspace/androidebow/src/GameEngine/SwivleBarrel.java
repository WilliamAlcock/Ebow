package GameEngine;
import VMQ.Vec3;
import java.util.List;


public class SwivleBarrel extends Enemy implements Fires{

	private BulletGenerator bulletGenerator;
	private Enemy parent;
	
	public SwivleBarrel(Vec3 position,float trackSpeed,InPlayObj toTrack,float rateOfFire,Enemy parent) {
		super(position,0, trackSpeed,0,5,1);
		this.parent = parent;
		addMovement(new MovementTrack(toTrack));
		LaserRound laser = new LaserRound(new Vec3(),60.0f);
		laser.setColor(new Vec3(1.0f,0.5f,0.5f));
		this.bulletGenerator = new BulletGenerator(laser);
	}
	
	@Override
	public void tick(float timeSinceLastTick,Vec3 scrollingVector) {
		super.tick(timeSinceLastTick, scrollingVector);
		setPosition(parent.getPosition());
	}
	
	@Override
	public void fireWeapon(float timeSinceLastTick,List<InPlayObj> fireList) {
		bulletGenerator.tick(timeSinceLastTick);
		Vec3 zVec = getRotation().GetZVector().normalize().round(4);
		if (bulletGenerator.readyToFire()) {
			bulletGenerator.setPosition(getPosition());
//			bulletGenerator.setRotation(getRotation().copy());
			bulletGenerator.addMovement(new MovementLinear(zVec,60.0f));
			fireList.add(bulletGenerator.generateBullet());
		}		
	}

	@Override
	public String getType() {
		return "SwivleBarrel";
	}

	@Override
	public boolean isFinished() {
		return parent.isFinished();
	}

	@Override
	public void setRateOfFire(float rateOfFire) {
		bulletGenerator.setRateOfFire(rateOfFire);
	}
}
