import VMQ.Vec3;


public class Digit extends HUDObjects{
	
	private int number;

	public Digit(Vec3 position,int number) {
		super(position);
		this.number = number;
	}

	public void setNumber(int num) {
		number = num;
	}
	
	@Override
	public String getType() {
		return "Digit"+number;
	}

}
