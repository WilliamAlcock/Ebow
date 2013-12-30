package GameEngine;

public class MySwitch {
	private boolean mySwitch;
	
	public MySwitch(boolean mySwitch) {
		this.mySwitch = mySwitch;
	}
	
	public boolean isOn() {
		return mySwitch;
	}
	
	public void set(boolean mySwitch) {
		this.mySwitch = mySwitch;
	}
}
