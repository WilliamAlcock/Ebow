package GameEngine;
import java.util.List;


public interface Fires {
	
	public void fireWeapon(float timeSinceLastTick,List<InPlayObj> fireList);
	
	public abstract void setRateOfFire(float rateOfFire);
}