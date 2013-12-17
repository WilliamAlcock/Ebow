package OpenGLTools;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import DataNormaliser.DataModel;
import FileReader.Material;
import FileReader.VBOObject;

import javax.media.opengl.GL3;

import com.jogamp.opengl.util.texture.Texture;

import FileReader.MeshObject;
import VMQ.Vec3;

public class GraphicsLibrary {
	private TextureLibrary textureLibrary;
	private HashMap<String,Vec3> dimensions;
	private ArrayList<Material> materials;
	private ArrayList<VBOObject> vboObjects;
	private HashMap<String,MeshObject[]> meshObjs; 
	
	public GraphicsLibrary(GL3 gl,String filename) {
		DataModel dataModel = load(new File(filename));
		// load textures
		textureLibrary = new TextureLibrary(gl,dataModel.textureNames.keySet());
		dimensions = dataModel.dimensions;
		materials = dataModel.materials;
		// build VBOs 
		vboObjects = dataModel.vboObjects;
		for (VBOObject curVBOObject: vboObjects) {
			curVBOObject.buildVBO(gl);
		}
		meshObjs = dataModel.meshObjs;
	}
	
	private DataModel load(File file) {
		DataModel newDataModel = null;
		String newLogs = null;
		try {																						// Loads the file
	        FileInputStream fileIn = new FileInputStream(file);
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        newDataModel = (DataModel) in.readObject();
	        newLogs = (String) in.readObject();
	        in.close();
	        fileIn.close();
	    } catch(IOException i) {
	    	System.out.println("IO Exception "+i);
	    } catch(ClassNotFoundException c) {
	    	System.out.println("Class not found "+c);
	    }
		
		if ((newDataModel!=null) && (newLogs!=null)) return newDataModel;
		System.err.println("ERROR LOADING GRAPHICS FILES");
		return null;
	}
	
	public Texture getTexture(String textureName) {
		return textureLibrary.getTexture(textureName);
	}
	
	public HashMap<String,Vec3> getDimensions() {
		return dimensions;
	}
	
	public Material getMaterialObject(int index) {
		return materials.get(index);
	}
	
	public int getMaterialSize() {
		return materials.size();
	}
	
	public VBOObject getVBOObject(int index) {
		return vboObjects.get(index);
	}
	
	public MeshObject[] getMeshObj(String type) {
		return meshObjs.get(type);
	}
}