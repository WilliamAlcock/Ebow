package GameEngine;
import VMQ.Vec3;
import java.util.LinkedList;

public class Wallpaper {

	private Vec3 position;
	private Vec3 direction;
	private float length;
	private float distanceBeforeFlip;
	private WallpaperPane bottomPane;
	private WallpaperPane topPane;
	
	public Wallpaper(Vec3 position,Vec3 direction,float length,LinkedList<GameObj> backgroundObjects) {
		this.position = position;
		this.direction = direction;
		this.length = length;
		this.distanceBeforeFlip = length*2;
		bottomPane = new WallpaperPane(position);		
		topPane = new WallpaperPane(new Vec3(position.getX(),position.getY()+length*2,position.getZ()));
		backgroundObjects.add(bottomPane);
		backgroundObjects.add(topPane);
	}
	
	public void tick(float timeSinceLastTick) {
		Vec3 newPosition = bottomPane.getPosition().add(direction.multiply(timeSinceLastTick));
		if (timeSinceLastTick>0) {
			distanceBeforeFlip = distanceBeforeFlip - bottomPane.getPosition().sub(newPosition).getMagnitude();
		} else {
			distanceBeforeFlip = distanceBeforeFlip + bottomPane.getPosition().sub(newPosition).getMagnitude();
		}
		if (distanceBeforeFlip<0) {
			distanceBeforeFlip = length*2;				
			bottomPane.setPosition(this.position);
		} else {
			bottomPane.setPosition(newPosition);
		}
		topPane.setPosition(new Vec3(bottomPane.getPosition().getX(),bottomPane.getPosition().getY()+length*2,bottomPane.getPosition().getZ()));
	}
}
