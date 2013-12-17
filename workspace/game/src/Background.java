
import java.util.LinkedList;

import VMQ.Vec3;

public class Background {

	private Wallpaper wallpaper;
	private StarMachine starMachine;
	private LinkedList<GameObj> backgroundObjects = new LinkedList<GameObj>();
	
	public Background(float wallpaperWidth,float wallpaperLength) {
		this.wallpaper = new Wallpaper(new Vec3(0,0,-99),new Vec3(0,-1,0),wallpaperLength,backgroundObjects);
		this.starMachine = new StarMachine(new Vec3(wallpaperWidth,wallpaperLength/1,-90),new Vec3(0,-2,0),2,20,wallpaperLength*2,100,backgroundObjects);
	}
	
	public void tick(float timeSinceLastTick,Vec3 scrollingDirection) {
		wallpaper.tick(timeSinceLastTick*scrollingDirection.getY());
		starMachine.tick(timeSinceLastTick*scrollingDirection.getY());
	}
	
	public LinkedList<GameObj> getObjects() {
		return backgroundObjects;
	}
}
