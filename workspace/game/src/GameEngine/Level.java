package GameEngine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import OpenGLTools.WindowDimensions;
import VMQ.Vec3;

public class Level {

	private LinkedList<InPlayObj> idleEnemys = new LinkedList<InPlayObj>();					// Game Objects not yet in play
	private float currentDistance;
	private Player player1;
	private CollisionDetector collider;
	private WindowDimensions windowDimensions;
	private boolean setup;
	
	public Level(Player player1,CollisionDetector collider,WindowDimensions windowDimensions) {
		this.player1 = player1;
		this.collider = collider;
		this.windowDimensions = windowDimensions;

	}
	
	public boolean tick(float timeSinceLastTick) {
		if (!setup) {
			level1();
			Collections.sort(idleEnemys);
			setup = true;
		}
		this.currentDistance += timeSinceLastTick*player1.getScrollingVector().getY();
		if ((idleEnemys.size()>0) && (currentDistance>idleEnemys.getFirst().getActivationDistance())) {
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<InPlayObj> getEnemys() {
		ArrayList<InPlayObj> retEnemys = new ArrayList<InPlayObj>();
		while ((!idleEnemys.isEmpty()) && (currentDistance>idleEnemys.getFirst().getActivationDistance())) {
			retEnemys.add(idleEnemys.removeFirst());
		}
		return retEnemys;
	}
	
	
/*
 * *********************************************************************************	
 */
	private void level1() {
		float yTop = windowDimensions.getMaxYPos(-80)*-1;
		float yBottom = windowDimensions.getMaxYPos(-80);
		float xLeft = windowDimensions.getMaxXPos(-80);
		float xRight = windowDimensions.getMaxXPos(-80)*-1;
		System.out.println("Level dimensions x: "+xLeft+","+xRight+" y: "+yTop+","+yBottom);
		
		// Power up - dual Shot
        PowerUpWeapon powerUp1 = new PowerUpWeapon(new Vec3(-50,20,-80),5.0f,0.0f,new MissileLauncher(collider));
        powerUp1.addMovement(new MovementLinear(new Vec3(1,0,0),100));
		idleEnemys.addLast(powerUp1);
		
		PowerUpWeapon powerUp2 = new PowerUpWeapon(new Vec3(-50,20,-80),5.0f,5.0f,new DualShot());
        powerUp2.addMovement(new MovementLinear(new Vec3(1,0,0),100));
		idleEnemys.addLast(powerUp2);
		
		PowerUpWeapon powerUp3 = new PowerUpWeapon(new Vec3(-50,20,-80),5.0f,10.0f,new RearGun());
        powerUp3.addMovement(new MovementLinear(new Vec3(1,0,0),100));
		idleEnemys.addLast(powerUp3);
		
		// first alien, does a figure of 8 to the left
		// speed 15, rotate 150, activationDistance 5, number 5
		for (Alien1 curAlien : addSnake1(new Vec3(0,yTop,-80),15,150,5,5)) {
			curAlien.addMovement(new MovementCircular(new Vec3(0,yTop-10,-80), new Vec3(0,0,1),180.0f));
			curAlien.addMovement(new MovementCircular(new Vec3(0,yTop-30,-80), new Vec3(0,0,-1),180.0f));
			curAlien.addMovement(new MovementLinear(new Vec3(-1,-1,0).normalize(),50));
			idleEnemys.addLast(curAlien);
		}
		
		// second alien, does a figure of 8 to the right
		// speed 15, rotate 150, activationDistance 10, number 5
		for (Alien1 curAlien : addSnake1(new Vec3(0,yTop,-80),15,150,10,5)) {
			curAlien.addMovement(new MovementCircular(new Vec3(0,yTop-10,-80), new Vec3(0,0,-1),180.0f));
			curAlien.addMovement(new MovementCircular(new Vec3(0,yTop-30,-80), new Vec3(0,0,1),180.0f));
			curAlien.addMovement(new MovementLinear(new Vec3(1,-1,0).normalize(),50));
			idleEnemys.addLast(curAlien);
		}
		// third alien, comes in from the left and then drops in a row
		// speed 15, rotate 150, activationDistance 15, number 8
		int i=0;
		for (Alien1 curAlien : addSnake1(new Vec3(xLeft,yTop-20,-80),15,150,15,8)) {
			curAlien.addMovement(new MovementLinear(new Vec3(1,0,0),80-(i*10)));
			curAlien.addMovement(new MovementLinear(new Vec3(0,-1,0),50));
			idleEnemys.addLast(curAlien);
			i++;
		}
		// enemy mirror ship, top of the screen 
		// speed 15, rotate 150, activationDistance 15
		addEnemyShip2(new Vec3 (0,yTop,-80),15, 150,15,player1.getShip());
		// 2 swivle ships descend from the sides 
		// speed 15, activationDistance 20, trackspeed 100
		SwivleShip swivleShip = new SwivleShip(new Vec3 (xLeft/2,yTop,-80), 15,20,player1.getShip(),100);
		swivleShip.addMovement(new MovementLinear(new Vec3(0,-1,0),20));
        swivleShip.addMovement(new MovementCircular(new Vec3(xLeft/2,yTop-30,-80), new Vec3(0,0,1),1800));
		swivleShip.addMovement(new MovementLinear(new Vec3(0,-1,0),30));
		idleEnemys.addLast(swivleShip);
		swivleShip = new SwivleShip(new Vec3 (xRight/2,yTop,-80), 15,20,player1.getShip(),100);
		swivleShip.addMovement(new MovementLinear(new Vec3(0,-1,0),20));
        swivleShip.addMovement(new MovementCircular(new Vec3(xRight/2,yTop-30,-80), new Vec3(0,0,-1),1800));
        swivleShip.addMovement(new MovementLinear(new Vec3(0,-1,0),30));
        idleEnemys.addLast(swivleShip);
        // 3 Enemy follow ships
        // speed 15, rotation speed 150, activationDistance 30
        addEnemyShip(new Vec3(xLeft/2,yTop,-80),15, 150,30,player1.getShip());
        addEnemyShip(new Vec3(xRight/2,yTop,-80),15, 150,30,player1.getShip());
        addEnemyShip(new Vec3(0,yTop,-80),15, 150,30,player1.getShip());
        
        // Wall guns either side of the screen
    	addWallGun(new Vec3(xLeft+5,yTop,-80), 0, 20,false);
    	addWallGun(new Vec3(xRight-5,yTop,-80), 0, 20,true);
	}
		
	
/*
 * *********************************************************************************	
 */
	
	// This will be replaced by loading a level
	private void addPowerUps() {
		Movement powerUpMovement = new MovementLinear(new Vec3(1,0,0),100);
		PowerUpWeapon powerUp1 = new PowerUpWeapon(new Vec3(-50,0,-80),5.0f,0.0f,new MissileLauncher(collider)); 
        powerUp1.addMovement(powerUpMovement);
        PowerUpWeapon powerUp2 = new PowerUpWeapon(new Vec3(-50,-10,-80),5.0f,0.0f,new RearGun());
        powerUp2.addMovement(powerUpMovement);
        PowerUpWeapon powerUp3 = new PowerUpWeapon(new Vec3(-50,10,-80),5.0f,0.0f,new FlameGun());
        powerUp3.addMovement(powerUpMovement);
        PowerUpWeapon powerUp4 = new PowerUpWeapon(new Vec3(-50,20,-80),5.0f,0.0f,new DualShot());
        powerUp4.addMovement(powerUpMovement);
		idleEnemys.addLast(powerUp1);
		idleEnemys.addLast(powerUp2);
		idleEnemys.addLast(powerUp3);
		idleEnemys.addLast(powerUp4);
	}
	

	
	// This will be replaced by loading a level
	// Needs fixing
	private ArrayList<Alien1> addSnake1(Vec3 position,  float speed, float rotSpeed,float activationDistance,int amount) {
		ArrayList<Alien1> alienList = new ArrayList<Alien1>();
		for (int i=0;i<amount;i++) {
			float tempActivationTimeFromStart=activationDistance+(i*5/speed);
			Alien1 alien1 = new Alien1(new Vec3 (position.getX(),position.getY(),position.getZ()),speed,rotSpeed,tempActivationTimeFromStart);
			alienList.add(alien1);
		}
		return alienList;
	}
	
	// This will be replaced by loading a level
	// Needs fixing
	private void addWallGun(Vec3 position, float activationDistance, float trackSpeed,boolean facing) {
		idleEnemys.addLast(new WallGun(position,activationDistance,player1.getShip(),trackSpeed,facing));
	}
	
	// This will be replaced by loading a level
	private void addSwivleShip(Vec3 position, float speed,float activationDistance, float trackSpeed) {
		SwivleShip swivleShip = new SwivleShip(position,speed,activationDistance,player1.getShip(),trackSpeed);
		swivleShip.addMovement(new MovementCircular(new Vec3(10.0f,10.0f,-80.0f), new Vec3(0,0,1),720.0f));
        swivleShip.addMovement(new MovementCircular(new Vec3(-10.0f,-10.0f,-80.0f), new Vec3(0,0,1),720.0f));
		idleEnemys.addLast(swivleShip);
	}
	
	// This will be replaced by loading a level
	private void addEnemyShip(Vec3 position,float speed, float rotSpeed,float activationDistance,InPlayObj toTrack) {
		EnemyShip enemyShip = new EnemyShip(new Vec3(position.getX(),position.getY(),position.getZ()),
				speed,rotSpeed,activationDistance);
		enemyShip.addMovement(new MovementFollow(toTrack,0.0f,0.0f));
		idleEnemys.addLast(enemyShip);
	}
	
	// This will be replaced by loading a level
	private void addEnemyShip2(Vec3 position,float speed, float rotSpeed,float activationDistance,InPlayObj toMirror) {
		EnemyShip2 enemyShip2 = new EnemyShip2(position,speed,rotSpeed,activationDistance);
		enemyShip2.addMovement(new MovementMirror(toMirror,new Vec3 (0.0f,-0.1f,0)));
		idleEnemys.addLast(enemyShip2);
	}
	
	// This will be replaced by loading a level
	private void addSnakes() {
		for (Alien1 curAlien: addSnake1(new Vec3(0.0f,10.0f,-80.0f),1000.0f,150.0f,5,5)) {
			curAlien.addMovement(new MovementCircular(new Vec3(10.0f,10.0f,-80.0f), new Vec3(0,0,1),10000.0f));
			curAlien.addMovement(new MovementCircular(new Vec3(-10.0f,-10.0f,-80.0f), new Vec3(0,0,1),720.0f));
			idleEnemys.addLast(curAlien);
		}
		
        for (Alien1 curAlien: addSnake1(new Vec3(-10.0f,10.0f,-80.0f),1000.0f,150.0f,10,5)) {
			curAlien.addMovement(new MovementCircular(new Vec3(10.0f,10.0f,-80.0f), new Vec3(0.7f,0.7f,0.0f),180.0f));
			curAlien.addMovement(new MovementCircular(new Vec3(10.0f,-10.0f,-80.0f),new Vec3(0,0,1),720.0f));
			idleEnemys.addLast(curAlien);
		}		
  	}
	
	// This will be replaced by loading a level
	private void addChars() {
		addSnakes();
		addPowerUps();
 
		addEnemyShip2(new Vec3(0.0f,20.0f,-80.0f),5.0f,150.0f,0,player1.getShip());
		addEnemyShip(new Vec3(-10.0f,30.0f,-80.0f),10.0f,150.0f,0,player1.getShip()); 		
		addSwivleShip(new Vec3(0.0f,20.0f,-80.0f),1000.0f,0, 100.0f);							
		Collections.sort(idleEnemys);	
//		addWallGun(new Vector3(30.0f,10.0f,-80.0f),0.0f,10.0f);					
	}	
}
