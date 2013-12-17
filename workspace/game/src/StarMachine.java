import VMQ.Vec3;
import java.util.Iterator;
import java.util.LinkedList;

public class StarMachine {

	private LinkedList<GameObj> backgroundObjects;
	private Vec3 startPosition;
	private Vec3 direction;
	private int numberOfStars;
	private float minSpeed;
	private float maxSpeed;
	private float distanceToTravel;
	
	public StarMachine(Vec3 startPosition,Vec3 direction,float minSpeed,float maxSpeed,float distanceToTravel,int numberOfStars,LinkedList<GameObj> backgroundObjects) {
		this.startPosition = startPosition;
		this.direction = direction;
		this.minSpeed = minSpeed;
		this.maxSpeed = maxSpeed;
		this.distanceToTravel = distanceToTravel;
		this.backgroundObjects = backgroundObjects;
		addStar(numberOfStars,true);
	}
	
	public int getNumberOfStars() {
		return numberOfStars;
	}
	
	public void tick(float timeSinceLastTick) {
		Iterator<GameObj> it = backgroundObjects.iterator();
		int removals=0;
		while (it.hasNext()) {
			GameObj curObj = it.next();
			if (curObj instanceof Star) {
				Star curStar = (Star) curObj;
				curStar.tick(timeSinceLastTick);
				if (curStar.getDistanceTravelled()>distanceToTravel) {
					it.remove();
					removals++;
				}			
			}
		}
		numberOfStars -= removals;
		if (removals>0) addStar(removals,false);
	}
	
	public void addStar(int numberOfStars,boolean randomy) {
		for (int i=0;i<numberOfStars;i++) {
			int speed = (int)(((maxSpeed-minSpeed)*Math.random())+minSpeed);
			float xpos = (float) ((startPosition.getX()*2*Math.random())-startPosition.getX());
			float ypos;
			if (randomy) {
				ypos = (float) ((startPosition.getY()*2*Math.random())-startPosition.getY());
			} else {
				ypos = startPosition.getY();
			}
			backgroundObjects.add(new Star(new Vec3(xpos,ypos,startPosition.getZ()),direction,speed,new Vec3(1,1,1)));			
		}
		this.numberOfStars += numberOfStars;
	}
}
