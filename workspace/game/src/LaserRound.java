
import VMQ.Vec3;


public class LaserRound extends Bullet{

	public LaserRound(Vec3 position, float speed) {
		super(position, speed, 0.0f, 1);
		setColor(new Vec3(0.5f,0.5f,1.0f));			// default colour is blue
	}

	@Override
	public String getType() {
		return "LaserRound";
	}
	
	@Override
	public boolean useObjectColor() {
		return true;
	}
	
	@Override
	public Bullet copy(Vec3 position) {
		LaserRound retLaser = new LaserRound(position,getSpeed());
		retLaser.setColor(getColor());
		return retLaser;
	}
}
