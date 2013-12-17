package FileReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import VMQ.Vec3;

public class MtlFileReader {

	private HashMap<String,Material> materials;
	private HashMap<String,String> textureNames;
	private MaterialBuilder materialBuilder;
	
	public MtlFileReader() {
		materialBuilder = new MaterialBuilder();
	}
	
	public void readFile(String filename) throws FileNotFoundException,IOException {
		materialBuilder.reset();
		materials = new HashMap<String,Material>();
		textureNames = new HashMap<String,String>();
		Path path = Paths.get(filename);
		// Open the file
		BufferedReader reader = Files.newBufferedReader(path, Charset.forName("US-ASCII"));
		String materialName;
		reader.readLine();																					// Skip first line
		String curline = reader.readLine();																	
		String[] strLine1 = curline.split(" "); 
		int numberOfMats = Integer.parseInt(strLine1[strLine1.length-1]);									// Get the number of materials in the file
		reader.readLine();																					// Skip line
		for (int i = 0;i<numberOfMats;i++) {
			materialName = reader.readLine().substring(7);														// ReadLine and cut first 6 chars off
			curline = reader.readLine();
			while ((curline != null) && (curline.length()>0)) {
				String[] strLine = curline.split(" ");														// Break the string up by spaces
				if (strLine[0].equals("Ns")) {
					materialBuilder.setSpecularWeight(Float.parseFloat(strLine[1]));
				} else if (strLine[0].equals("Ka")) {
					materialBuilder.setAmbient(new Vec3(Float.parseFloat(strLine[1]),Float.parseFloat(strLine[2]),Float.parseFloat(strLine[3])));
				} else if (strLine[0].equals("Kd")) {
					materialBuilder.setDiffuse(new Vec3(Float.parseFloat(strLine[1]),Float.parseFloat(strLine[2]),Float.parseFloat(strLine[3])));
				} else if (strLine[0].equals("Ks")) {
					materialBuilder.setSpecular(new Vec3(Float.parseFloat(strLine[1]),Float.parseFloat(strLine[2]),Float.parseFloat(strLine[3])));
				} else if (strLine[0].equals("Em")) {
					materialBuilder.setEmission(new Vec3(Float.parseFloat(strLine[1]),Float.parseFloat(strLine[2]),Float.parseFloat(strLine[3])));
				} else if (strLine[0].equals("Ni")) {
					materialBuilder.setOpticalDensity(Float.parseFloat(strLine[1]));
				} else if (strLine[0].equals("d")) {
					materialBuilder.setDissolve(Float.parseFloat(strLine[1]));
				} else if (strLine[0].equals("illum")) {
					materialBuilder.setIllumination(Integer.parseInt(strLine[1]));
				} else if (strLine[0].equals("map_Kd")) {
					textureNames.put(materialName,strLine[1].trim());
				} 
				curline = reader.readLine();
			}
			materials.put(materialName, materialBuilder.getMaterial());
		}

	}
	
	public Material getMaterial(String name) {
		return materials.get(name);
	}
	
	public String getTextureName(String name) {
		return textureNames.get(name);
	}
	
	public int size() {
		return materials.size();
	}
	
}
