import VMQ.Quaternion;
import VMQ.Vec3;

public class MovementCircular extends Movement {

	private Vec3 center;
	private Vec3 rotationVector;
	private Vec3 centerAdjustment;
	
	public MovementCircular(Vec3 center,Vec3 rotationVector,float endDegree) {
		super(endDegree);
		this.center = center;
		this.rotationVector = rotationVector;
		this.centerAdjustment = new Vec3();
	}
	
	public Vec3 getAdjustment() {
		return centerAdjustment;
	}
	
	public void adjust(Vec3 movementAdjustment) {
		center = center.add(movementAdjustment);
	}
	
	public boolean move(InPlayObj toMove,float timeSinceLastTick,Vec3 scrollingVector) {
		// Adjusting the positions for the scrolling Vector
		Vec3 oldPosition = toMove.getPosition().copy();
		toMove.adjustPositionForScrolling(timeSinceLastTick, scrollingVector);
		Vec3 adjustment = toMove.getPosition().sub(oldPosition);
		center = center.add(adjustment);
		centerAdjustment = centerAdjustment.add(adjustment);
		
		if (toMove.getCurDist()>=getEndDistance()) {
			return true;
		} else {
			Vec3 positionVector = toMove.getPosition().sub(center);			
			float distanceToMove = (toMove.getSpeed()*timeSinceLastTick);
			float angle = (float)Math.toDegrees(Math.asin((distanceToMove/2)/positionVector.getMagnitude()));
			
			Quaternion rotationQuat = new Quaternion(angle*2,rotationVector);
			Quaternion positionQuat = new Quaternion(positionVector);
			Quaternion newPositionQuat = rotationQuat.multiply(positionQuat).multiply(rotationQuat.getInverse());
			
			toMove.getPosition().setX(center.getX()+newPositionQuat.getX());
			toMove.getPosition().setY(center.getY()+newPositionQuat.getY());
			toMove.getPosition().setZ(center.getZ()+newPositionQuat.getZ());
			toMove.setCurDist(toMove.getCurDist()+angle*2);
			
			return false;
		}
	}
}
