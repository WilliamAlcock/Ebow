import VMQ.Vec3;


public class HealthBar extends HUDObjects{

	private float healthPercent;
	private float width;
	
	public HealthBar(Vec3 position,float width) {
		super(position);
		this.width = width;
		setColor(new Vec3(0.0f,1.0f,0.2f));
		setLife(0.7f);
		updateHealth(100);
	}
	
	public void updateHealth(int health) {
		float decAmount = (healthPercent - health)/100;
		this.getPosition().setX(this.getPosition().getX()-(width*decAmount));
		this.healthPercent = health;
		this.getScale().setX(healthPercent/100);
		this.getColor().setX((100-healthPercent)/100);
		this.getColor().setY(healthPercent/100);
	}
	
	public void updatePosition() {
		this.getPosition().setX(this.getPosition().getX()-(width*((100-healthPercent)/100)));
	}

	@Override
	public String getType() {
		return "HealthBar";
	}
}
