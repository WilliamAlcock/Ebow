package FileReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import VMQ.Vec2;
import VMQ.Vec3;

public class ObjFileReader {

	private MtlFileReader mtlFileReader;
	private float xMax;
	private float xMin;
	private float yMax;
	private float yMin;
	private float zMax;
	private float zMin;
	private Mesh[] mesh;
	private Material[] material;
	private String[] textureName;
	
	public ObjFileReader() {
		mtlFileReader = new MtlFileReader();
	}
	
	public void readFile(String filename) throws FileNotFoundException,IOException{
		Path path = Paths.get(filename);
		ArrayList<Vec3> vertices = new ArrayList<Vec3>();									// Collection for vertices coordinates
		ArrayList<Vec3> normals = new ArrayList<Vec3>();									// Collection for normals coordinates 
		ArrayList<Vec2> textures = new ArrayList<Vec2>();									// Collection for textures coordinates
		ArrayList<Triangle> tris = new ArrayList<Triangle>();								// Collection for triangles
		
		// Open the file
		BufferedReader reader = Files.newBufferedReader(path, Charset.forName("US-ASCII"));
		reader.readLine();
		reader.readLine();																// Skip the header
		String curline = reader.readLine().substring(7);								// ReadLine and cut first 6 chars off to leave the material library filename
		mtlFileReader.readFile(path.getParent()+"/"+curline);							// Open the material file
		
		System.out.println(mtlFileReader.size());
		
		xMax = 0;
		xMin = 0;
		yMax = 0;
		yMin = 0;
		zMax = 0;
		zMin = 0;
		mesh = new Mesh[mtlFileReader.size()];								// Initialise meshObjects to the correct size, same as the amount of materials
		material = new Material[mtlFileReader.size()];
		textureName = new String[mtlFileReader.size()];
		
		while (!curline.startsWith("v ")) curline = reader.readLine();					// Skips lines until it gets to vertices
		// Gets vertices from file and adds to verts collection
		while (curline.startsWith("v ")) {											
			curline = curline.substring(2);
			String[] strverts = curline.split(" ");
			if (Float.parseFloat(strverts[0])<xMin) xMin = Float.parseFloat(strverts[0]);	// Update x extremities
			if (Float.parseFloat(strverts[0])>xMax) xMax = Float.parseFloat(strverts[0]);
			if (Float.parseFloat(strverts[1])<yMin) yMin = Float.parseFloat(strverts[1]);	// Update y extremities
			if (Float.parseFloat(strverts[1])>yMax) yMax = Float.parseFloat(strverts[1]);
			if (Float.parseFloat(strverts[2])<zMin) zMin = Float.parseFloat(strverts[2]);	// Update z extremities
			if (Float.parseFloat(strverts[2])>zMax) zMax = Float.parseFloat(strverts[2]);
			vertices.add(new Vec3(Float.parseFloat(strverts[0]),Float.parseFloat(strverts[1]),Float.parseFloat(strverts[2])));	
			curline = reader.readLine();
		}
		// Gets texture vertices from file and adds to texts collection
		while (curline.startsWith("vt ")) {											
			curline = curline.substring(3);
			String[] strverts2 = curline.split(" ");
			textures.add(new Vec2(Float.parseFloat(strverts2[0]),Float.parseFloat(strverts2[1])));	
			curline = reader.readLine();
		}
		// Gets normal vertices from file and adds to norms collection
		while (curline.startsWith("vn ")) {											
			curline = curline.substring(3);
			String[] strverts = curline.split(" ");
			normals.add(new Vec3(Float.parseFloat(strverts[0]),Float.parseFloat(strverts[1]),Float.parseFloat(strverts[2])));	
			curline = reader.readLine();
		}
		
		System.out.println("Read all vertices,normals,textures");
		
		for (int i=0;i<mtlFileReader.size();i++) {
			// Deals with faces object header info
			while (!curline.startsWith("f ")) {											
				String[] strLine = curline.split(" ");
				if (strLine[0].equals("g")) {
					
				} else if (strLine[0].equals("usemtl")) {
					material[i] = mtlFileReader.getMaterial(strLine[1]);
					textureName[i] = mtlFileReader.getTextureName(strLine[1]);
				} else if (strLine[0].equals("s")) {
					
				}
				curline = reader.readLine();
			}
			// Gets faces(triangles) from file and creates triangles from verts,texts and norms arraylists
			while ((curline != null) && (curline.startsWith("f"))) {					
				curline = curline.substring(2);
				String[] strv = curline.split(" ");
				String[] strverts = new String[strv.length];
				String[] strtexts = new String[strv.length];
				String[] strnorms = new String[strv.length];
				for(int j=0;j<strv.length;j++){
					String[] tempverts = strv[j].split("/");
					strverts[j]=tempverts[0].trim();
					strtexts[j]=tempverts[1].trim();
					strnorms[j]=tempverts[2].trim();
				}				
				Triangle addtri = new Triangle(vertices.get(Integer.parseInt(strverts[0])-1),
						vertices.get(Integer.parseInt(strverts[1])-1),
						vertices.get(Integer.parseInt(strverts[2])-1),
						normals.get(Integer.parseInt(strnorms[0])-1),
						normals.get(Integer.parseInt(strnorms[1])-1),
						normals.get(Integer.parseInt(strnorms[2])-1),
						textures.get(Integer.parseInt(strtexts[0])-1),
						textures.get(Integer.parseInt(strtexts[1])-1),
						textures.get(Integer.parseInt(strtexts[2])-1));		
				tris.add(addtri); 
				curline = reader.readLine();		
			}
			// Create a meshObject using material and triangle array
			mesh[i] = new Mesh(tris.toArray(new Triangle[tris.size()])); 
					
			// Clear the tris arraylist
			tris.clear();																	
		}
	}
	
	public Mesh[] getMeshs() {
		return mesh;
	}
	
	public Material[] getMaterials() {
		return material;
	}
	
	public String[] getTextureNames() {
		return textureName;
	}
	
	public int size() {
		return mesh.length;
	}
	
	public Vec3 getDimensions() {
		Vec3 dimensions = new Vec3();
		if (xMax>(xMin*-1)) {
			dimensions.setX(xMax);
		} else {
			dimensions.setX(xMin*-1);
		}
	
		if (yMax>(yMin*-1)) {
			dimensions.setY(yMax);
		} else {
			dimensions.setY(yMin*-1);
		}
		
		if (zMax>(zMin*-1)) {
			dimensions.setZ(zMax);
		} else {
			dimensions.setZ(zMin*-1);
		}
		return dimensions;
	}
}

