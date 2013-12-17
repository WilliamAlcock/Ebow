import VMQ.Vec3;


public class ScoreBoard {
	private int score;
	private int numDigits;
	private Digit[] scoreDigits;
	private Vec3 position;
	private float width;

	public ScoreBoard(Vec3 Position,int score,int numDigits,float width) {
		this.position = Position;
		this.score = score;
		this.numDigits = numDigits;
		this.scoreDigits = new Digit[numDigits];
		this.width = width*2;
		float xstart;
		if (numDigits%2==0) {
			xstart = (float) (position.getX()-((numDigits/2)-0.5)*this.width);
		} else {
			xstart = (float) (position.getX()-(numDigits/2)*this.width);
		}
		for (int i=0;i<numDigits;i++) {
			scoreDigits[i] = new Digit(new Vec3(xstart,position.getY(),position.getZ()),0);
			xstart += this.width;
		}
		tick();
	}
	
	/*
	 * updates the array of digits
	 */
	public void tick() {
		int tempscore = score;
		for (int i=numDigits-1;i>-1;i--) {
			scoreDigits[i].setNumber(tempscore%10);
			tempscore /= 10;
		}
	}
	
	/*
	 * sets the position
	 */
	public void setPosition(Vec3 position) {
		this.position = position;
		float xstart;
		if (numDigits%2==0) {
			xstart = (float) (position.getX()-((numDigits/2)-0.5)*width);
		} else {
			xstart = (float) (position.getX()-(numDigits/2)*width);
		}
		for (int i=0;i<numDigits;i++) {
			scoreDigits[i].setPosition(new Vec3(xstart,position.getY(),position.getZ()));
			xstart += width;
		}
	}
	
	/*
	 * returns the number of digits in the scoreboard
	 */
	public int getNumDigits() {
		return numDigits;
	}
	
	/*
	 * increments the scoreboard
	 */
	public void inc(int inc) {
		score += inc;
	}
	
	/*
	 * resets the scoreboard
	 */
	public void reset() {
		score = 0;
	}
	
	/* 
	 * returns the digit objects
	 */
	public Digit[] getDigits() {
		return scoreDigits;
	}
}
