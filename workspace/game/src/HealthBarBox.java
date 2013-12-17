import VMQ.Vec3;


public class HealthBarBox extends HUDObjects{

	public HealthBarBox(Vec3 position) {
		super(position);
	}

	@Override
	public String getType() {
		return "HealthBarBox";
	}
}
