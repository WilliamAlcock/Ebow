package GameEngine;
import VMQ.Vec3;

public abstract class Particle extends GameObj implements Comparable<Particle>{
	
	private float fade;
	private Vec3 direction;
	private float growthRate;
	private float slowDown;
	private Vec3 colorChange;
	private Vec3 gravity;
	
	public Particle(Vec3 position) {
		super(position);
		this.fade = 0.0f;
		
		this.growthRate = 0.0f;
		this.slowDown = 0.0f;
		
		this.colorChange = new Vec3();
		this.direction = new Vec3();

		this.gravity = new Vec3();
	}
	
	public Vec3 getColorChange() {
		return colorChange;
	}		
	
	public void setColorChange(Vec3 colorChange) {
		this.colorChange = colorChange;
	}		
	
	public float getGrowthRate() {
		return growthRate;
	}

	public void setGrowthRate(float growthRate) {
		this.growthRate = growthRate;
	}

	public float getSlowDown() {
		return slowDown;
	}

	public void setSlowDown(float slowDown) {
		this.slowDown = slowDown;
	}
	
	public void setFade(float fade) {
		this.fade = fade;
	}
	
	public float getFade() {
		return fade;
	}	
	
	public void setDirection(Vec3 direction) {
		this.direction = direction;
	}
	
	public Vec3 getDirection() {
		return direction;
	}
	
	public Vec3 getGravity() {
		return gravity;
	}
	
	public void setGravity(Vec3 gravity) {
		this.gravity = gravity;
	}	
	
	public float getAlpha() {
		return getLife();
	}
	
	public void move(float timeSinceLastTick) {
		// Move by Direction
		Vec3 inc = getDirection().multiply(timeSinceLastTick);
		// Add gravity
		inc = inc.add(getGravity().multiply(timeSinceLastTick));
		// Grow
		Vec3 incScale = getScale().multiply(getGrowthRate()*timeSinceLastTick);
		// Slow Down
		float slowDown = (getSlowDown()*timeSinceLastTick)/1000f;
		inc = inc.sub(new Vec3(slowDown,slowDown,slowDown));
		
		setPosition(getPosition().add(inc));
		
		setLife(getLife() - (fade*timeSinceLastTick));
		setScale(getScale().add(incScale));
		
		setColor(getColor().sub(getColorChange().multiply(timeSinceLastTick)));
	}
	
	public DISPLAYTYPE getDisplayType() {
		return DISPLAYTYPE.TRANSPARENT;
	}
	
	public int compareTo(Particle p) {
		if (getPosition().getZ()>p.getPosition().getZ()) {
			return 1;
		} else if (getPosition().getZ()<p.getPosition().getZ()) {
			return -1;
		} else {
			return 0;
		}
    }
}
