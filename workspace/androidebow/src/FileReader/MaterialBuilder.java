package FileReader;

import VMQ.Vec3;

public class MaterialBuilder {
	
	private Vec3 ambient = new Vec3();			// Ka
	private Vec3 diffuse = new Vec3();			// Kd
	private Vec3 specular = new Vec3();			// Ks
	private Vec3 emission = new Vec3();			// I have added this to give my materials an emission
	private float specularWeight;				// Ns
	private float opticalDensity;				// Ni
	private float dissolve;						// d
	private int illumination;					// illum
	
	public MaterialBuilder() {}
	
	public void reset() {
		ambient = new Vec3();			
		diffuse = new Vec3();			
		specular = new Vec3();			
		emission = new Vec3();			
		specularWeight = 0;				
		opticalDensity = 0;				
		dissolve = 0;					
		illumination = 0;								
	}
	
	public Material getMaterial() {
		return new Material(ambient,diffuse,specular,emission,
				specularWeight,opticalDensity,dissolve,illumination);
	}
	
	public void setEmission(Vec3 emission) {
		this.emission = emission;
	}

	public void setAmbient(Vec3 ambient) {
		this.ambient = ambient;
	}
	
	public void setDiffuse(Vec3 diffuse) {
		this.diffuse = diffuse;
	}
	
	public void setSpecular(Vec3 specular) {
		this.specular = specular;
	}
	
	public void setSpecularWeight(float specularWeight) {
		this.specularWeight = specularWeight;
	}

	public void setOpticalDensity(float opticalDensity) {
		this.opticalDensity = opticalDensity;
	}

	public void setDissolve(float dissolve) {
		this.dissolve = dissolve;
	}
	
	public void setIllumination(int illumination) {
		this.illumination = illumination;
	}
}
