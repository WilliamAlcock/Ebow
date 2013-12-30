package GameEngine;

public class Rotation {

/*	public static float[] rotate2D(float centerx,float centery,float objectx,float objecty,double degrees) {
		float[] retPos = new float[2];
		double radians = Math.toRadians(degrees);
		float x = objectx-centerx;
		float y = objecty-centery;
		retPos[0] = (float)(centerx+(x*(Math.cos(radians)))+(y*(Math.sin(radians))));
		retPos[1] = (float)(centery+(-x*(Math.sin(radians)))+(y*(Math.cos(radians))));
		return retPos;
	}
	
	public static float[] rotate2DCounter(float centerx,float centery,float objectx,float objecty,double degrees) {
		float[] retPos = new float[2];
		double radians = Math.toRadians(degrees);
		float x = objectx-centerx;
		float y = objecty-centery;
		retPos[0] = (float)(centerx+(x*(Math.cos(radians)))+(-y*(Math.sin(radians))));
		retPos[1] = (float)(centery+(x*(Math.sin(radians)))+(y*(Math.cos(radians))));
		return retPos;
	}
	
	public static float getAngleBetween(float aX,float aY, float bX, float bY) {
		float adjacent = bX - aX;
		float opposite = bY - aY;
		float angle = (float)Math.toDegrees(Math.atan(opposite/adjacent));		
		
		if (bX<aX) {
			// b is to the left of a
			if (bY==aY) {
				// b is directly to the left of a (-1,0)				
				angle = 270f;
			} else {
				angle = 270 + angle;
			}
		} else if (bX>aX){
			// b is to the right of a
			if (bY==aY) {
				// b is directly to the right of a (1,0)
				angle = 90f;
			} else {
				angle = 90 + angle;
			}
		} else if (bX==aX) {
			// b is in line with a
			if (bY<aY) {
				// b is directly below a (0,-1)
				angle = 360f;
			} else if (bY>aY) {
				// b is directly above a (0,1)
				angle = 180f;
			} else {
				// b is in the same position as a
			}
		}
		return angle;
	}		
	
	public static float truncate(float value,int decPlaces) {
		float retValue = (float) ((int)(value*Math.pow(10, decPlaces))/Math.pow(10,decPlaces));
		return retValue;
	}	*/
}
