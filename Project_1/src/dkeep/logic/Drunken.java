package dkeep.logic;

import java.util.Random;

/**  
* Drunken.java - A subclass of Guard to represent a personality.
*/
public class Drunken extends Guarda {
	Random rd = new Random();
	boolean dir = true;
	
	/**  
	* Constructor of the class Suspicious.
	* @param x X position of the Entidade.
	* @param y Y position of the Entidade.
	* @param tag The simple graphic representation of the Entidade.
	* @param mov Array that has the movement information of the guarda.
	* @see Guarda
	*/ 
	public Drunken(int x, int y, char tag, char[] mov) {
		super(x, y, tag, mov);
	}
	
	public boolean getDir() {
		return dir;
	}

	/**  
	* Method responsible for moving the guard according to its personality.
	* @param direction char that represents the direction to move.
	* @param b Board in which to move the Guarda.
	* @see Guarda
	* @see ComportamentoGuarda 
	*/
	public void move(char direction, Board b) {
		int i = rd.nextInt(10);
		if (i < 3) {
			setTag('g');
			return;
		} else {
			if (getTag() == 'g') {
				setTag('G');
				i = rd.nextInt(10);
				if (i < 3) {
					this.dir = !this.dir;
				}
			}
		}
		compGuarda.move(this, b);
	}
}
