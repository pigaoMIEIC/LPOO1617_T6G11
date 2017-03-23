package dkeep.logic;

import java.io.Serializable;
import java.util.Vector;

/**
 * Board.java - A class to store the game symbols array.
 */
public class Board implements Serializable{
	private char board[/* y */][/* x */];
	

	private String name;
	private String output[]= new String[1];

	/**
	 * Constructor for the class Board.
	 * @param layout Bidimensional array of char to represent a matrix like a game board.
	 */
	public Board(char[][] layout) {
		board = copy(layout);
	}

	/**
	 * Getter for the field board.
	 * @return Bidimensional array of char data type.
	 */
	public char[][] getBoard() {
		return board;
	}
	
	/**
	 * Method to copy the board. 
	 * @param layout 
	 * @return
	 */
	private char[][] copy(char[][] layout) {
		char[][] copy = new char[layout.length][layout[0].length];
		for(int i=0;i<layout.length;i++){
			for(int j=0; j<layout[0].length;j++){
				copy[i][j]=layout[i][j];
			}
		}
		return copy;
	}
	
	/**
	 * Method to print the Entidade in the board.
	 * @param map Vector of Entidade's to store the entidades in the game.
	 * @param endFlag boolean single emlement array is set to true if game has ended.
	 * @return String that represents the board.
	 */
	public String print(Vector<Entidade> map, boolean endFlag[]) {
		output[0]="";
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				if (this.printEntidade(x, y, map, endFlag,output)) {
					//output[0]+=' ';
					continue;
				}
				output[0]+=(board[y][x]);
				//output[0]+=' ';
			}
			output[0]+="\n";
		}
		return output[0];
	}

	/**
	 * Method responsible for printing an Entidade and setting the end game flag 
	 * @param x Board x coordinate.
	 * @param y Board y coordinate.
	 * @param map Vector of game Entidade's.
	 * @param endFlag The flag to verify if the game ended.  
	 * @param output String that represents the board.
	 * @return True if an Entidade was printed, false otherwise.
	 */
	private boolean printEntidade(int x, int y, Vector<Entidade> map, boolean endFlag[],String output[]) {
		boolean flag = false;
		if (map == null)
			return flag;
		for (Entidade temp : map) {
			if (temp.x == x && temp.y == y) {
				if (endFlag[0]) {
					temp.print(this.name, temp.getCurrent(), this, output);
				} else {
					endFlag[0] = temp.print(this.name, temp.getCurrent(), this, output);
				}
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * Setter for the field name
	 * @param name String data type.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Setter for the field board.
	 * @param Level bidimensional array of char.
	 */
	public void updateLevel(char[][] level) {
		board=copy(level);
	}

	/**
	 * Setter for a board element
	 * @param x x coordinate.
	 * @param y y coordinate.
	 * @param value char data type to be set in position [x,y].
	 */
	public void set(int x, int y, char value) {
		board[y][x] = value;
	}

	/**
	 * Getter for a board element
	 * @param x x coordinate.
	 * @param y y coordinate.
	 * @return char data type that is in position [x,y].
	 */
	public char get(int x, int y) {
		return board[y][x];
	}

	/**
	 * Method that updates the board and modifies the parameters accordingly.
	 * @param x x coordinate to refresh the board.
	 * @param y y coordinate to refresh the board.
	 * @param dir direction in which the Entidade moved. 
	 * @param tag Entidade filed to be updated.
	 * @param current Entidade field to be updated.
	 * @return Char that is the next current  field to be set in Entidade.
	 */
	public char refresh(int x, int y, char dir, char tag, char current) {
		char temp = this.get(x, y);
		if (temp == 'k' && name == "level2") {
			switch (tag) {
			case 'O':
				tag = '$';
				break;
			}
		}
		switch (dir) {
		case 's':
			this.board[y][x] = tag;
			this.board[y - 1][x] = current;
			break;
		case 'w':
			this.board[y][x] = tag;
			this.board[y + 1][x] = current;
			break;
		case 'd':
			this.board[y][x] = tag;
			this.board[y][x - 1] = current;
			break;
		case 'a':
			this.board[y][x] = tag;
			this.board[y][x + 1] = current;
			break;
		}
		return temp;
	}

	/**
	 * Method that changes the door representation in every level.
	 */
	public void openDoors() {
		if (name.equals("level1")) {
			board[5][0] = 's';
			board[6][0] = 's';
		}
		if (name.equals("level2")) {
			for (int y = 0; y < board.length; y++) {
				for (int x = 0; x < board[0].length; x++) {
					if(board[y][x]=='i'){
						board[y][x]='s';
					}
				}
			}
		}
		if(name.equals("testlevel")){
			board[2][0] = 's';
			board[3][0] = 's';
		}
		if(name.equals("testlevel2")){
			board[2][0] = 's';
		}
	}

	/**
	 * Getter for the field name.
	 * @return String data type. 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method that reads a Bidimensional array and transforms it into a Vector of Entidade's. 
	 * @return Vector of entidades read from the board. Null if the board is invalid.
	 */
	public Vector<Entidade> readBoard() {
		Hero hero=null;
		boolean hasDoor=false, hasKey=false;
		Vector<Entidade> temp = new Vector<Entidade>();
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				switch(board[y][x]){
				case 'A':
					if(hero!=null)
						return null;
					hero=new Hero(x,y,'A');
					break;
				case 'O':temp.add(new Ogre(x,y,'O')); break;
				case 'i':hasDoor=true;
				case 'k':hasKey=true;
				}
			}
		}
		if(!(hasKey&&hasDoor&&!temp.isEmpty()&&hero!=null)){
			return null;
		}
		temp.add(hero);
		return temp;
	}
}