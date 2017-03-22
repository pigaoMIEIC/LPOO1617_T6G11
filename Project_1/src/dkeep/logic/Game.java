package dkeep.logic;

import java.awt.Component;
import java.util.Vector;

import dkeep.test.CellPosition;

//import dkeep.test.CellPosition;

/**  
* Game.java - A class to store and run the game Board and the game Entidade's.
* @see Board
* @see Entidade
*/ 
public class Game implements java.io.Serializable{

	
	public final static int DEFEAT = 1;
	public final static int win = 0;
	int finalstatus = -1;
	
	static Board board;
	private static Vector<Entidade> map;
	boolean endLevel[] = new boolean[1];
	char input;

	/**  
	* Constructor of the class Game.
	* @param b The game board to be played.
	* @param map The container which has the information about the game Entidade's.
	*/
	public Game(Board b, Vector<Entidade> map) {
		Game.board = b;
		Game.setEntidades(map);
		endLevel[0] = false;
	}

	/**  
	* Method that is responsible for moving all the Entidade's in the game.
	* @param input Direction in which the game Hero will move if possible.
	*/
	public void Move(char input) {
		for (Entidade temp : getEntidades()) {
			temp.move(input, board);//input is ignored if Ogre is in random mode
			if (temp instanceof Hero) {
				((Hero) temp).stun(board, getEntidades());
			}
		}
	}

	/**  
	* Method that is responsible for making all the Ogres in the field map attacking and updating the game board.
	*/
	public void attack() {
		for (Entidade temp : getEntidades()) {
			if (temp instanceof Ogre) {
				((Ogre) temp).attack(board);
			}
		}
	}

	/**
	*  Method that is responsible for clearing the game board of all the Ogres' attacks.
	*/
	public void clearAttack() {
		for (Entidade temp : getEntidades()) {
			if (temp instanceof Ogre) {
				((Ogre) temp).clearAttack(board);
			}
		}
	}

	/**
	*  Method that prints the game Board to a string.
	*  @return String data type that contains the game board separating each line with new line characters.
	*/
	public String printBoard() {
		return board.print(getEntidades(), endLevel);
	}

	/**
	*  Method that is responsible for verifying end game conditions.
	*  @return Returns true if game has ended, false otherwise.
	*/
	public boolean end() {
		if (getEntidades().lastElement().getCurrent()=='s') {
			System.out.print("Parabéns passou o nível!!\n");
			finalstatus = win;
			return true;
		}
		if (((Hero) getEntidades().lastElement()).checkSurround('G', getEntidades())) {
			System.out.print("Foi apanhado pelo guarda. :(");
			finalstatus = DEFEAT;
			 return true;
		}
		 if (((Hero) getEntidades().lastElement()).checkSurround('O', getEntidades())) {
		 System.out.print("Foi apanhado pelo Ogre. :(");
		 finalstatus = DEFEAT;
		 return true;
		 }
		if (((Hero) getEntidades().lastElement()).checkSurround(board, '*')) {
			System.out.print("Foi atingido pelo Ogre. :(");
			finalstatus = DEFEAT;
			return true;
		}
		
		
		return false;
	}
	
	/**
	*  Method used in testing to obtain the hero position.
	*  @return CellPosition data type whit the information of the Hero in the game Board.
	*/
	public CellPosition getHeroPosition() {
		CellPosition temp = new CellPosition(getEntidades().lastElement().x, getEntidades().lastElement().y);
		return temp;
	}

	/**
	*  Method to verify the end game status.
	*  @return int data type with -1 if game has not ended, 1 if game ended in defeat, 0 if game ended in victory.
	*/
	public int getEndStatus() {
		// TODO Auto-generated method stub
		return finalstatus;
	}

	/**  
	* Getter for the field board.
	* @return Board data type.
	* @see Board
	*/
	public Board getBoard() {
		return board;
	}

	/**  
	* Getter for the field map.
	* @return Vector<Entidade> data type.
	* @see Entidade
	*/
	public Vector<Entidade> getEntidades() {
		return map;
	}

	/**  
	* Setter for the field map.
	* @param map Vector<Entidade> data type.
	* @see Entidade
	*/
	public static void setEntidades(Vector<Entidade> map) {
		Game.map = map;
	}
	
	/**  
	* Setter for the field board.
	* @param b Board data type.
	* @see Board
	*/
	public void setBoard(Board b) {
		Game.board=b;
	}

}
