
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import OpenGLTools.WindowDimensions;
import VMQ.Vec3;

public class CollisionDetector implements Observer {
	
	private Grid enemys;
	private HashMap<String,Vec3> dimensions;							// Dimensions of each type of obj
	private Player player1;
	
	private float screenWidth;			// the width of the screen
	private float screenHeight;			// the height of the screen
	private float boxWidth;				// the height of each grid square
	private float boxHeight;			// the width of each grid square
	private int gridRows;				// Number of rows in the grid
	private int gridColumns;			// Number of columns in the grid
	private int size; 
	private WindowDimensions windowDimensions;
	private float zvec;
	
	// need to pass the screen width and height in
	// get the width and height of the player and work out the amount of rows and columns
	public CollisionDetector(WindowDimensions windowDimensions,float zvec,HashMap<String,Vec3> dimensions,Player player1) {
		this.windowDimensions = windowDimensions;
		this.dimensions = dimensions;
		this.boxWidth = (dimensions.get("Ship").getX()*2.0f)*1.2f;
		this.boxHeight = (dimensions.get("Ship").getZ()*2.0f)*1.2f;
		this.zvec = zvec;
		this.player1 = player1;
		this.enemys = getNewGrid();
	}
	
	public void tick() {
		if (size>0) {
			check((List<InPlayObj>) player1.getObjects().clone()); 
		}
	}
	
	/*
	 * resets the grid when the window is resized
	 */
	private Grid getNewGrid() {
		this.screenWidth = windowDimensions.getMaxXPos(Math.abs(zvec))*2; 
		this.screenHeight = windowDimensions.getMaxYPos(Math.abs(zvec))*2;
		this.gridRows = (int)(this.screenHeight/this.boxHeight)+1;
		this.gridColumns = (int)(this.screenWidth/this.boxWidth)+1;
		System.out.println(screenWidth+","+screenHeight+" "+gridRows+","+gridColumns);
		return new Grid(gridRows,gridColumns);
	}
	
	/*
	 * checks which grid square an object should be in and moves it if necessary
	 */
	public void move(InPlayObj enemy) {
		GridPos newPos = getGridSquare(enemy.getPosition().getX(),enemy.getPosition().getY());
		GridPos oldPos = enemys.getGridPos(enemy);
		if ((newPos.getRow()!=oldPos.getRow()) || (newPos.getColumn()!=oldPos.getColumn())) {
			enemys.moveInGrid(enemy,newPos);
		} 
	}
	
	/*
	 * adds an object to the grid
	 */
	public void add(InPlayObj enemy) {		
		enemys.addToGrid(enemy,getGridSquare(enemy.getPosition().getX(), enemy.getPosition().getY()));
		size++;
	}
	
	/*
	 * removes an object from the grid
	 */
	public void remove(InPlayObj enemy) {
		enemys.remove(enemy);
		size--;
	}
	
	/* 
	 * iterates through each square and finds the nearest square with an object in
	 * returns null if there are none
	 */
	public InPlayObj getClosestObject(InPlayObj object) {
		GridPos myGrid = getGridSquare(object.getPosition().getX(),object.getPosition().getY());
		GridPos closest = null;
		int closestDistance = gridRows*gridColumns;
		
		for (int row=0;row<gridRows;row++) {
			for (int column=0;column<gridColumns;column++) {
				if (enemys.getNumEnemysInSquare(row, column)>0) {
					int distance = Math.abs(myGrid.getRow()-row) + Math.abs(myGrid.getColumn()-column); 
					if (distance<closestDistance) {
						closest = new GridPos(row,column);
						closestDistance = distance;
					}
				}
			}
		}
		
		if (closest==null) { 
			return null;
		} else {
			DLinkedListNode curNode = enemys.getFromGrid(closest.getRow(), closest.getColumn()).getHead();
			InPlayObj curObj = (InPlayObj)curNode.getData();
			while (curObj.getCategory().equals("PowerUp")) {
				curNode = curNode.getNext();
				curObj = (InPlayObj)curNode.getData();
			}
			return curObj;
		}
	}
	
	/*
	 * returns which square an object should be in from its screen position
	 */
	private GridPos getGridSquare(float xpos,float ypos) {
		return new GridPos((int)(formatPos(ypos,screenHeight)/boxHeight),(int)(formatPos(xpos,screenWidth)/boxWidth));
	}
	
	/*
	 * formats a position so 0,0 is at the beginning not the middle
	 */
	private float formatPos(float pos,float totLength) {
		float retPos = 0.0f;
		if (pos<0.0f) {
			retPos = (totLength/2)-(pos*-1.0f);
			if (retPos<0.0f) retPos = 0.0f;
		} else if (pos>0.0f) {
			retPos = (totLength/2)+pos;
			if (retPos>totLength) retPos = totLength;
		} else if (pos==0.0f) {
			retPos = (totLength/2);
		}
		return retPos;
	}
	
	/*
	 * checks if an object is colliding with any objects in the grid
	 */
	public void check(List<InPlayObj> toCheck) {
		for (InPlayObj curObj: toCheck) {
			GridPos gridPos = getGridSquare(curObj.getPosition().getX(),curObj.getPosition().getY());
			int columnStart;
			int columnEnd;
			int rowStart;
			int rowEnd;
			if (gridPos.getRow()==0) rowStart=0; else rowStart=-1;
			if (gridPos.getRow()==(gridRows-1)) rowEnd=1; else rowEnd=2;
			if (gridPos.getColumn()==0) columnStart=0; else columnStart=-1;
			if (gridPos.getColumn()==(gridColumns-1)) columnEnd=1; else columnEnd=2;
			for (int j=rowStart;j<rowEnd;j++) {
				for (int i=columnStart;i<columnEnd;i++) {
					if (enemys.getFromGrid((gridPos.getRow()+j), gridPos.getColumn()+i)!=null) {
						checkList(curObj,enemys.getFromGrid((gridPos.getRow()+j), (gridPos.getColumn()+i)));
					}
				}
			}
		}
	}
	
	/*
	 * checks if an object is colliding with any objects in a list
	 */
	private void checkList(InPlayObj myGameObj, DLinkedList squareList) {
		DLinkedListNode curNode = squareList.getHead();
		while(curNode.getData()!=null) {
			closeDetect(myGameObj,(InPlayObj)curNode.getData());
			curNode=curNode.getNext();
		}
	}
	
	// check if two objects are touching
	// this is done by seeing if the any of the corners of the smaller object are inside the corners of the larger object
	private void closeDetect(InPlayObj gameObj1, InPlayObj gameObj2) {
		float xDist = formatPos(gameObj1.getPosition().getX(),screenWidth) - formatPos(gameObj2.getPosition().getX(),screenWidth);
		if (xDist < 0.0f) xDist = xDist * -1.0f;
		if (xDist<(dimensions.get(gameObj1.getType()).getX()+dimensions.get(gameObj2.getType()).getX())) {
			float yDist = formatPos(gameObj1.getPosition().getY(),screenHeight) - formatPos(gameObj2.getPosition().getY(),screenHeight);
			if (yDist < 0.0f) yDist = yDist * -1.0f;
			if (yDist<dimensions.get(gameObj1.getType()).getZ()+dimensions.get(gameObj2.getType()).getZ()) {
				gameObj2.handleCollision(gameObj1);
			}
		}
	}

	@Override
	public void update(Observable o, Object object) {
		Grid newEnemys = getNewGrid();
		for (InPlayObj curObj: enemys.getObjects()) {
			newEnemys.addToGrid(curObj, getGridSquare(curObj.getPosition().getX(), curObj.getPosition().getY()));
		}
		this.enemys = newEnemys;
	}
}