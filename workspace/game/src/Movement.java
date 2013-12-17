import VMQ.Vec3;

public abstract class Movement{
	
	private float endDistance;
	
	public Movement(float endDistance) {
		this.endDistance = endDistance;
	}
	
	public float getEndDistance() {
		return endDistance;
	}		
	
	public Vec3 getAdjustment() {
		return null;
	}
	
	public void adjust(Vec3 movementAdjustment) {}
	
	public abstract boolean move(InPlayObj toMove,float timeSinceLastTick,Vec3 scrollingVector);
}
