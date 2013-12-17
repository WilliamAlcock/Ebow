import java.util.LinkedList;

import VMQ.Vec3;


public class Explosion implements ParticleGenerator {
	private Vec3 position;
	private int numOfParticles;
	private Vec3 radius;
	private boolean flash;
	private boolean shockWave;
	private boolean sparks;
	private boolean smokeTrails;
	private boolean debris;
	private boolean roundSparks;
	
	public Explosion(Vec3 position,int numOfParticles,Vec3 radius,boolean flash,boolean shockWave,boolean sparks,boolean smokeTrails,boolean debris,boolean roundSparks) {
		this.position = position;
		this.numOfParticles = numOfParticles;
		this.radius = radius;
		this.flash = flash;
		this.shockWave = shockWave;
		this.sparks = sparks;
		this.smokeTrails = smokeTrails;
		this.debris = debris;
		this.roundSparks = roundSparks;
	}
		
	@Override
	public void generate(LinkedList<Particle> particles) {
		// Smoke
		for (int i=0; i<numOfParticles;i++) {
			float xpos = (float)((radius.getX()*2f)*Math.random())-radius.getX();
			float ypos = (float)((radius.getY()*2f)*Math.random())-radius.getY();
			float zpos = (float)((radius.getZ()*2f)*Math.random())-radius.getZ();
			particles.add(new Smoke(new Vec3(position.getX()+xpos,position.getY()+zpos,position.getZ()+ypos)));	
		}							
		if (flash) {	
			// Flash
			for (int i=0;i<numOfParticles;i++) {
				float xpos = (float)((radius.getX()*2f)*Math.random())-radius.getX();
				float ypos = (float)((radius.getY()*2f)*Math.random())-radius.getY();
				float zpos = (float)((radius.getZ()*2f)*Math.random())-radius.getZ();
				particles.add(new Flash(new Vec3(position.getX()+xpos,position.getY()+zpos,position.getZ()+ypos)));
			}		
		}
		if (sparks) {
			// Sparks
			for (int i=0;i<numOfParticles;i++) {
				particles.add(new Spark(new Vec3(position.getX(),position.getY(),position.getZ())));
			}
		}		
		if (smokeTrails) {
			// Smoke Trails
			for (int i=0;i<numOfParticles;i++) {
				particles.add(new SmokeTrail(new Vec3(position.getX(),position.getY(),position.getZ())));
			}
		}
		if (debris) {
			// Debris
			for (int i=0;i<numOfParticles;i++) {
				particles.add(new Debris(new Vec3(position.getX(),position.getY(),position.getZ())));
			}
		}
		if (roundSparks) {
			for (int i=0;i<numOfParticles;i++) {
				float xpos = (float)((radius.getX()*2f)*Math.random())-radius.getX();
				float ypos = (float)((radius.getY()*2f)*Math.random())-radius.getY();
				float zpos = (float)((radius.getZ()*2f)*Math.random())-radius.getZ();
				particles.add(new RoundSparks(new Vec3(position.getX()+xpos,position.getY()+zpos,position.getZ()+ypos)));
			}		
		}
		if (shockWave) {
			// Shock Wave
			particles.add(new ShockWave(new Vec3(position.getX(),position.getY(),position.getZ())));
		}			
	} 
}
