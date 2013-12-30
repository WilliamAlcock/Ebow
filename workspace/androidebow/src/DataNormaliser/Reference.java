package DataNormaliser;

import java.io.Serializable;

public class Reference implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public int index;
	
	public Reference(String name,int index) {
		this.name = name;
		this.index = index;
	}
	
	@Override
	public String toString() {
		return name+","+index;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null) return false;
		if (!(obj instanceof Reference)) return false;
		
		Reference castObj = (Reference)obj;
		if (name!=castObj.name) return false;
		if (index!=castObj.index) return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 17;
		hashCode = 31 * hashCode + name.hashCode();
		hashCode = 31 * hashCode + index;
		return hashCode;
	}
}