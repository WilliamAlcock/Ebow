package GameEngine;
import VMQ.Vec3;

public class MissileLauncher extends Weapon implements hasComponents{

	private MissileLauncherBarrel missileLauncherBarrel;
	
	public MissileLauncher(CollisionDetector collider) {
		super(new Vec3(), 0, 100, new Vec3(3.98473f,-0.98575f,0),20, 1);
		this.missileLauncherBarrel = new MissileLauncherBarrel(collider); 
	}

	@Override
	public String getType() {
		return "MissileLauncher";
	}

	@Override
	// Might be possible to remove this - not sure till i write the code to destroy the weapons.
	public InPlayObj[] getComponentObjects() {
		return new InPlayObj[] {missileLauncherBarrel};
	}
}
