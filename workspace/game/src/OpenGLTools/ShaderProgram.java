package OpenGLTools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.media.opengl.GL3;

public class ShaderProgram {
	
	private HashMap<String,Integer> locations = new HashMap<String,Integer>();
	private int shaderProgram;
	private GL3 gl;
	
	public ShaderProgram(GL3 gl,String vertexShaderFile, String fragmentShaderFile) {
		this.gl = gl;
		shaderProgram = gl.glCreateProgram();
		int vertexShader = gl.glCreateShader(GL3.GL_VERTEX_SHADER);
		int fragmentShader = gl.glCreateShader(GL3.GL_FRAGMENT_SHADER);
		gl.glShaderSource(vertexShader, 1, readFile("shaders/"+vertexShaderFile), (int[])null,0);
		gl.glShaderSource(fragmentShader, 1, readFile("shaders/"+fragmentShaderFile), (int[])null,0);
		gl.glCompileShader(vertexShader);
		gl.glCompileShader(fragmentShader);
		
		getShaderErrors(vertexShader);
		getShaderErrors(fragmentShader);
		
		gl.glAttachShader(shaderProgram, vertexShader);
		gl.glAttachShader(shaderProgram, fragmentShader);
		gl.glLinkProgram(shaderProgram);
		gl.glValidateProgram(shaderProgram);
		
		getProgramErrors();
	    
	    gl.glDeleteShader(vertexShader);
	    gl.glDeleteShader(fragmentShader);
	}
	
	private void getShaderErrors(int shader) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
	    gl.glGetShaderiv(shader, GL3.GL_LINK_STATUS, intBuffer);
	    
	    if (intBuffer.get(0) != 1) {
	        gl.glGetShaderiv(shader, GL3.GL_INFO_LOG_LENGTH, intBuffer);
	        int size = intBuffer.get(0);
	        System.err.println("Shader error: ");
	        if (size > 0) {
	            ByteBuffer byteBuffer = ByteBuffer.allocate(size);
	            gl.glGetShaderInfoLog(shader, size, intBuffer, byteBuffer);
	            for (byte b : byteBuffer.array()) {
	                System.err.print((char) b);
	            }
	        } else {
	            System.out.println("Unknown");
	        }
	    }
	}
	
	private void getProgramErrors() {
		IntBuffer intBuffer = IntBuffer.allocate(1);
	    gl.glGetProgramiv(shaderProgram, GL3.GL_LINK_STATUS, intBuffer);
	    
	    if (intBuffer.get(0) != 1) {
	        gl.glGetProgramiv(shaderProgram, GL3.GL_INFO_LOG_LENGTH, intBuffer);
	        int size = intBuffer.get(0);
	        System.err.println("Program link error: ");
	        if (size > 0) {
	            ByteBuffer byteBuffer = ByteBuffer.allocate(size);
	            gl.glGetProgramInfoLog(shaderProgram, size, intBuffer, byteBuffer);
	            for (byte b : byteBuffer.array()) {
	                System.err.print((char) b);
	            }
	        } else {
	            System.out.println("Unknown");
	        }
	    }
	}
	
	public int getLocation(String name) {
		return locations.get(name);
	}
	
	public int putLocation(String name) {
		int location = gl.glGetUniformLocation(shaderProgram, name);
		locations.put(name, location);
		return location;
	}
	
	public int use() {
		gl.glUseProgram(shaderProgram);
		return shaderProgram;
	}
	
	public int getID() {
		return shaderProgram;
	}
	
	public void dontUseShader() {
   		gl.glUseProgram(0);
	}
	
	private String[] readFile(String filename) {
		Path path = Paths.get(filename);
		String fileContent = new String();
		try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("US-ASCII"))) {
			String curLine;
			while ((curLine = reader.readLine()) !=null ) {
				fileContent += curLine + "\n";
			}							
		} catch(FileNotFoundException e) {
			System.err.println("Unable to open "+filename);
		} catch(IOException e) {
			System.err.println("A problem was encountered reading "+filename);
		}
		System.out.println(fileContent);
		return new String[] {fileContent};
	}

}
