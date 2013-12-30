package FileReader;
import java.io.Serializable;

import VMQ.Vec2;
import VMQ.Vec3;

public class Triangle implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vec3[] vertices; 
	private Vec3[] normals;
	private Vec2[] textures;
	private int hashCode;
	
	public Triangle (Vec3 vert0, Vec3 vert1, Vec3 vert2,
			Vec3 normal0,Vec3 normal1,Vec3 normal2,
			Vec2 texture0,Vec2 texture1,Vec2 texture2) {
		normals = new Vec3[3];
		vertices = new Vec3[3];
		textures = new Vec2[3];
		normals[0] = normal0;
		normals[1] = normal1;
		normals[2] = normal2;
		vertices[0] = vert0;
		vertices[1] = vert1;
		vertices[2] = vert2;
		textures[0] = texture0;
		textures[1] = texture1;
		textures[2] = texture2;
		
		hashCode = 17;
		for (int i=0;i<3;i++) {
			hashCode = 31 * hashCode + vertices[i].hashCode();
			hashCode = 31 * hashCode + normals[i].hashCode();
			hashCode = 31 * hashCode + textures[i].hashCode();
		}
	}
	
	public Vec3 getVert(int dim) {
		return vertices[dim];
	}
	
	public Vec3 getNorm(int dim) {
		return normals[dim];
	}
	
	public Vec2 getText(int dim) {
		return textures[dim];
	}
	
	public String toString() {
		return "Vertices: "+vertices[0].toString()+" "+vertices[1].toString()+" "+vertices[2].toString()+"\n"+
				"Normals: "+normals[0].toString()+" "+normals[1].toString()+" "+normals[2].toString()+"\n"+
				"Textures: "+textures[0].toString()+" "+textures[1].toString()+"\n";
	}
	
	public boolean equals(final Object obj) {
		if (obj == this) return true;
		if (obj == null) return false;
		if (!(obj instanceof Triangle)) return false;
		
		Triangle castObj = (Triangle)obj;
		
		for (int i=0;i<3;i++) {
			if (!(vertices[i].equals(castObj.vertices[i]))) return false;
			if (!(normals[i].equals(castObj.normals[i]))) return false;
			if (!(textures[i].equals(castObj.textures[i]))) return false;
		}
		
		return true;
	}
	
	public int hashCode() {
		return hashCode;
	}
}


