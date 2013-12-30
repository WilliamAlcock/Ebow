package GameEngine;
import VMQ.Vec3;

public class Text extends GameObj{
	
	private String text;
	private String font;
	private int size;
	private float lifeSpan;
	private float age;
	
	public Text(Vec3 position,String text,String font,int size,float lifeSpan) {
		super(position);
		this.text = text;
		this.font = font;
		this.size = size;
		this.lifeSpan = lifeSpan;
	}
	
	public void tick(float timeSinceLastTick) {
		this.age += timeSinceLastTick;
	}
	
	public boolean isAlive() {
		return lifeSpan>age;
	}

	public String getText() {
		return text;
	}

	public String getFont() {
		return font;
	}

	public int getSize() {
		return size;
	}
	
	public float getLifeSpan() {
		return lifeSpan;
	}

	public String getType() {
		return "Text";
	}

	public DISPLAYTYPE getDisplayType() {
		return DISPLAYTYPE.TEXT;
	}
}
