package GameEngine;
import VMQ.Vec3;

public class SwivleShip extends Enemy implements hasComponents{

	private SwivleBarrel swivleBarrel;
	
	public SwivleShip(Vec3 position, float speed,float activationDistance, InPlayObj toTrack, float trackSpeed) {
		super(position, speed, 0, activationDistance, 5,1);
		this.swivleBarrel = new SwivleBarrel(position,trackSpeed,toTrack,1,this);
	}
	
	public String getType() {
		return "SwivleShip";
	}
	
	public InPlayObj[] getComponentObjects() {
		return new InPlayObj[] {swivleBarrel};
	}

	public void decHealth(int decAmount) {
		super.decHealth(decAmount);
		swivleBarrel.decHealth(decAmount);
	}
	
	public boolean fires() {
		return false;
	}
}
