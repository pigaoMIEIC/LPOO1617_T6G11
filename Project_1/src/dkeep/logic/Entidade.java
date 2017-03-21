package dkeep.logic;

import java.io.Serializable;

public abstract class Entidade implements Serializable{
	int x,y;
	char tag=' ';
	char current=' ';
	
	public Entidade(int x, int y,char tag){
		this.x = x;
		this.y = y;
		this.tag = tag;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public char getTag() {
		return tag;
	}

	public void setTag(char tag) {
		this.tag = tag;
	}

	public char getCurrent() {
		return current;
	}

	public void setCurrent(char current) {
		this.current = current;
	}

	public abstract boolean print(String level,char current,Board b, String output[]);
	
	public abstract void move(char direction, Board b);

	
}
