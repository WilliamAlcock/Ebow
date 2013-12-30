package GameEngine;
import VMQ.Vec3;


public abstract class HUDObjects extends GameObj {

	public HUDObjects(Vec3 position) {
		super(position);
		setColor(new Vec3(1.0f,0.0f,0.0f));	
		setColorObject(true);
	}
	
	@Override
	public DISPLAYTYPE getDisplayType() {
		return DISPLAYTYPE.TRANSPARENT_ONE;
	}
}
