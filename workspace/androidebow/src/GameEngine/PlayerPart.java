package GameEngine;
import VMQ.Vec3;

public abstract class PlayerPart extends InPlayObj{
	
	private Player player;
	
	public PlayerPart(Vec3 position, float speed, float rotSpeed,int health,int damage) {
		super(position,speed,rotSpeed,0,health,damage);
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public DISPLAYTYPE getDisplayType() {
		return DISPLAYTYPE.SOLID;
	}
	
	public String getCategory() {
		return "PlayerPart";
	}
	
	public boolean isFinished() {
		return false;
	}
	
	
	
	public void handleCollision(InPlayObj objCollidingWith) {}
}
