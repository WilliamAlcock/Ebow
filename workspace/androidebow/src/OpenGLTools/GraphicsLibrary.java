package OpenGLTools;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.res.AssetManager;

import DataNormaliser.DataModel;
import FileReader.Material;
import FileReader.VBOObject;

import FileReader.MeshObject;
import VMQ.Vec3;

public class GraphicsLibrary {
	private TextureLibrary textureLibrary;
	private HashMap<String,Vec3> dimensions;
	private ArrayList<Material> materials;
	private ArrayList<VBOObject> vboObjects;
	private HashMap<String,MeshObject[]> meshObjs; 
	
	public GraphicsLibrary(Context context,String filename) {
		DataModel dataModel = load(context,filename);
		// load textures
		textureLibrary = new TextureLibrary(context,dataModel.textureNames.keySet());
		dimensions = dataModel.dimensions;
		materials = dataModel.materials;
		// build VBOs 
		vboObjects = dataModel.vboObjects;
		for (VBOObject curVBOObject: vboObjects) {
			curVBOObject.buildVBO();
		}
		meshObjs = dataModel.meshObjs;
	}
	
	private DataModel load(Context context,String filename) {
		AssetManager assetManager = context.getAssets();
		DataModel newDataModel = null;
		String newLogs = null;
		try {
			InputStream input = assetManager.open(filename);
			ObjectInputStream oinput = new ObjectInputStream(input);
	        newDataModel = (DataModel) oinput.readObject();
	        newLogs = (String) oinput.readObject();
	        oinput.close();
	        input.close();
	    } catch(IOException i) {
	    	System.out.println("IO Exception "+i);
	    } catch(ClassNotFoundException c) {
	    	System.out.println("Class not found "+c);
	    }
		
		if ((newDataModel!=null) && (newLogs!=null)) return newDataModel;
		System.err.println("ERROR LOADING GRAPHICS FILES");
		return null;
	}
	
/*	public int getTexture(String textureName) {
		return textureLibrary.getTexture(textureName);
	}			*/
	
	public void bindTexture(String textureName) {
		textureLibrary.bindTexture(textureName);
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