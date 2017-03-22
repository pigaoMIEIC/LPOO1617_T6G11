package dkeep.logic;

/**  
* Drunken.java - A subclass of Guard to represent a personality.
*/
public class Rookie extends Guarda {

	/**  
	* Constructor of the class Rookie.
	* @param x X position of the Entidade.
	* @param y Y position of the Entidade.
	* @param tag The simple graphic representation of the Entidade.
	* @param mov Array that has the movement information of the Guarda.
	* @see Guarda
	*/ 
	public Rookie(int x, int y, char tag, char[] mov) {
		super(x, y, tag, mov);
	}

	
	/**  
	* Method responsible for moving the guard according to its personality.
	* @param direction char that represents the direction to move.
	* @param b Board in which to move the Guarda.
	* @see Guarda
	* @see ComportamentoGuarda 
	*/
	public void move(char direction, Board b) {
		compGuarda.move(true,this,b);
	}

}
