
import VMQ.Vec3;

public class LaserRectangle extends Bullet{
	
	public LaserRectangle(Vec3 position, float speed) {
		super(position, speed, 0.0f,1);			// damage 1
		setColor(new Vec3(0.5f,0.5f,1.0f));			// default colour is blue
	}
	
	@Override
	public String getType() {
		return "LaserRectangle";
	}
	
	@Override
	public boolean useObjectColor() {
		return true;
	}

	@Override
	public Bullet copy(Vec3 position) {
		LaserRectangle retLaser = new LaserRectangle(position, getSpeed()); 
		retLaser.setColor(this.getColor()); 
		return retLaser;
	}
}
