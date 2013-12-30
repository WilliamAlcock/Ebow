package GameEngine;
import java.util.HashMap;
import java.util.Set;

public class Grid {

	private DLinkedList[][] grid;
	private HashMap<InPlayObj,DLinkedListNode> listNodes;		// List node for each enemy
	private HashMap<InPlayObj,GridPos> positions;				// Position of each enemy in the grid
	private int[][] enemysInSquare;								// Number of objects excluding PowerUps in each grid square
	
	public Grid(int row,int column) {
		System.out.println("start row: "+row+" column: "+column);
		grid = new DLinkedList[row][column];
		enemysInSquare = new int[row][column];
		for (int i=0;i<row;i++) {
			for (int j=0;j<column;j++) {
				grid[i][j] = new DLinkedList();
			}
		}
		listNodes = new HashMap<InPlayObj,DLinkedListNode>();
		positions = new HashMap<InPlayObj,GridPos>();
	}
	
	public Set<InPlayObj> getObjects() {
		return positions.keySet();
	}
	
	public GridPos getGridPos(InPlayObj enemy) {
		return positions.get(enemy);
	}
	
	public int getNumEnemysInSquare(int row,int column) {
		return enemysInSquare[row][column];
	}
	
	public void addToGrid(InPlayObj enemy,GridPos gridPos) {
		if (listNodes.get(enemy)==null) {
			if (!(enemy.getCategory().equals("PowerUp"))) {
				enemysInSquare[gridPos.getRow()][gridPos.getColumn()]++;
			}
			positions.put(enemy, gridPos);
			listNodes.put(enemy, grid[gridPos.getRow()][gridPos.getColumn()].addLast(enemy));
		} else {
			System.err.println("enemy already in grid");
		}
	}
	
	public void moveInGrid(InPlayObj enemy,GridPos newPos) {
		GridPos oldPos = positions.get(enemy);
		if (oldPos==null) {
			System.err.println("enemy does not exist in grid");			// This should not happen
		} else {
			grid[oldPos.getRow()][oldPos.getColumn()].removeNode(listNodes.get(enemy));
			grid[newPos.getRow()][newPos.getColumn()].addNodeLast(listNodes.get(enemy));
			positions.put(enemy, newPos);
			if (!(enemy.getCategory().equals("PowerUp"))) {
				enemysInSquare[newPos.getRow()][newPos.getColumn()]++;
				enemysInSquare[oldPos.getRow()][oldPos.getColumn()]--;
			}
		}
	}
	
	public void remove(InPlayObj enemy) {
		GridPos curPos = positions.get(enemy);
		if (curPos==null) {
			System.err.println("enemy not in grid");
		} else {
			grid[curPos.getRow()][curPos.getColumn()].removeNode(listNodes.get(enemy));
			positions.remove(enemy);
			listNodes.remove(enemy);
			if (!(enemy.getCategory().equals("PowerUp"))) {
				enemysInSquare[curPos.getRow()][curPos.getColumn()]--;
			}
		}
	}
	
	public DLinkedList getFromGrid(int row,int column) {
		return grid[row][column];
	}
}
