import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import VMQ.Vec3;


public class LiveEnemys {

	private LinkedList<InPlayObj> liveEnemys = new LinkedList<InPlayObj>(); 							// Enemies in play
	private CollisionDetector collider;
	private ParticleEngine explosions;
	private HashMap<String,Vec3> dimensions;
	private HUD hUD;
	

	public LiveEnemys(CollisionDetector collider,HUD hUD,ParticleEngine explosions,HashMap<String,Vec3> dimensions) {
		this.collider = collider;
		this.hUD = hUD;
		this.explosions = explosions;
		this.dimensions = dimensions;
	}
	
	public LinkedList<InPlayObj> getObjects() {
		return liveEnemys;
	}
	
	public void tick(float timeSinceLastTick, Vec3 scrollingVector) {
		Iterator<InPlayObj> it = liveEnemys.iterator();
		ArrayList<InPlayObj> newEnemys = new ArrayList<InPlayObj>();
		while (it.hasNext()) {
			InPlayObj curEnemy = it.next();
			curEnemy.tick(timeSinceLastTick,scrollingVector.multiply(-1));
			if (curEnemy instanceof Fires) {
				((Fires)curEnemy).fireWeapon(timeSinceLastTick,newEnemys);
			}
			if (curEnemy.isFinished()) {
				collider.remove(curEnemy);
				it.remove();
		
			} else if (curEnemy.isAlive()) {
				collider.move(curEnemy);
			} else {
				collider.remove(curEnemy);
				Explosion explosion = curEnemy.getExplosion(dimensions.get(curEnemy.getType()));
				if (curEnemy.getCategory().equals("Enemy")) hUD.incScore(((Enemy)curEnemy).getPoints());
				if (explosion!=null) {
					explosions.add(explosion);
				}
				it.remove();
			}
		}
		for (InPlayObj curObj:newEnemys) {
			add(curObj);
		}
	}
	
	public int size() {
		return liveEnemys.size();
	}
	
	public void add(InPlayObj liveEnemy) {
		liveEnemys.add(liveEnemy);
		collider.add(liveEnemy);
		if (liveEnemy instanceof hasComponents) {
			for (InPlayObj componentObj: ((hasComponents)liveEnemy).getComponentObjects()) {
				add(componentObj);
			}
		}
	}
}
