package dkeep.test;

public class CellPosition {
	int x,y;
	public CellPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	

	@Override
	public boolean equals(Object obj) {
		CellPosition other = (CellPosition) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
}
