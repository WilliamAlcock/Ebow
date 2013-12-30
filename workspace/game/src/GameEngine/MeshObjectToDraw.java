package GameEngine;
import FileReader.MeshObject;
import VMQ.Mat4x4;
import VMQ.Vec4;

public class MeshObjectToDraw implements Comparable<MeshObjectToDraw>{

	private Mat4x4 modelMatrix;
	private MeshObject meshObject;
	
	private Vec4 objectColor;
	private boolean useColorObject;
	private GameObj.DISPLAYTYPE displayType;
	
	public MeshObjectToDraw(MeshObject meshObject,Mat4x4 modelMatrix,Vec4 objectColor,GameObj.DISPLAYTYPE displayType,boolean useColorObject) {
		this.meshObject = meshObject;
		this.modelMatrix = modelMatrix;
		this.useColorObject = useColorObject;
		this.objectColor = objectColor;
		this.displayType = displayType;
	}
	
	public boolean useColorObject() {
		return useColorObject;
	}
	
	public Mat4x4 getModelMatrix() {
		return modelMatrix;
	}
	
	public MeshObject getMeshObject() {
		return meshObject;
	}
	
	public Vec4 getObjectColor() {
		return objectColor;
	}
	
	public GameObj.DISPLAYTYPE getDisplayType() {
		return displayType;
	}

	@Override
	public int compareTo(MeshObjectToDraw m) {
		int answer = displayType.compareTo(m.displayType);
		if (answer==0) {  
			return meshObject.getTextureName().compareTo(m.meshObject.getTextureName());
		} else {
			return answer;
		}
    }
}
