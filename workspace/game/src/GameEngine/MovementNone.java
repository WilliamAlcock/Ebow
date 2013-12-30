package GameEngine;
import VMQ.Vec3;

public class MovementNone extends Movement{

	public MovementNone() {
		super(0.0f);
	}
	
	public boolean move(InPlayObj toMove, float timeSinceLastTick,Vec3 scrollingVector) {
		toMove.adjustPositionForScrolling(timeSinceLastTick, scrollingVector);
		return false;
	}
}
