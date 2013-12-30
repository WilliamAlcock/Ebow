package FileReader;

import java.io.Serializable;

import VMQ.Vec3;

public class Material implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Labels in Mtl file
	private Vec3 ambient; 						// Ka
	private Vec3 diffuse; 						// Kd
	private Vec3 specular;						// Ks
	private Vec3 emission;						// I have added this to give my materials an emission
	private float specularWeight;				// Ns
	private float opticalDensity;				// Ni
	private float dissolve;						// d
	private int illumination;					// illum
	private int hashCode;
	
	public Material(Vec3 ambient,Vec3 diffuse,Vec3 specular,Vec3 emission,
					float specularWeight,float opticalDensity,float dissolve,int illumination) {
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
		this.emission = emission;
		this.specularWeight = specularWeight;
		this.opticalDensity = opticalDensity;
		this.dissolve = dissolve;
		this.illumination = illumination;
		
		hashCode = 17;
		hashCode = 31 * hashCode + ambient.hashCode();
		hashCode = 31 * hashCode + diffuse.hashCode();
		hashCode = 31 * hashCode + specular.hashCode();
		hashCode = 31 * hashCode + emission.hashCode();
		hashCode = 31 * hashCode + Float.floatToIntBits(specularWeight);
		hashCode = 31 * hashCode + Float.floatToIntBits(opticalDensity);
		hashCode = 31 * hashCode + Float.floatToIntBits(dissolve);
		hashCode = 31 * hashCode + illumination;
	}
	
	public Vec3 getEmission() {
		return emission;
	}
	
	public Vec3 getAmbient() {
		return ambient;
	}

	public Vec3 getDiffuse() {
		return diffuse;
	}
	
	public Vec3 getSpecular() {
		return specular;
	}
	
	public float GetSpecularWeight() {
		return specularWeight;
	}
	
	public float getOpticalDensity() {
		return opticalDensity;
	}

	public float getDissolve() {
		return dissolve;
	}
	
	public int getIllumination() {
		return illumination;
	}
	
	public String toString() {
		return "ambient: "+ambient+"\ndiffuse: "+diffuse+"\nspecular: "+specular+"\nemission "+emission+
				"specularWeight: "+specularWeight+"\nopticalDensity: "+opticalDensity+"\ndissolve: "+dissolve+"\nillumination: "+illumination;		
	}
	
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null) return false;
		if (!(obj instanceof Material)) return false;
		
		Material castObj = (Material)obj;
		if (!(ambient.equals(castObj.ambient))) return false;
		if (!(diffuse.equals(castObj.diffuse))) return false;
		if (!(specular.equals(castObj.specular))) return false;
		if (!(emission.equals(castObj.emission))) return false;
		if (specularWeight != castObj.specularWeight) return false;
		if (opticalDensity != castObj.opticalDensity) return false;
		if (dissolve != castObj.dissolve) return false;
		if (illumination != castObj.illumination) return false;
		
		return true;
	}
	
	public int hashCode() {
		System.out.println(this+"\nhashcode: "+this.hashCode);
		return hashCode;
	}	
}
