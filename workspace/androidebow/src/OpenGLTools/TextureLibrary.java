package OpenGLTools;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Set;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;

public class TextureLibrary {

	private HashMap<String,Integer> textures = new HashMap<String,Integer>();
	private Context context;
	
	public TextureLibrary(Context context,Set<String> textureNames) {
		this.context = context;
		for (String curString: textureNames) {
			addTexture(curString);
		}
	}
	
	public boolean textureExists(String filename) {
		filename = removeExtension(filename);
		return textures.containsKey(filename);
	}
	
	public int getTexture(String filename) {
		filename = removeExtension(filename);
		return textures.get(filename);
	}
	
	public void bindTexture(String filename) {
		filename = removeExtension(filename);
		if (filename.equals("")) {
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
		} else {
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures.get(filename));
		}
	}
	
	private String removeExtension(String filename) {
		return filename.substring(0, filename.lastIndexOf(".")).toLowerCase();
	}
	
	private int addTexture(String filename) {
		filename = removeExtension(filename);
		int texture;
		try {
			texture = loadTexture(context,filename);
		} catch (Exception ex) {
			System.err.println("Error loading texture: "+ex+" "+filename);
			return (Integer) null;
		}
		textures.put(filename, texture);
		return texture;
	}	
	
	public static int loadTexture(final Context context, String resourceName) {
		int resourceId = context.getResources().getIdentifier("drawable/"+resourceName , "drawable", context.getPackageName());
		int[] textureNameWorkspace = new int[1]; 
        GLES20.glGenTextures(1, textureNameWorkspace, 0); 
        int textureName = textureNameWorkspace[0]; 
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureName); 
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR); 
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR); 
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT); 
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT); 

        
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),resourceId); 
        myTexImage2D(bitmap); 
        return textureName; 
    } 

    public static void myTexImage2D(Bitmap bitmap) { 
        // Don't loading using GLUtils, load using gl-method directly 
        // GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0); 
        int[] pixels = extractPixels(bitmap); 
        byte[] pixelComponents = new byte[pixels.length*4]; 
        int byteIndex = 0; 
        for (int i = 0; i < pixels.length; i++) { 
                int p = pixels[i]; 
                    // Convert to byte representation RGBA required by gl.glTexImage2D. 
                    // We don't use intbuffer, because then we 
                    // would be relying on the intbuffer wrapping to write the ints in 
                    // big-endian format, which means it would work for the wrong 
                    // reasons, and it might brake on some hardware. 
                pixelComponents[byteIndex++] = (byte) ((p >> 16) & 0xFF); // red 
                pixelComponents[byteIndex++] = (byte) ((p >> 8) & 0xFF); // green 
                pixelComponents[byteIndex++] = (byte) ((p) & 0xFF); // blue 
                pixelComponents[byteIndex++] = (byte) (p >> 24);  // alpha 
        } 
        ByteBuffer pixelBuffer = ByteBuffer.wrap(pixelComponents); 

        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, bitmap.getWidth(), bitmap.getHeight(), 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, pixelBuffer); 
    } 

    public static int[] extractPixels(Bitmap src) { 
            int x = 0; 
            int y = 0; 
            int w = src.getWidth(); 
            int h = src.getHeight(); 
            int[] colors = new int[w * h]; 
            src.getPixels(colors, 0, w, x, y, w, h); 
            return colors; 
    } 


}