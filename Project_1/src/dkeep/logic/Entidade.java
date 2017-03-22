package dkeep.logic;

import java.io.Serializable;

/**  
* Entidade.java - A class to store moving parts of the game.
*/ 
public abstract class Entidade implements Serializable{
	int x,y;
	char tag=' ';
	char current=' ';
	
	
	/**  
	* Constructor of the class Entidade.
	* @param x X position of the Entidade.
	* @param y Y position of the Entidade.
	* @param tag The simple graphic representation of the Entidade.
	*/ 
	public Entidade(int x, int y,char tag){
		this.x = x;
		this.y = y;
		this.tag = tag;
	}
	
	/**  
	* Setter for the field X.
	* @param x int data type.
	*/ 
	public void setX(int x) {
		this.x = x;
	}
	
	/**  
	* Setter for the field Y.
	* @param y int data type.
	*/ 
	public void setY(int y) {
		this.y = y;
	}

	/**  
	* Getter for the field X.
	* @return int data type.
	*/ 
	public int getX() {
		return x;
	}
	
	/**  
	* Setter for the field Y.
	* @return int data type.
	*/ 
	public int getY() {
		return y;
	}

	/**  
	* Getter for the field Tag.
	* @return char data type.
	*/ 
	public char getTag() {
		return tag;
	}

	/**  
	* Setter for the field Tag.
	* @param tag char data type.
	*/ 
	public void setTag(char tag) {
		this.tag = tag;
	}

	/**  
	* Getter for the field Current.
	* @return char data type.
	*/ 
	public char getCurrent() {
		return current;
	}

	/**  
	* Getter for the field Current.
	* @param current char data type.
	*/ 
	public void setCurrent(char current) {
		this.current = current;
	}

	
	public abstract boolean print(String level,char current,Board b, String output[]);
	
	public abstract void move(char direction, Board b);

	
}
