package GameEngine;

import VMQ.Vec3;

public class EnemyShip2 extends Enemy implements hasComponents{
	
	private GunBarrel barrel1;
	private GunBarrel barrel2;
	
	public EnemyShip2(Vec3 position, float speed,float rotSpeed, float activationDistance) {
		super(position, speed, rotSpeed, activationDistance,5,1); 	//health 5 damage 1
		this.barrel1 = new GunBarrel(position,150.0f,this,new Vec3(2.25f,0.8f,2.4f),1);
		this.barrel2 = new GunBarrel(position,-150.0f,this,new Vec3(-2.25f,0.8f,2.4f),1);
		barrel1.getScale().setX(0.4f);
		barrel1.getScale().setY(0.4f);
		barrel2.getScale().setX(0.4f);
		barrel2.getScale().setY(0.4f);
	}
	
	@Override
	public void tick(float timeSinceLastTick,Vec3 scrollingVector) {
		super.tick(timeSinceLastTick, scrollingVector);
		barrel1.track();
		barrel2.track();
	}

	@Override
	public String getType() {
		return "EnemyShip2";
	}
	
	@Override
	public InPlayObj[] getComponentObjects() { 
		return new InPlayObj[] {barrel1,barrel2};
	}
	
	@Override
	public void decHealth(int decAmount) {
		super.decHealth(decAmount);
		barrel1.decHealth(decAmount);
		barrel2.decHealth(decAmount);
	}
}
