package FileReader;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.badlogic.gdx.backends.android.AndroidGL20;

import android.annotation.SuppressLint;
import android.opengl.GLES20;

public class VBOObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Mesh mesh;
	private int[] VBO = new int[1];
	private final int numberOfVerts;
	private final int stride = 32;
	
	public VBOObject(Mesh mesh) {
		this.mesh = mesh;
		this.VBO[0] = 0;
		this.numberOfVerts = mesh.length()*3;
	}
	
	public void buildVBO() {
		if (VBO[0]==0) {
			
			FloatBuffer buffer = ByteBuffer.allocateDirect(numberOfVerts*32).order(ByteOrder.nativeOrder()).asFloatBuffer();
			
//			FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(numberOfVerts*3*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
//			FloatBuffer normalBuffer = ByteBuffer.allocateDirect(numberOfVerts*3*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
//			FloatBuffer textureBuffer = ByteBuffer.allocateDirect(numberOfVerts*2*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
			for (int i = 0; i < mesh.getMesh().length; i++) {
				for (int j = 0; j <3; j++) {
					// vertex
					buffer.put(mesh.getMesh()[i].getVert(j).getX());
					buffer.put(mesh.getMesh()[i].getVert(j).getY());
					buffer.put(mesh.getMesh()[i].getVert(j).getZ());
					// normal
					buffer.put(mesh.getMesh()[i].getNorm(j).getX());
					buffer.put(mesh.getMesh()[i].getNorm(j).getY());
					buffer.put(mesh.getMesh()[i].getNorm(j).getZ());
					// texture
					buffer.put(mesh.getMesh()[i].getText(j).getX());
					buffer.put(mesh.getMesh()[i].getText(j).getY());
				}
			}
			buffer.position(0);
	        // Generate 1 buffer and load the information for vertices, normals and textures back to back
	        GLES20.glGenBuffers(1, VBO, 0);  																	
	        
	        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, VBO[0]);  													// Bind The Buffer
	     	// Assign the size of the buffer: 
	        // numberOfVerts*4 (4 bytes for a float) *8 - (3 points for a vertex + 3 points for a normal + 2 points for a texture)
	        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, (numberOfVerts*32), buffer, GLES20.GL_STATIC_DRAW);	       
	        // Leave no VBO bound (bind to buffer 0)
	        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		} else {
			System.out.println("VBO already initialised");
		}
	}
	
	public void deleteVBO() {
		if (VBO[0]!=0) {
			GLES20.glDeleteBuffers(1,VBO,0);
			VBO[0] = 0;
		} else {
			System.out.println("VBO not initialised");
		}
	}
	
	@SuppressLint("NewApi")
	public void renderVBO(int vertexLocation,int normalLocation,int textureLocation) {	
		
		if (VBO[0]!=0) {
			// Bind The buffer
	        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, VBO[0]);  					
	        
	        // Enable 3 Array pointers
			GLES20.glEnableVertexAttribArray(vertexLocation);
	        GLES20.glEnableVertexAttribArray(normalLocation);
	        GLES20.glEnableVertexAttribArray(textureLocation);
	        
	        // Set the pointers
	        GLES20.glVertexAttribPointer(vertexLocation, 3, GLES20.GL_FLOAT, false, stride, 0);
	        GLES20.glVertexAttribPointer(normalLocation, 3, GLES20.GL_FLOAT, false, stride, 12);
	        GLES20.glVertexAttribPointer(textureLocation, 2, GLES20.GL_FLOAT, false, stride, 24);
	        
	        // Draw the arrays
	  		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, numberOfVerts);
			
	        // Leave no VBO bound (bind to buffer 0)
	        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
	        
	        GLES20.glDisableVertexAttribArray(vertexLocation);
	        GLES20.glDisableVertexAttribArray(normalLocation);
	        GLES20.glDisableVertexAttribArray(textureLocation);

		} else {
			System.out.println("VBO not initialised");
		}
	}
	
	public boolean equals(Object obj) {
		if (obj==this) return true;
		if (obj==null) return false;
		if (!(obj instanceof VBOObject)) return false;
		
		VBOObject castObj = (VBOObject)obj;
		if (!(mesh.equals(castObj.mesh))) return false;
		return true;
	}	
	
	public int hashCode() {
		return mesh.hashCode();
	}
}
