import VMQ.Vec3;

public class RoundSparks extends Particle{

	public RoundSparks(Vec3 position) {
		super(position);
		// Random fade speed
		setFade((float)((100*Math.random())+35)/20f+0.003f);
		// Random speed between -2.5 and 2.5 
		getDirection().setX((float) ((5 * Math.random()) - 2.5f) );
		getDirection().setY((float) ((5 * Math.random()) - 2.5f) );
		getDirection().setZ((float) ((5 * Math.random()) - 2.5f) );
		// Set slowDown
		setSlowDown(5.0f);
		// Set growthRate
		setGrowthRate(5.0f);
		// Set the color
		setColor(new Vec3(1.0f,0.8f,0.0f));
		// Set the color change
		setColorChange(new Vec3(1.0f,0.8f,0.0f));
	}

	public String getType() {
		return "RoundSparks";
	}

}
