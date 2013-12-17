import VMQ.Vec3;

public class MovementFollow extends Movement {
	
	private InPlayObj toFollow;
	private float opposingGravityPercent;
	private float opposingGravityAngle;
	
	public MovementFollow(InPlayObj toFollow,float opposingGravityPercent,float opposingGravityAngle) {
		super(0.0f);
		this.toFollow = toFollow;
		this.opposingGravityPercent = opposingGravityPercent%100;
		this.opposingGravityAngle = opposingGravityAngle%360;
	}
	
	public boolean move(InPlayObj toMove,float timeSinceLastTick,Vec3 scrollingVector) {
		if (toFollow.isAlive() && !toFollow.isFinished()) {
			// vector between toMove and toFollow
			Vec3 vectorToFollow = toFollow.getPosition().sub(toMove.getPosition()).normalize();
			// float angleToMove = toMove.getRotation().GetZVector().getAngleBetween(vectorToFollow);
			Vec3 crossP = toMove.getRotation().GetZVector().crossProduct(vectorToFollow);
			// rotate
			toMove.rotateTimeBased(timeSinceLastTick, crossP);
			// new position
			toMove.moveTimeBased(timeSinceLastTick,vectorToFollow);
			// bank
			if (crossP.getZ()<0) {
				toMove.bankRight();
			} else if (crossP.getZ()>0) {
				toMove.bankLeft();
			} else {
				toMove.levelOff();
			}
			toMove.adjustPositionForScrolling(timeSinceLastTick, scrollingVector);
			return false;
		} else {
			return true;
		}
	}
}
