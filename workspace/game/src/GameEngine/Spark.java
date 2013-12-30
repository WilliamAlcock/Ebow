package GameEngine;
import VMQ.Quaternion;
import VMQ.Vec3;

public class Spark extends Particle{
	
	public Spark(Vec3 position) {
		super(position);
		// Random fade speed
		setFade((float)((100*Math.random())+20)/20f+0.003f);
		// Random speed between -1 and 1 
		getDirection().setX((float) ((80 * Math.random()) - 40f) );
		getDirection().setY((float) ((80 * Math.random()) - 40f) );
		getDirection().setZ((float) ((80 * Math.random()) - 40f) );
		// Set rotation
		setRotation(new Quaternion(new Vec3(-1,0,0).getAngleBetween(getDirection().normalize()),new Vec3(-1,0,0).crossProduct(getDirection().normalize())));
		// Set slowDown
		setSlowDown(0.0f);
		// Set growthRate
		setGrowthRate(0f);
		// Set the color
		setColor(new Vec3(1.0f,1.0f,0.2f));
		// Set the color change
		setColorChange(new Vec3(0.0f,0.0f,0.0f));
	}
	
	public String getType() {
		return "Spark";
	}
	
}
