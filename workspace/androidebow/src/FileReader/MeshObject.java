package FileReader;

import java.io.Serializable;

public class MeshObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int vboIndex;
	private int materialIndex;
	private String textureName;
	
	public MeshObject(int materialIndex,int vboIndex,String textureName) {
		this.materialIndex = materialIndex;
 		this.vboIndex = vboIndex;
 		this.textureName = textureName;
	}
	
	public int getMaterialIndex() {
		return materialIndex;
	}
	
	public void setMaterialIndex(int materialIndex) {
		this.materialIndex = materialIndex;
	}
	
	public int getVBOIndex() {
		return vboIndex;
	}
	
	public void setVBOIndex(int vboIndex) {
		this.vboIndex = vboIndex;
	}
	
	public String getTextureName() {
		return textureName;
	}
}
