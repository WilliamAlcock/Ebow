import VMQ.Quaternion;
import VMQ.Vec3;

public class SmokeTrail extends Particle{

	private String type = "SmokeTrail"+(int)((2.99*Math.random())+1);
	
	public SmokeTrail(Vec3 position) {
		super(position);
		// Random fade speed
		setFade((float)((100*Math.random())+50)/20f+0.003f);
		// Random speed between -1 and 1 
		getDirection().setX((float) ((50 * Math.random()) - 25f) );
		getDirection().setY((float) ((50 * Math.random()) - 25f) );
		getDirection().setZ((float) ((50 * Math.random()) - 25f) );
		// Set rotation
		setRotation(new Quaternion(new Vec3(-1,0,0).getAngleBetween(getDirection().normalize()),new Vec3(-1,0,0).crossProduct(getDirection().normalize())));
		// Set slowDown
		setSlowDown(0.0f);
		// Set growthRate
		setGrowthRate(2f);
		// Set the color
		setColor(new Vec3(1.0f,1.0f,0.2f));
		// Set the color change
		setColorChange(new Vec3(0.5f,0.5f,0.1f));
	}

	public String getType() {
		return type;
	}
	
}
