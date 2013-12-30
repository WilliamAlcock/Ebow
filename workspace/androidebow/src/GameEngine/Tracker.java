package GameEngine;
import VMQ.Quaternion;
import VMQ.Vec3;

public class Tracker {

	private Vec3 offset;
	private InPlayObj toTrack;
	private InPlayObj parent;
	private Quaternion lastTrackAngle;
	private boolean followAxis[] = new boolean[3];
	private boolean followRotation;
	
	public Tracker(InPlayObj parent,InPlayObj toTrack,Vec3 offset) {
		this.toTrack = toTrack;
		this.offset = offset;
		this.parent = parent;
		this.lastTrackAngle = toTrack.getRotation().copy();
		for (int i=0;i<3;i++) {
			setFollowAxis(i,true);
		}
	}
	
	public void setOffset(Vec3 offset) {
		this.offset = offset;
	}
	
	public Vec3 getOffset() {
		return offset;
	}
	
	public Quaternion getLastTrackAngle() {
		return lastTrackAngle;
	}
	
	public void setFollowAxis (int axis,boolean followAxis) {
		if ((axis>=0) && (axis<3)) {
			this.followAxis[axis] = followAxis;
		}
	}
	
	public void setFollowRotation(boolean followRotation) {
		this.followRotation = followRotation;
	}
	
	public void track() {
		// offset position along X vector
		Vec3 newPos = new Vec3(toTrack.getPosition().getX(),toTrack.getPosition().getY(),toTrack.getPosition().getZ());
		Vec3 xAxis = new Vec3(-1,0,0);
		Vec3 yAxis = new Vec3(0,1,0);
		Vec3 zAxis = new Vec3(0,0,1);
		if (followAxis[0]) {
			xAxis = toTrack.getRotation().GetXVector();
		}
		if (followAxis[1]) {
			yAxis = toTrack.getRotation().GetYVector();
		}
		if (followAxis[2]) {
			zAxis = toTrack.getRotation().GetZVector();			
		}		
		newPos = newPos.add(xAxis.multiply(offset.getX()));
		// offset position along Y vector		
		newPos = newPos.add(yAxis.multiply(offset.getY()));
		// offset position along Z vector		
		newPos = newPos.add(zAxis.multiply(offset.getZ()));		
		// set the position
		parent.setPosition(newPos);
		if (followRotation) {
			// rotate to correct angle			
			Quaternion rotationDifference = toTrack.getRotation().multiply(lastTrackAngle.getInverse());
			parent.setRotation(rotationDifference.multiply(parent.getRotation()));	
			lastTrackAngle = toTrack.getRotation().copy();
		}
	}
}