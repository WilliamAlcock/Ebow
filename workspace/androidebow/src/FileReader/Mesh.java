package FileReader;

import java.io.Serializable;
import java.util.Arrays;

public class Mesh implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Triangle[] mesh;
	
	public Mesh(Triangle[] mesh) {
		this.mesh = mesh;
	}
	
	public boolean equals(final Object obj) {
		if (obj == this) return true;
		if (obj == null) return false;
		if (!(obj instanceof Mesh)) return false;
		
		Mesh castObj = (Mesh)obj;
		return Arrays.equals(mesh,castObj.mesh);
	}
	
	public int hashCode() {
		return Arrays.hashCode(mesh);
	}
	
	public int length() {
		return mesh.length;
	}
	
	public Triangle[] getMesh() {
		return mesh;
	}
}
