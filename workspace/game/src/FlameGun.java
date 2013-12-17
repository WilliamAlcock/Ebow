import java.util.List;

import VMQ.Vec3;

public class FlameGun extends Weapon implements Fires{

	public FlameGun() {
		super(new Vec3(), 0, 100, new Vec3(-7.2f,0,-2), 20, 1);
		setFollowAxis(0,false);
	}

	@Override
	public String getType() {
		return "FlameGun";
	}

	@Override
	public void setRateOfFire(float rateOfFire) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fireWeapon(float timeSinceLastTick, List<InPlayObj> fireList) {
		// TODO Auto-generated method stub
		
	}
}
