package GameEngine;
import VMQ.Vec3;


public class GameOverSign extends HUDObjects{

	private float speed;
	
	public GameOverSign(float speed) {
		super(new Vec3(0,0,-75));
		this.speed = speed;
	}
	
	public boolean tick(float timeSinceLastTick) {
		this.getPosition().setZ(Math.min(-5, this.getPosition().getZ()+(speed*timeSinceLastTick)));
		return this.getPosition().getZ()<=-5;
	}

	@Override
	public String getType() {
		return "GameOverSign";
	}

}
