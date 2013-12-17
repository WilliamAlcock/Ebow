import VMQ.Vec3;

public class WallpaperPane extends BackgroundObjects{

	public WallpaperPane(Vec3 position) {
		super(position);
	}
	
	public String getType() {
		return "Wallpaper";
	}
	
	public DISPLAYTYPE getDisplayType() {
		return DISPLAYTYPE.SOLID;
	}
	
}
