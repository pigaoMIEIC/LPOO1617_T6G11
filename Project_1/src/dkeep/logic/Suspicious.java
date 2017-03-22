package dkeep.logic;
import java.util.Random;


/**  
* Suspicious.java - A subclass of Guard to represent a personality.
*/
public class Suspicious extends Guarda {
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
	public Suspicious(int x, int y, char tag, char[] mov) {
		super(x, y, tag, mov);
	}

	/**  
	* Method responsible for moving the guard according to its personality.
	* @see Guarda
	* @see ComportamentoGuarda 
	*/
	public void move(char direction, Board b) {
		int i = rd.nextInt(100);
		if (i < 10) {
		dir = !dir;
		}
		compGuarda.move(dir, this, b);
	}

	public boolean getDir() {
		return dir;
	}
}
