
public class Clock {
	private long timeCreated;
	private long timeOfLastTick;
	private long timeOfCurrentTick;
	private long numberOfTicks;
	
	public Clock() {
		timeCreated = System.currentTimeMillis();
		timeOfLastTick = timeCreated;
		timeOfCurrentTick = timeCreated;
		numberOfTicks=0;
	}
	
	public void tick() {
		timeOfLastTick = timeOfCurrentTick;
		timeOfCurrentTick = System.currentTimeMillis();
		numberOfTicks++;
	}
	
	public long getNumberOfTicks() {
		return numberOfTicks;
	}
	
	public float getTimeSinceLastTick() {
		return ((float)(timeOfCurrentTick-timeOfLastTick))/1000f;
	}
	
	public long getTimeSinceCreated() {
		return timeOfCurrentTick-timeCreated;
	}
}
