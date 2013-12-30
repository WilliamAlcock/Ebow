package GameEngine;
import VMQ.Vec3;

public class EnemyShip extends Enemy{
	
	public EnemyShip(Vec3 position,float speed,float rotSpeed,float activationDistance) {
		super(position, speed, rotSpeed,activationDistance,  5,1); 	//health 5 damage 1
	}

	// firing code to adapt just incase i decide i want it to fire
	
	/*		long timeNow = System.currentTimeMillis();
	if ((timeNow-timeOfLastFire)>1000) {
		DLinkedList bulletMovement = new DLinkedList();
		float[] bulletVector = Rotation.rotate2DCounter(0, 0, 0, -1, getRotation().getY());
		bulletMovement.addFirst(new MovementLinear(bulletVector[0],bulletVector[1],0.0f,60.0f));
		Bullet retBullet = new BulletRed(new Vector3(getPosition().getX(),getPosition().getY(),getPosition().getZ()),100.0f,bulletMovement.getHead());
		retBullet.getRotation().setY(getRotation().getY());
		timeOfLastFire = timeNow;
		return retBullet;
	}		*/		
	
	@Override
	public String getType() {
		return "EnemyShip";
	}
}

