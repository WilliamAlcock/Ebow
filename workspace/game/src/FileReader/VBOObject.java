package FileReader;

import java.io.Serializable;
import java.nio.FloatBuffer;

import javax.media.opengl.GL3;

import com.jogamp.common.nio.Buffers;

public class VBOObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Mesh mesh;
	private int[] VBO = new int[1];
	private int numberOfVerts;
	
	public VBOObject(Mesh mesh) {
		this.mesh = mesh;
		this.VBO[0] = 0;
		this.numberOfVerts = mesh.length()*3;
	}
	
	public void buildVBO(GL3 gl) {
		if (VBO[0]==0) {
			FloatBuffer vertexBuffer = Buffers.newDirectFloatBuffer(numberOfVerts*3);
			FloatBuffer normalBuffer = Buffers.newDirectFloatBuffer(numberOfVerts*3);
			FloatBuffer textureBuffer = Buffers.newDirectFloatBuffer(numberOfVerts*2);
			for (int i = 0; i < mesh.getMesh().length; i++) {
				for (int j = 0; j <3; j++) {
					// X
					vertexBuffer.put(mesh.getMesh()[i].getVert(j).getX());
					normalBuffer.put(mesh.getMesh()[i].getNorm(j).getX());
					textureBuffer.put(mesh.getMesh()[i].getText(j).getX());
					// Y
					vertexBuffer.put(mesh.getMesh()[i].getVert(j).getY());
					normalBuffer.put(mesh.getMesh()[i].getNorm(j).getY());
					textureBuffer.put(mesh.getMesh()[i].getText(j).getY());
					// Z
					vertexBuffer.put(mesh.getMesh()[i].getVert(j).getZ());
					normalBuffer.put(mesh.getMesh()[i].getNorm(j).getZ());
				}
			}
			vertexBuffer.rewind();
			normalBuffer.rewind();
			textureBuffer.rewind();
	        // Generate 1 buffer and load the information for vertices, normals and textures back to back
	        gl.glGenBuffers(1, VBO, 0);  																	
	        
	        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, VBO[0]);  													// Bind The Buffer
	     	// Assign the size of the buffer: 
	        // numberOfVerts*4 (4 bytes for a float) *8 - (3 points for a vertex + 3 points for a normal + 2 points for a texture)
	        gl.glBufferData(GL3.GL_ARRAY_BUFFER, (numberOfVerts*4)*9, null, GL3.GL_STATIC_DRAW);
	        
	        // Load the vertex info first
	        gl.glBufferSubData(GL3.GL_ARRAY_BUFFER, 0, (numberOfVerts*4)*3, vertexBuffer);					
	        // Load the normal info second 
	        gl.glBufferSubData(GL3.GL_ARRAY_BUFFER,(numberOfVerts*4)*3,(numberOfVerts*4)*3,normalBuffer);
	        // Load the texture info third
	        gl.glBufferSubData(GL3.GL_ARRAY_BUFFER,(numberOfVerts*4)*6,(numberOfVerts*4)*2,textureBuffer);
	        
	        // Leave no VBO bound (bind to buffer 0)
	        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
		} else {
			System.out.println("VBO already initialised");
		}
	}
	
	public void deleteVBO(GL3 gl) {
		if (VBO[0]!=0) {
			gl.glDeleteBuffers(1,VBO,0);
			VBO[0] = 0;
		} else {
			System.out.println("VBO not initialised");
		}
	}
	
	public void renderVBO(GL3 gl) {	
        
		if (VBO[0]!=0) {
	        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, VBO[0]);  					// Bind The buffer
	        
	        // Enable 3 Array pointers
	        // 1 - Vertex coords
	        // 0 - Normal coords
	        // 2 - Texture coords
			gl.glEnableVertexAttribArray(0);
	        gl.glEnableVertexAttribArray(1);
	        gl.glEnableVertexAttribArray(2);
	        
	        // Set the pointers
	        gl.glVertexAttribPointer(1, 3, GL3.GL_FLOAT, false, 0, 0);
	        gl.glVertexAttribPointer(0, 3, GL3.GL_FLOAT, false, 0, (numberOfVerts*4)*3);
	        gl.glVertexAttribPointer(2, 2, GL3.GL_FLOAT, false, 0, (numberOfVerts*4)*6);
	        
	        // Draw the arrays
	  		gl.glDrawArrays(GL3.GL_TRIANGLES, 0, numberOfVerts);
			
	        // Leave no VBO bound (bind to buffer 0)
	        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
	        
		    gl.glDisableClientState(0);
			gl.glDisableClientState(1);
			gl.glDisableClientState(2);
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
