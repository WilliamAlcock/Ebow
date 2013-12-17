import VMQ.Vec3;

public class WallGunTurret extends Enemy implements hasComponents{
	
	private GunBarrel wallGunBarrel;
	
	public WallGunTurret (Vec3 position, float activationDistance, float trackSpeed, InPlayObj toTrack) {
		super(position, 0.0f,trackSpeed,activationDistance, 5,1); 	//health 5 damage 1
		this.wallGunBarrel = new GunBarrel(position,100.0f,this,new Vec3(0,0,3.5f),1);
		this.addMovement(new MovementTrack(toTrack));
	}

	public void tick(float timeSinceLastTick,Vec3 scrollingVector) {
		super.tick(timeSinceLastTick, scrollingVector);
		wallGunBarrel.track();
	}

	public String getType() {
		return "WallGunTurret";
	}

	public boolean isFinished() {
		return false;
	}
	
	public InPlayObj[] getComponentObjects() {
		return new InPlayObj[] {wallGunBarrel};
	}
	
	public void decHealth(int decAmount) {
		super.decHealth(decAmount);
		wallGunBarrel.decHealth(decAmount);
	}
	
	public boolean fires() {
		return false;
	}
}
