import java.util.HashMap;
import java.util.List;

import OpenGLTools.WindowDimensions;
import VMQ.Vec3;

public class GameEngine {
	private Clock gameClock;											// Clock
	private Player player1;												// Player
	
	private Level level;												// The level
	 
	private CollisionDetector collider;									// Collision detector
	private Background background;										// Background
	private LiveEnemys liveEnemys;										// Live enemys on display
	private ParticleEngine particles;									// Explosions etc
	private HUD hUD;													// Head up display
	
	public GameEngine(HashMap<String,Vec3> dimensions,WindowDimensions windowDimensions) {
		gameClock = new Clock();
		
		particles = new ParticleEngine();
		player1 = new Player(new Vec3(0.0f, 18.0f, -80.0f),25.0f,windowDimensions,dimensions,particles);
		// add the ships engines to the particle engine
		for (ParticleGenerator curEngine: player1.getEngines()) {
			particles.add(curEngine);
		}
		collider = new CollisionDetector(windowDimensions,-80,dimensions,player1);
		level = new Level(player1,collider,windowDimensions);
		background = new Background(dimensions.get("Wallpaper").getX(),dimensions.get("Wallpaper").getY());
		hUD = new HUD(windowDimensions,dimensions);
		windowDimensions.addObserver(hUD);
		windowDimensions.addObserver(collider);
		player1.getShip().addObserver(hUD);
		liveEnemys = new LiveEnemys(collider,hUD,particles,dimensions);
	}
	
	public boolean tick() {
		gameClock.tick();
		player1.tick(gameClock.getTimeSinceLastTick());
		background.tick(gameClock.getTimeSinceLastTick(), player1.getScrollingVector());
		
		if (level.tick(gameClock.getTimeSinceLastTick())) {
			for (InPlayObj curObj: level.getEnemys()) {
				liveEnemys.add(curObj);
			}
		}
		
		liveEnemys.tick(gameClock.getTimeSinceLastTick(), player1.getScrollingVector());
		particles.tick(gameClock.getTimeSinceLastTick(), player1.getScrollingVector());
		collider.tick();
		return hUD.tick(gameClock.getTimeSinceLastTick());
	}
	
	/*
	 * returns a list of all the enemy Objects
	 */
	public List<InPlayObj> getEnemysToDisplay() {
		return liveEnemys.getObjects();
	}
	
	/*
	 * returns a list of all the player Objects
	 */
	public List<InPlayObj> getPlayersToDisplay() {
		return player1.getObjects();
	}
	
	/*
	 * returns a list of all the particles
	 */
	public List<Particle> getParticlesToDisplay() {
		return particles.getParticles();
	}
	
	/*
	 * returns a list off all the Background Objects
	 */
	public List<GameObj> getBackgroundToDisplay() {
		return background.getObjects();
	}
	
	/*
	 * returns a list off all the HUD Objects
	 */
	public List<GameObj> getHUDObjects() {
		return hUD.getObjects();
	}
	
	public void setPlayerMove(int direction) {
		player1.setNextMove(direction);
	}
}
	
