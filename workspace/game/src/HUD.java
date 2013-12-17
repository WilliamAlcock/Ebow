import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import OpenGLTools.WindowDimensions;
import VMQ.Vec3;

public class HUD implements Observer{

	private LinkedList<GameObj> HUDObjects = new LinkedList<GameObj>();
	private HealthBarBox healthBarBox = new HealthBarBox(new Vec3(0,0,-5));
	private HealthBar healthBar;
	private WindowDimensions windowDimensions; 
	private HashMap<String,Vec3> dimensions;
	private ScoreBoard scoreBoard;
	private GameOverSign gameOverSign;
	
	public HUD(WindowDimensions windowDimensions,HashMap<String,Vec3> dimensions) {
		this.windowDimensions = windowDimensions;
		this.dimensions = dimensions;
		healthBar = new HealthBar(new Vec3(0,0,-5),dimensions.get("HealthBar").getX());
		this.scoreBoard = new ScoreBoard(new Vec3(2,2,-5),0,7,dimensions.get("Digit0").getX());
		for (Digit curDigit: scoreBoard.getDigits()) {
			HUDObjects.add(curDigit);
		}
		HUDObjects.add(healthBar);
		HUDObjects.add(healthBarBox);
		positionObjects();
	}
	
	public boolean tick(float timeSinceLastTick) {
		scoreBoard.tick();
		if (gameOverSign!=null) {
			return gameOverSign.tick(timeSinceLastTick);
		} else {
			return false;
		}
	}
	
	/*
	 * position the objects relative to the window
	 */
	private void positionObjects() {
		float topScreen = windowDimensions.getMaxYPos(5);
		float xBox = windowDimensions.getMaxXPos(-5)+dimensions.get("HealthBarBox").getX()+0.02f;
		float yBox = (topScreen*-1)+dimensions.get("HealthBarBox").getY()+0.02f;
		float xBar = xBox + (float)(dimensions.get("HealthBarBox").getX()/3); 
		float yDigit = topScreen - dimensions.get("Digit0").getY()-0.1f;
		float xDigit = windowDimensions.getMaxXPos(5)-(dimensions.get("Digit0").getX()*scoreBoard.getNumDigits())-0.02f;
		healthBar.setPosition(new Vec3(xBar,yBox,-5));
		healthBarBox.setPosition(new Vec3(xBox,yBox,-5));
		scoreBoard.setPosition(new Vec3(xDigit,yDigit,-5));
	}
	
	/* 
	 * increment the score board
	 */
	public void incScore(int inc) {
		scoreBoard.inc(inc);
	}
	
	@Override
	public void update(Observable o, Object object) {
		if (object instanceof Ship) {
			int health = ((Ship)object).getHealth();
			if (health>0) {
				healthBar.updateHealth(health);
			} else {
				gameOverSign = new GameOverSign(40);
				HUDObjects.add(gameOverSign);
			}
		} else if (object instanceof WindowDimensions) {
			positionObjects();
			healthBar.updatePosition();
		}
	} 
	
	/*
	 * return the objects
	 */
	public LinkedList<GameObj> getObjects() {
		return HUDObjects;
	}
}
