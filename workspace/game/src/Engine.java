import java.util.LinkedList;

import VMQ.Vec3;

public class Engine implements ParticleGenerator{

	private int numberOfParticles;
	private Vec3 offset;
	private Vec3 shipPosition;
	private Vec3 initialGravity;
	private Vec3 gravity;
	private int minPower,maxPower,power;
	private MySwitch isOn;
	private MySwitch killed;
	
	public Engine(Vec3 shipPosition,Vec3 offset,Vec3 gravity,int numberOfParticles,int minPower,int maxPower) {
		this.numberOfParticles = numberOfParticles;
		this.offset = offset;
		this.shipPosition = shipPosition;
		this.initialGravity = gravity.normalize();
		this.minPower = minPower;
		this.maxPower = maxPower;
		this.power = minPower;
		this.gravity = initialGravity.copy();
		this.isOn = new MySwitch(true);
		this.killed = new MySwitch(false);
		updateGravity();
	}
	
	public void kill() {
		killed.set(true);
	}
	
	public void off() {
		isOn.set(false);
	}
	
	public void on() {
		isOn.set(true);	
	}		
	
	public void increasePower(int increase) {
		if (power<maxPower) {
			power += increase;
		}
		updateGravity();
	}
	
	public void decreasePower(int decrease) {
		if (power>minPower) {
			power -= decrease;
		}
		updateGravity();
	}
	
	public void updateGravity() {
		Vec3 newGravity = initialGravity.multiply(power);
		gravity.setX(newGravity.getX());
		gravity.setY(newGravity.getY());
		gravity.setZ(newGravity.getZ());
	}

	@Override
	public void generate(LinkedList<Particle> particles) {
		for (int i=0;i<numberOfParticles;i++) {
			particles.add(new JetFire(shipPosition,offset.copy(),gravity,1f,isOn,killed));
		}
	}
}
