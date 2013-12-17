import VMQ.Vec3;

public class ShockWave extends Particle{
	
	public ShockWave(Vec3 position) {
		super(position);
		// Random fade speed
		setFade(4.5f);
		// Random speed between -1 and 1 
		getDirection().setX(0.0f);
		getDirection().setY(0.0f);
		getDirection().setZ(0.0f);
		// Set slowDown
		setSlowDown(0.0f);
		// Set growthRate
		setGrowthRate(10f);
		// Set the color
		setColor(new Vec3(1.0f,1.0f,0.3f));
		// Set the color change
		setColorChange(new Vec3(0.0f,0.0f,0.0f));
	}

	public String getType() {
		return "ShockWave";
	}
	
}
