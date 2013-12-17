package DataNormaliser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import VMQ.Vec3;

import FileReader.MeshObject;
import FileReader.Material;
import FileReader.VBOObject;

public class DataModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public HashMap<String,Integer> textureNames = new HashMap<String,Integer>();
	public HashMap<String,MeshObject[]> meshObjs = new HashMap<String,MeshObject[]>();
	public HashMap<String,Vec3> dimensions = new HashMap<String,Vec3>();
	
	public ArrayList<Material> materials = new ArrayList<Material>();
	public ArrayList<LinkedList<Reference>> materialRefs = new ArrayList<LinkedList<Reference>>();
	
	public ArrayList<VBOObject> vboObjects = new ArrayList<VBOObject>();
	public ArrayList<LinkedList<Reference>> vboObjectRefs = new ArrayList<LinkedList<Reference>>();
}

