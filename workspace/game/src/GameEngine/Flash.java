package GameEngine;
import VMQ.Vec3;


public class Flash extends Particle{

	private String type = "Flash"+(int)((3.99*Math.random())+1);
	
	public Flash(Vec3 position) {
		super(position);
		// Random fade speed
		setFade((float)((100*Math.random())+110)/20f+0.003f);
		// Random speed between -1 and 1 
		getDirection().setX((float) ((2 * Math.random()) - 1.0f) );
		getDirection().setY((float) ((2 * Math.random()) - 1.0f) );
		getDirection().setZ((float) ((2 * Math.random()) - 1.0f) );
		// Set slowDown
		setSlowDown(0.0f);
		// Set growthRate
		setGrowthRate(20f);
		// Set the color
		setColor(new Vec3(1.0f,1.0f,0.2f));
		// Set the color change
		setColorChange(new Vec3(0.0f,0.0f,0.0f));
	}

	@Override
	public String getType() {
		return type;
	}
}
