package GameEngine;
import VMQ.Quaternion;
import VMQ.Vec3;

public class MovementMirror extends Movement {

	private InPlayObj toMirror;
	private Quaternion facingQuat;
	private Vec3 movementVector;
	
	private Vec3 facing;
	
	public MovementMirror(InPlayObj toMirror,Vec3 facing) {
		super(0.0f);
		this.toMirror = toMirror;
		this.facing = new Vec3(facing.getX(),facing.getY(),0.0f).normalize();
		float facingAngle = InPlayObj.ORIGINALQUAT.GetZVector().getAngleBetween(this.facing);
		this.facingQuat = InPlayObj.ORIGINALQUAT.multiply(new Quaternion(facingAngle,InPlayObj.ORIGINALQUAT.GetZVector().multiply(-1))).normalize();
		this.movementVector = this.facingQuat.GetXVector(); //this.facing.crossProduct(new Vector3(0,0,1));
	}

	public boolean move(InPlayObj toMove, float timeSinceLastTick,Vec3 scrollingVector) {
		Vec3 p1 = toMove.getPosition();
		Vec3 p2 = new Vec3(toMirror.getPosition().getX(),toMirror.getPosition().getY(),p1.getZ());
		
		toMove.setRotation(facingQuat.normalize());
		
		if (toMove.getPosition().getZ()!=toMirror.getPosition().getZ()) {
			/*
			 * P1 + aV1 = P2 + bV2
			 * aV1 = (P2-P1) + bV2
			 * a(V1xV2) = (P2-P1) x V2
			 */
			
			Vec3 v1 = this.facing;
			Vec3 v2 = this.movementVector;
			
			Vec3 p2subp1crossv2 = p2.sub(p1).crossProduct(v2);
			Vec3 v1crossv2 = v1.crossProduct(v2);
			float a = p2subp1crossv2.getMagnitude()/v1crossv2.getMagnitude();
			
			Vec3 newP2 = p1.add(v1.multiply(a));
			newP2.setZ(toMirror.getPosition().getZ());
			
			Vec3 newP2Vector = newP2.sub(p1);
			
			float angleBetween = toMove.getRotation().GetZVector().getAngleBetween(newP2Vector);
			
			Vec3 crossP = toMove.getRotation().GetZVector().crossProduct(newP2Vector).normalize();
			
			if (Math.abs(angleBetween)>1) {
				toMove.rotateTimeBased(timeSinceLastTick,crossP);
			}
		}
		
		Vec3 crossP = this.facing.crossProduct(p2.sub(p1).normalize());
		
		// Move and Bank
		if (crossP.getZ()>0.01) {
			toMove.bankLeft();
			toMove.moveTimeBased(timeSinceLastTick, movementVector);
		} else if (crossP.getZ()<-0.01) {
			toMove.bankRight();
			toMove.moveTimeBased(timeSinceLastTick, movementVector.multiply(-1));
		} else {
			toMove.levelOff();
		}
		
		this.facingQuat = toMove.getRotation();
		toMove.adjustPositionForScrolling(timeSinceLastTick, scrollingVector);
		return false;
	}
	

}
