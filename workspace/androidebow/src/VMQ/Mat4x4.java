package VMQ;

import java.io.Serializable;
import java.util.Arrays;

public class Mat4x4 implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// column, row
	private float[] mat4x4 = new float[16];
	public static final Mat4x4 identity = new Mat4x4(new float[] {1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1});
	
	public Mat4x4(Vec4 c1,Vec4 c2,Vec4 c3,Vec4 c4) {
		this.mat4x4[0] = c1.getX();
		this.mat4x4[1] = c1.getY();
		this.mat4x4[2] = c1.getZ();
		this.mat4x4[3] = c1.getW();
		
		this.mat4x4[4] = c2.getX();
		this.mat4x4[5] = c2.getY();
		this.mat4x4[6] = c2.getZ();
		this.mat4x4[7] = c2.getW();
		
		this.mat4x4[8] = c3.getX();
		this.mat4x4[9] = c3.getY();
		this.mat4x4[10] = c3.getZ();
		this.mat4x4[11] = c3.getW();
		
		this.mat4x4[12] = c4.getX();
		this.mat4x4[13] = c4.getY();
		this.mat4x4[14] = c4.getZ();
		this.mat4x4[15] = c4.getW();
	}
	
	public Mat4x4(float[] mat4x4) {
		this.mat4x4 = mat4x4;
	}
	
	public Mat4x4() {
	}
	
	public float getPos(int column,int row) {
		return mat4x4[(column*4)+row];
	}
	
	public void setPos(int column,int row,float value) {
		mat4x4[(column*4)+row] = value;
	}
	
	public String toString() {
		return mat4x4[0]+","+mat4x4[4]+","+mat4x4[8]+","+mat4x4[12]+"\n"+
				mat4x4[1]+","+mat4x4[5]+","+mat4x4[9]+","+mat4x4[13]+"\n"+
				mat4x4[2]+","+mat4x4[6]+","+mat4x4[10]+","+mat4x4[14]+"\n"+
				mat4x4[3]+","+mat4x4[7]+","+mat4x4[11]+","+mat4x4[15]+"\n";
	}
	
	public int getRows() {
		return 4;
	}
	
	public int getColumns() {
		return 4;
	}
	
	/*
	 * returns transpose of the matrix
	 */
	public Mat4x4 getTranspose() {
		return new Mat4x4(new float[] {mat4x4[0],mat4x4[1],mat4x4[2],mat4x4[3],
									mat4x4[4],mat4x4[5],mat4x4[6],mat4x4[7],
									mat4x4[8],mat4x4[9],mat4x4[10],mat4x4[11],
									mat4x4[12],mat4x4[13],mat4x4[14],mat4x4[15]});
	}
	
	/*
	 * returns inverse of the matrix (Gauss-Jordan Elimination)
	 */
	public Mat4x4 getInverse() {
		Mat4x4 leftMatrix = this.copy();								// A
		Mat4x4 rightMatrix = Mat4x4.identity;
	
		int column = 0;													// B
		do {
			int largest = column;
			for (int i=column;i<4;i++) {															// C
				if (Math.abs(leftMatrix.getPos(column,i))>Math.abs(leftMatrix.getPos(column,largest))) largest = i;
			}
			
			if (leftMatrix.getPos(column,largest) == 0) return null;								
			if (largest!=column) {																	// D
				exchangeRows(leftMatrix,rightMatrix,largest,column);
			}
			multScalar(leftMatrix,rightMatrix,column,(1/leftMatrix.getPos(column,column)));			// E
			for (int r=0;r<4;r++) {																	// F
				if (r!=column) {
					addMultRow(leftMatrix,rightMatrix,r,column,-(leftMatrix.getPos(column,r)));
				}
			}
			column ++;																				// G 
		} while (column<4);
		return rightMatrix;
	}
	
	/*
	 * swap row1 and row2
	 */
	private void exchangeRows(Mat4x4 leftMatrix,Mat4x4 rightMatrix,int row1,int row2) {
		float tempRow[] = new float[2];
		for (int i=0;i<4;i++) {
			tempRow[0] = leftMatrix.getPos(i, row1);
			tempRow[1] = rightMatrix.getPos(i, row1);
			leftMatrix.setPos(i, row1, leftMatrix.getPos(i, row2));
			rightMatrix.setPos(i, row1, rightMatrix.getPos(i, row2));
			leftMatrix.setPos(i, row2, tempRow[0]);
			rightMatrix.setPos(i, row2, tempRow[1]);
		}
	}
	
	/*
	 * multiply row by a non zero scalar
	 */
	private void multScalar(Mat4x4 leftMatrix,Mat4x4 rightMatrix,int row1,float scalar) {
		if (scalar!=0) {
			for (int i=0;i<4;i++) {
				leftMatrix.setPos(i, row1, leftMatrix.getPos(i, row1)*scalar);
				rightMatrix.setPos(i, row1, rightMatrix.getPos(i, row1)*scalar);
			}
		}
	}
	
	/*
	 * row1 = row1 + (row2*scalar) 
	 */
	private void addMultRow(Mat4x4 leftMatrix,Mat4x4 rightMatrix,int row1,int row2,float scalar) {
		for (int i=0;i<4;i++) {
			leftMatrix.setPos(i, row1, leftMatrix.getPos(i, row1)+(leftMatrix.getPos(i, row2)*scalar));
			rightMatrix.setPos(i, row1, rightMatrix.getPos(i, row1)+(rightMatrix.getPos(i, row2)*scalar));
		}
	}
	
	public Mat4x4 round(int places) {
		float power = (float)Math.pow(10, places);
		Mat4x4 retMat4x4 = new Mat4x4();
		for (int column=0;column<4;column++) {
			for (int row=0;row<4;row++) {
				retMat4x4.setPos(column, row, (Math.round((this.mat4x4[(column*4)+row]*power)))/power);
			}
		}
		return retMat4x4;
	}
	
	public Mat4x4 add(Mat4x4 mat4x4) {
		Mat4x4 retMat4x4 = new Mat4x4();
		for (int column=0;column<4;column++) {
			for (int row=0;row<4;row++) {
				retMat4x4.setPos(column, row, this.getPos(column, row)+mat4x4.getPos(column, row));
			}
		}
		return retMat4x4;
	}
	
	public Mat4x4 sub(Mat4x4 mat4x4) {
		Mat4x4 retMat4x4 = new Mat4x4();
		for (int column=0;column<4;column++) {
			for (int row=0;row<4;row++) {
				retMat4x4.setPos(column, row, this.getPos(column, row)-mat4x4.getPos(column, row));
			}
		}
		return retMat4x4;
	}
	
	public Mat4x4 multiply(float m) {
		Mat4x4 retMat4x4 = new Mat4x4();
		for (int column=0;column<4;column++) {
			for (int row=0;row<4;row++) {
				retMat4x4.setPos(column, row, this.getPos(column, row)*m);
			}
		}
		return retMat4x4;
	}
	
	public Vec4 multiply(Vec4 vector) {
		Vec4 retVec4 = new Vec4();
		retVec4.setX((vector.getX()*mat4x4[0])+(vector.getY()*mat4x4[4])+(vector.getZ()*mat4x4[8])+(vector.getW()*mat4x4[12]));
		retVec4.setY((vector.getX()*mat4x4[1])+(vector.getY()*mat4x4[5])+(vector.getZ()*mat4x4[9])+(vector.getW()*mat4x4[13]));
		retVec4.setZ((vector.getX()*mat4x4[2])+(vector.getY()*mat4x4[6])+(vector.getZ()*mat4x4[10])+(vector.getW()*mat4x4[14]));
		retVec4.setW((vector.getX()*mat4x4[3])+(vector.getY()*mat4x4[7])+(vector.getZ()*mat4x4[11])+(vector.getW()*mat4x4[15]));
		return retVec4;
	}
	
	public Mat4x4 multiply(Mat4x4 matrix) {
		Mat4x4 retMat4x4 = new Mat4x4();		
		for (int i=0;i<16;i++) {
			int remainder = i%4;
			int div = i-remainder;
			retMat4x4.mat4x4[i] = mat4x4[remainder]*matrix.mat4x4[div]+
					mat4x4[remainder+4]*matrix.mat4x4[div+1]+
					mat4x4[remainder+8]*matrix.mat4x4[div+2]+
					mat4x4[remainder+12]*matrix.mat4x4[div+3];
		}
		return retMat4x4;
	}
	
	public Mat4x4 copy() {
		float[] newMat4x4 = mat4x4.clone();
		return new Mat4x4(newMat4x4);
	}
	
	public float[] getAsArray() {
		return mat4x4;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj==null) return false;
		if (obj==this) return true;
		if (!(obj instanceof Mat4x4)) return false;
		
		return Arrays.equals(mat4x4,((Mat4x4)obj).mat4x4);
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(mat4x4); 
	}
}
