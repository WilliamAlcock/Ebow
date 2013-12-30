package OpenGLTools;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.HashMap;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;

public class ShaderProgram {
	
	private HashMap<String,Integer> locations = new HashMap<String,Integer>();
	private HashMap<String,Integer> attributes = new HashMap<String,Integer>();
	private int shaderProgram;
	
	
	public ShaderProgram(Context context,String vertexShaderFile, String fragmentShaderFile,String[] attr) {
		shaderProgram = GLES20.glCreateProgram();
		int vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
		int fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
		System.out.println("vertex shader number: "+vertexShader);
		System.out.println("fragment shader number: "+fragmentShader);
		GLES20.glShaderSource(vertexShader, readFile(context,vertexShaderFile));		
		GLES20.glShaderSource(fragmentShader, readFile(context,fragmentShaderFile));
		GLES20.glCompileShader(vertexShader);
		System.out.println("Finished vertex shader");
		GLES20.glCompileShader(fragmentShader);
		System.out.println("Finished fragment shader");
		
		getShaderErrors(vertexShader);
		getShaderErrors(fragmentShader);
		
		GLES20.glAttachShader(shaderProgram, vertexShader);
		GLES20.glAttachShader(shaderProgram, fragmentShader);
		
		// Bind attributes
		if (attr != null) {
			for (int i = 0; i < attr.length; i++) {
				GLES20.glBindAttribLocation(shaderProgram, i, attr[i]);
				attributes.put(attr[i],i);
			}						
		}
		
		GLES20.glLinkProgram(shaderProgram);
		GLES20.glValidateProgram(shaderProgram);
		
		getProgramErrors();
	    
	    GLES20.glDeleteShader(vertexShader);
	    GLES20.glDeleteShader(fragmentShader);
	}
	
	private void getShaderErrors(int shader) {
		IntBuffer intBuffer = IntBuffer.allocate(1);
	    GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, intBuffer);
	    
	    if (intBuffer.get(0) != 1) {
	        GLES20.glGetShaderiv(shader, GLES20.GL_INFO_LOG_LENGTH, intBuffer);
	        System.err.println("Shader error: "+shader);
	        if (intBuffer.get(0) > 0) {
	            System.out.println(GLES20.glGetShaderInfoLog(shader));
	        } else {
	            System.out.println("Unknown");
	        }
	    }
	}
	
	private void getProgramErrors() {
		IntBuffer intBuffer = IntBuffer.allocate(1);
	    GLES20.glGetProgramiv(shaderProgram, GLES20.GL_LINK_STATUS, intBuffer);
	    if (intBuffer.get(0) != 1) {
	        GLES20.glGetProgramiv(shaderProgram, GLES20.GL_INFO_LOG_LENGTH, intBuffer);
	        System.err.println("Program link error: ");
	        if (intBuffer.get(0) > 0) {
	        	System.out.println(GLES20.glGetProgramInfoLog(shaderProgram));
	        } else {
	            System.out.println("Unknown");
	        }
	    }
	}
	
	public int getLocation(String name) {
		return locations.get(name);
	}
	
	public int putLocation(String name) {
		int location = GLES20.glGetUniformLocation(shaderProgram, name);
		locations.put(name, location);
		return location;
	}
	
	public int getAttribute(String name) {
		return attributes.get(name);
	}
	
	public int use() {
		GLES20.glUseProgram(shaderProgram);
		return shaderProgram;
	}
	
	public int getID() {
		return shaderProgram;
	}
	
	public void dontUseShader() {
   		GLES20.glUseProgram(0);
	}
	
	private String readFile(Context context,String filename) {
		// *********** Issue here ****
        
		String retString = new String();
		AssetManager assetManager = context.getAssets();
		try {
			InputStream input = assetManager.open(filename);
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    int i = input.read();
		    while (i != -1) {
		          baos.write(i);
		          i = input.read();
		    }
		    System.out.println(baos.toString());
		    return baos.toString();
		} catch(FileNotFoundException e) {
			System.err.println("Unable to open "+filename);
		} catch(IOException e) {
			System.err.println("A problem was encountered reading "+filename);
		}
		System.out.println(retString);
		return retString;
	}
}
