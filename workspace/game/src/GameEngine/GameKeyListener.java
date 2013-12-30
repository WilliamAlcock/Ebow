package GameEngine;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener{
	
	private boolean[] keyBuffer;					// keyboard buffer
	private GameEngine gameEngine;
	private Ebow ebow;
	
	public GameKeyListener(Ebow ebow) {
		this.ebow = ebow;
		keyBuffer = new boolean[256];				
	}
	
	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}
	
	@Override
    public void keyTyped(KeyEvent e) {}
 
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		keyBuffer[keyCode]=true;
	}
 
	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		keyBuffer[keyCode]=false;
		if (keyCode==KeyEvent.VK_ESCAPE)  {
			ebow.quit();
		}
	}

	public void checkKeyBuffer() {
		if (gameEngine!=null) { 
			int xDir = 0;
			if (keyBuffer[KeyEvent.VK_UP]) {
				gameEngine.setPlayerMove(0);
			}
			if (keyBuffer[KeyEvent.VK_DOWN]) {
				gameEngine.setPlayerMove(1);
			}
			if (keyBuffer[KeyEvent.VK_RIGHT]) {
				xDir += 1;
			}
			if (keyBuffer[KeyEvent.VK_LEFT]) {
				xDir -= 1;
			}
			if (keyBuffer[KeyEvent.VK_CONTROL]) {
				gameEngine.setPlayerMove(5);
			}
			if (xDir>0) {
				gameEngine.setPlayerMove(2);
			} else if (xDir<0) {
				gameEngine.setPlayerMove(3);
			} else if (xDir==0) {
				gameEngine.setPlayerMove(4);
			}	
		} else {
			System.err.println("Game engine not set");
		}
	}
}
