package OpenGLTools;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.media.opengl.GL3;
import javax.media.opengl.GLException;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class TextureLibrary {

	HashMap<String,Texture> textures = new HashMap<String,Texture>();
	
	public TextureLibrary(GL3 gl,Set<String> textureNames) {
		for (String curString: textureNames) {
			addTexture(gl,curString);
		}
	}
	
	public boolean textureExists(String filename) {
		return textures.containsKey(filename);
	}
	
	public Texture getTexture(String filename) {
		return textures.get(filename);
	}
	
	private Texture addTexture(GL3 gl,String filename) {
		Texture texture;
		try {
			texture = loadTexture(gl,"c:/Users/Jack/My Documents/Game/Images/"+filename);
		} catch (IOException ex) {
			System.err.println("Error loading texture: "+ex+" "+filename);
			return null;
		}
		textures.put(filename, texture);
		return texture;
	}	

	private Texture loadTexture(GL3 gl,String file) throws GLException, IOException {
	    ByteArrayOutputStream os = new ByteArrayOutputStream();
	    ImageIO.write(ImageIO.read(new File(file)), "png", os);
	    InputStream fis = new ByteArrayInputStream(os.toByteArray());
	    Texture newTexture = TextureIO.newTexture(fis, true, TextureIO.PNG);
	    newTexture.setTexParameteri(gl,GL3.GL_TEXTURE_WRAP_S, GL3.GL_REPEAT);
		newTexture.setTexParameteri(gl,GL3.GL_TEXTURE_WRAP_T, GL3.GL_REPEAT);
		newTexture.setTexParameteri(gl,GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
		newTexture.setTexParameteri(gl,GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR_MIPMAP_LINEAR);
		System.out.println("Must flip: "+newTexture.getMustFlipVertically());
	    return newTexture;
	}		

}