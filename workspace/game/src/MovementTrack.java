import VMQ.Vec3;

public class MovementTrack extends Movement{

	private InPlayObj toTrack;
	
	public MovementTrack(InPlayObj toTrack) {
		super(0.0f);
		this.toTrack = toTrack;
	}
	
	public boolean move(InPlayObj toMove, float timeSinceLastTick,Vec3 scrollingVector) {
		Vec3 zVector = toMove.getRotation().GetZVector();
		Vec3 vectorToTrack = toTrack.getPosition().sub(toMove.getPosition());
		float angleToMove = zVector.getAngleBetween(vectorToTrack);
		Vec3 crossP = zVector.crossProduct(vectorToTrack).normalize();
		toMove.setCurDist(angleToMove);
		toMove.rotateTimeBased(timeSinceLastTick, crossP);		
		toMove.adjustPositionForScrolling(timeSinceLastTick, scrollingVector);
		return false;
	}


	
}

	
